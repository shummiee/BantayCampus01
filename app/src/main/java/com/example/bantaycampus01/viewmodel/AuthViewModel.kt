package com.example.bantaycampus01.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bantaycampus01.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class AuthViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String?, String?) -> Unit
    ) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    val uid = auth.currentUser?.uid

                    FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(uid!!)
                        .get()
                        .addOnSuccessListener { document ->

                            val role = document.getString("role")

                            onResult(true, null, role)

                        }

                } else {

                    onResult(false, task.exception?.localizedMessage, null)

                }
            }
    }

    fun logout(onResult: (Boolean, String?) -> Unit) {
        try {
            auth.signOut()
            onResult(true, null)
        } catch (e: Exception) {
            onResult(false, e.localizedMessage)
        }
    }

    fun register(
        email : String,
        name : String,
        contactNumber : String,
        idNumber : String,
        department : String,
        dob : String,
        role : String,

        password: String,
        onResult: (Boolean, String?)-> Unit){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    var userId = it.result?.user?.uid

                    val userModel = UserModel(name, email, contactNumber, idNumber, department, dob, role="USER", userId!!)
                    firestore.collection("users").document(userId)
                        .set(userModel)
                        .addOnCompleteListener { dbTask->
                            if(dbTask.isSuccessful){
                                onResult(true,null)
                            }else{
                                onResult(false,"Something went wrong")
                            }
                        }

                }else{
                    onResult(false,it.exception?.localizedMessage)
                }
            }
    }

    fun getUserName(onResult: (String?) -> Unit) {
        val uid = auth.currentUser?.uid

        if (uid != null) {
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener { document ->
                    val fullName = document.getString("name")
                    val firstName = fullName?.split(" ")?.firstOrNull()  // take first word
                    onResult(firstName)
                }
                .addOnFailureListener {
                    onResult(null)
                }
        } else {
            onResult(null)
        }
    }

    fun getUserProfile(onResult: (String?, String?, String?, String?, String?, String?) -> Unit) {

        val uid = auth.currentUser?.uid

        if (uid != null) {

            FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener { document ->

                    val name = document.getString("name")
                    val email = document.getString("email")
                    val contactNumber = document.getString("contactNumber")
                    val idNumber = document.getString("idNUmber")
                    val department = document.getString("department")
                    val role = document.getString("role")


                    onResult(name,email,contactNumber,idNumber,department, role)

                }
                .addOnFailureListener {
                    onResult(null, null, null, null, null, null)
                }

        } else {
            onResult(null, null, null, null, null, null)
        }
    }
}