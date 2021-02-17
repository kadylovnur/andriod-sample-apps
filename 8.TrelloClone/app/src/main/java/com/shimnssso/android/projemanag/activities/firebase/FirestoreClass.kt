package com.shimnssso.android.projemanag.activities.firebase

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shimnssso.android.projemanag.activities.SignInActivity
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

    // TODO (Step 1: Create a function to SignIn using firebase and get the user details from Firestore Database.)
    // START
    /**
     * A function to SignIn using firebase and get the user details from Firestore Database.
     */
    fun signInUser(activity: SignInActivity) {

        // Here we pass the collection name from which we wants the data.
        db.collection(Constants.USERS)
            // The document id to get the Fields of user.
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.e(
                    activity.javaClass.simpleName, document.toString()
                )

                // Here we have received the document snapshot which is converted into the User Data model object.
                val loggedInUser = document.toObject(User::class.java)!!

                // Here call a function of base activity for transferring the result to it.
                activity.signInSuccess(loggedInUser)
            }
            .addOnFailureListener { e ->
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting loggedIn user details",
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