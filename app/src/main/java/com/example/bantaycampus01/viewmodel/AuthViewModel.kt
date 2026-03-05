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
}