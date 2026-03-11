package com.example.bantaycampus01.screens.user

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bantaycampus01.R
import com.example.bantaycampus01.partials.user.UserHeader
import com.example.bantaycampus01.partials.user.UserNavBar
import com.example.bantaycampus01.partials.user.UserUI
import com.example.bantaycampus01.screens.user.menu.PopUps.MarkSafeDialog
import com.example.bantaycampus01.viewmodel.ReportViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun UserSafetyScreen(
    modifier: Modifier,
    navController: NavController,

    userName: String = "User",

    currentStatusText: String = "Current Status: ALL SAFE",
    lastCheckinText: String = "Last Student Check-in 10 minutes ago",

    advisoryTitle: String = "Campus Security Advisory",
    advisoryTime: String = "10:45 AM • Ongoing",
    advisoryHeadline: String = "Attention Students!",
    advisoryBody: String =
        "There is a power outage at the Academic Building that is currently being resolved.\nPlease take caution.",

    campusUpdateTitle: String = "CAMPUS UPDATE",
    campusUpdateStatus: String = "SAFE",
    campusUpdateMessage: String = "\"Normal campus operations. No immediate threats reported.\"",
    campusUpdateTime: String = "10:45 AM",

    onClockClick: () -> Unit = {},
    onViewAdvisoryClick: () -> Unit = {},
    onViewContactsClick: () -> Unit = {},
    onViewMapsClick: () -> Unit = {}
) {
    UserSafetyPageUI(
        modifier = modifier,
        navController = navController,
        userName = userName,

        currentStatusText = currentStatusText,
        lastCheckinText = lastCheckinText,

        advisoryTitle = advisoryTitle,
        advisoryTime = advisoryTime,
        advisoryHeadline = advisoryHeadline,
        advisoryBody = advisoryBody,

        campusUpdateTitle = campusUpdateTitle,
        campusUpdateStatus = campusUpdateStatus,
        campusUpdateMessage = campusUpdateMessage,
        campusUpdateTime = campusUpdateTime,

        onClockClick = onClockClick,
        onViewAdvisoryClick = onViewAdvisoryClick,
        onViewContactsClick = onViewContactsClick,
        onViewMapsClick = onViewMapsClick
    )
}

