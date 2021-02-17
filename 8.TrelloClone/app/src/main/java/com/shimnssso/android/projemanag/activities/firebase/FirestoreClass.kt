package com.shimnssso.android.projemanag.activities.firebase

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shimnssso.android.projemanag.activities.SignUpActivity
import com.shimnssso.android.projemanag.models.User
import com.shimnssso.android.projemanag.utils.Constants

class FirestoreClass {
    private val db = Firebase.firestore

    /**
     * A function to make an entry of the registered user in the firestore database.
     */
    fun registerUser(activity: SignUpActivity, userInfo: User) {

        db.collection(Constants.USERS)
            // Document ID for users fields. Here the document it is the User ID.
            .document(getCurrentUserID())
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                activity.userRegisteredSuccess()
            }
            .addOnFailureListener { e ->
                Log.e(
                    activity.javaClass.simpleName,
                    "Error writing document",
                    e
                )
            }
    }

    /**
     * A function for getting the user id of current logged user.
     */
    fun getCurrentUserID(): String {
        return Firebase.auth.currentUser!!.uid
    }
}