package com.example.bantaycampus01.model

data class UserModel(
    /*
    *FOR REGISTRATION OR ACCOUNT CREATION!!
    * Add all the models you need for the user:
    * Name, email, DOB, etc.
    *
    * NOTE: Don't edit uid. Nullable
    */
    val name: String,
    val email: String,
    val contactNumber: String,
    val idNumber: String,
    val department: String,
    val dob: String,

    val uid: String
)