@Composable
fun UserSafetyPageUI(
    modifier: Modifier,
    navController: NavController,

    userName: String = "User",

    currentStatusText: String,
    lastCheckinText: String,

    advisoryTitle: String,
    advisoryTime: String,
    advisoryHeadline: String,
    advisoryBody: String,

    campusUpdateTitle: String,
    campusUpdateStatus: String,
    campusUpdateMessage: String,
    campusUpdateTime: String,

    onClockClick: () -> Unit,
    onViewAdvisoryClick: () -> Unit,
    onViewContactsClick: () -> Unit,
    onViewMapsClick: () -> Unit
) {
    val advisoryBorder = UserUI.DangerRed
    val cardBg = UserUI.CardBg

    val screenScroll = rememberScrollState()
    val context = LocalContext.current
    val reportViewModel: ReportViewModel = viewModel()
    val db = remember { FirebaseFirestore.getInstance() }

    var showMarkedSafeDialog by rememberSaveable { mutableStateOf(false) }
    var showFireExitMapDialog by rememberSaveable { mutableStateOf(false) }

    var currentStatusState by rememberSaveable { mutableStateOf(currentStatusText) }
    var lastCheckinState by rememberSaveable { mutableStateOf(lastCheckinText) }

    var advisoryTitleState by rememberSaveable { mutableStateOf(advisoryTitle) }
    var advisoryTimeState by rememberSaveable { mutableStateOf(advisoryTime) }
    var advisoryHeadlineState by rememberSaveable { mutableStateOf(advisoryHeadline) }
    var advisoryBodyState by rememberSaveable { mutableStateOf(advisoryBody) }

    var campusUpdateTitleState by rememberSaveable { mutableStateOf(campusUpdateTitle) }
    var campusUpdateStatusState by rememberSaveable { mutableStateOf(campusUpdateStatus) }
    var campusUpdateMessageState by rememberSaveable { mutableStateOf(campusUpdateMessage) }
    var campusUpdateTimeState by rememberSaveable { mutableStateOf(campusUpdateTime) }

    val statusDot = when (campusUpdateStatusState.uppercase()) {
        "SAFE", "RESOLVED" -> UserUI.Green
        "CAUTION" -> Color(0xFFF4B400)
        "RESTRICTED" -> Color(0xFFE53935)
        else -> UserUI.Green
    }

    fun openDialer(phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to open phone app.", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        reportViewModel.refreshSafetyStatusIfExpired(
            onComplete = { },
            onFailure = { }
        )
    }

    DisposableEffect(Unit) {
        val advisoryListener: ListenerRegistration =
            db.collection("campus_updates")
                .document("advisory")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) return@addSnapshotListener

                    if (snapshot != null && snapshot.exists()) {
                        val advisoryMessage = snapshot.getString("message") ?: advisoryBody
                        val updatedAt = snapshot.getLong("updatedAt") ?: 0L

                        advisoryTitleState = advisoryTitle
                        advisoryHeadlineState = "Attention Students!"
                        advisoryBodyState = advisoryMessage

                        advisoryTimeState =
                            if (updatedAt > 0L) {
                                SimpleDateFormat(
                                    "h:mm a",
                                    Locale.getDefault()
                                ).format(Date(updatedAt)) + " • Ongoing"
                            } else {
                                advisoryTime
                            }
                    }
                }

        val statusListener: ListenerRegistration =
            db.collection("campus_updates")
                .document("status")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) return@addSnapshotListener

                    if (snapshot != null && snapshot.exists()) {
                        val status = snapshot.getString("status") ?: campusUpdateStatus
                        val message = snapshot.getString("message") ?: campusUpdateMessage
                        val updatedAt = snapshot.getLong("updatedAt") ?: 0L

                        campusUpdateTitleState = campusUpdateTitle
                        campusUpdateStatusState = status
                        campusUpdateMessageState = message

                        currentStatusState = "Current Status: $status"

                        campusUpdateTimeState =
                            if (updatedAt > 0L) {
                                SimpleDateFormat(
                                    "h:mm a",
                                    Locale.getDefault()
                                ).format(Date(updatedAt))
                            } else {
                                campusUpdateTime
                            }

                        lastCheckinState =
                            if (updatedAt > 0L) {
                                "Last updated " + SimpleDateFormat(
                                    "MMM d, yyyy - h:mm a",
                                    Locale.getDefault()
                                ).format(Date(updatedAt))
                            } else {
                                lastCheckinText
                            }
                    }
                }

        onDispose {
            advisoryListener.remove()
            statusListener.remove()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(UserUI.Bg)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
                .verticalScroll(screenScroll)
        ) {

            UserHeader()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(UserUI.DarkBlue)
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = currentStatusState,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Text(
                        text = lastCheckinState,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        fontStyle = FontStyle.Italic
                    )
                }

                Button(
                    onClick = {
                        onClockClick()

                        reportViewModel.markUserSafe(
                            onSuccess = {
                                showMarkedSafeDialog = true
                                Toast.makeText(
                                    context,
                                    "Check-in successful.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onFailure = {
                                Toast.makeText(
                                    context,
                                    "Failed to check in: ${it.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    },
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.22f)
                    ),
                    modifier = Modifier.size(46.dp)
                ) {
                    Text("✅", fontSize = 24.sp)
                }
            }

            Spacer(Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .fillMaxWidth()
                    .border(2.dp, advisoryBorder, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(12.dp)
                    .clickable { onViewAdvisoryClick() }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "👤  $advisoryTitleState",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = UserUI.DarkBlue
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = advisoryTimeState,
                        fontSize = 9.sp,
                        color = UserUI.DarkBlue.copy(alpha = 0.7f)
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = advisoryHeadlineState,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = advisoryBorder
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = advisoryBodyState,
                    fontSize = 12.sp,
                    color = UserUI.DarkBlue,
                    lineHeight = 14.sp
                )
            }

            Spacer(Modifier.height(14.dp))

            Text(
                text = campusUpdateTitleState,
                modifier = Modifier.padding(horizontal = 14.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = UserUI.DarkBlue
            )

            Spacer(Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(cardBg)
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Status: $campusUpdateStatusState",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )

                    Spacer(Modifier.width(6.dp))

                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(statusDot)
                    )
                }

                Spacer(Modifier.height(10.dp))

                Text(
                    text = campusUpdateMessageState,
                    fontSize = 14.sp,
                    color = Color.White,
                    lineHeight = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = campusUpdateTimeState,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(Modifier.height(14.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                InfoPanel(
                    modifier = Modifier.weight(1f),
                    panelBg = UserUI.DarkBlue,
                    title = "EMERGENCY CONTACTS",
                    titleTextColor = Color.White
                ) {
                    MiniWhiteCard(
                        title = "ADMIN",
                        body = "+63 912 3456 789",
                        onClick = {
                            openDialer("+639123456789")
                        }
                    )

                    Spacer(Modifier.height(8.dp))

                    MiniWhiteCard(
                        title = "GUARD",
                        body = "+63 917 123 4567",
                        onClick = {
                            openDialer("+639171234567")
                        }
                    )

                    Spacer(Modifier.height(8.dp))

                    MiniWhiteCard(
                        title = "CLINIC",
                        body = "+63 918 987 6543",
                        onClick = {
                            openDialer("+639189876543")
                        }
                    )
                }

                InfoPanel(
                    modifier = Modifier.weight(1f),
                    panelBg = UserUI.PaleBlueCard,
                    title = "FIRE EXIT MAPS",
                    titleTextColor = UserUI.DarkBlue
                ) {
                    MiniWhiteCard(
                        title = "ACADEMIC BUILDING",
                        body = "2ND FLOOR",
                        onClick = {
                            showFireExitMapDialog = true
                        }
                    )

                    Spacer(Modifier.height(8.dp))

                    MiniWhiteCard(
                        title = "ACADEMIC BUILDING",
                        body = "3RD FLOOR",
                        onClick = {
                            showFireExitMapDialog = true
                        }
                    )

                    Spacer(Modifier.height(8.dp))

                    MiniWhiteCard(
                        title = "ACADEMIC BUILDING",
                        body = "4TH FLOOR",
                        onClick = {
                            showFireExitMapDialog = true
                        }
                    )
                }
            }

            Spacer(Modifier.height(90.dp))
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            UserNavBar(
                modifier = Modifier,
                navController = navController
            )
        }

        MarkSafeDialog(
            show = showMarkedSafeDialog,
            onConfirm = { showMarkedSafeDialog = false },
            onDismiss = { showMarkedSafeDialog = false }
        )

        if (showFireExitMapDialog) {
            FireExitMapDialog(
                onDismiss = { showFireExitMapDialog = false }
            )
        }
    }
}

@Composable
private fun InfoPanel(
    modifier: Modifier = Modifier,
    panelBg: Color,
    title: String,
    titleTextColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(panelBg)
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .padding(vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = titleTextColor,
                textAlign = TextAlign.Center
            )
        }

        Spacer(Modifier.height(6.dp))
        content()
    }
}

@Composable
private fun MiniWhiteCard(
    title: String,
    body: String,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(
                1.dp,
                UserUI.DarkBlue.copy(alpha = 0.25f),
                RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 10.dp, horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Black,
            color = UserUI.DarkBlue,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(2.dp))

        Text(
            text = body,
            fontSize = 12.sp,
            color = UserUI.DarkBlue,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun FireExitMapDialog(
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Fire Exit Map",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = UserUI.DarkBlue
            )

            Spacer(modifier = Modifier.height(12.dp))

            Image(
                painter = painterResource(id = R.drawable.sample_fire_exit),
                contentDescription = "Fire Exit Map",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onDismiss,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = UserUI.DarkBlue
                )
            ) {
                Text("Close", color = Color.White)
            }
        }
    }
}