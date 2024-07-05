package com.example.grocerylist.base.firebase

import android.app.Activity
import android.widget.Toast
import com.example.grocerylist.auth.SignInActivity
import com.example.grocerylist.auth.SignUpActivity
import com.example.grocerylist.model.User
import com.example.grocerylist.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FireBaseRepository {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun getCurrentUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

    fun registerUser(activity: SignUpActivity, userInfo: User) {
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.signUpSuccess()
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Registration Failure!!!.", Toast.LENGTH_SHORT).show()
            }
    }

    fun loadUserData(activity: Activity) {
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { documentSnapShot ->
                val loggedInUser = documentSnapShot.toObject(User::class.java)
                if (loggedInUser != null)
                    when (activity) {
                        is SignInActivity -> {
                            activity.signInSuccess()
                        }
                    }
            }
    }

}