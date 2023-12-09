package com.example.emptyactivity.DataModels

import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * The class used to interact with the user profile repository on firestore specifically.
 *
 * @property db The reference to the current firestore instance.
 * @property dbProfile A reference to the specific collection used.
 * */
class ProfileRepositoryFirestore(): ProfileRepository {
    val db = Firebase.firestore
    val dbProfile: CollectionReference = db.collection("Profile")
    override suspend fun saveProfile(user: User) {
    val profileId = user._username

        dbProfile.document(profileId).set(user)
            .addOnSuccessListener {
                println("Profile saved")
            }
            .addOnFailureListener{ e ->
                println("Error saving profile: $e")
            }
    }

    override suspend fun getProfile(username: String): Flow<User> = callbackFlow {
       val docref = dbProfile.document(username)

        val subscription = docref.addSnapshotListener{ snapshot, error ->
            if(error != null){
                println("Listen failed: $error")
                return@addSnapshotListener
            }

            if(snapshot != null && snapshot.exists()){
                val user = snapshot.toObject(User::class.java)

                if(user != null){
                    println("Real-time update to profile")
                    trySend(user)
                } else {
                    println("Profile has become null")
                    trySend(User("1","1","1", false, mutableListOf(""), mutableListOf(""), mutableListOf(""), mutableListOf(""), mutableListOf(),100))
                }
            }
        }

        awaitClose{subscription.remove()}
    }

    private fun parseDocumentToUserObject(doc: DocumentSnapshot) : User?{
        var username = doc.get("_username")
        var email = doc.get("_email")
        var followers = doc.get("_followers")
        var following = doc.get("_following")
        var isBanned = doc.get("_isBanned")
        var likedPosts = doc.get("_likedPosts")
        var controversialRating = (doc.get("_maxControversialRating") as Long).toInt()
        var password = doc.get("_password")
        var posts = doc.get("_posts")
        var controversyTypesUnparsed = doc.get("controversialTypes") as List<String>

        var controversialTypesParsed = mutableListOf<Post.ControversialType>()
        controversyTypesUnparsed.forEach { c ->
            controversialTypesParsed.add(Post.ControversialType.valueOf(c))
        }

        return User(email.toString(), username.toString(), password.toString(), isBanned.toString().toBoolean(), followers as MutableList<String>, following as MutableList<String>, posts as MutableList<String>, likedPosts  as MutableList<String>, controversialTypesParsed, controversialRating)
    }

    override suspend fun getProfiles(): Flow<List<User>> = callbackFlow{
        val subscription = dbProfile.addSnapshotListener { snapshot, error ->
            if(error != null){
                println("Listen failed: $error")
                return@addSnapshotListener
            }

            if(snapshot != null){
                var profiles = mutableListOf<User>()

                snapshot.documents.forEach { doc  ->
                    profiles.add(parseDocumentToUserObject(doc)!!)
                }

                if(profiles != null) {
                    println("Real time update to profile")
                    trySend(profiles)
                } else
                    println("Profiles has become null")
                    trySend(listOf<User>())
            } else {
             println("Profiles collection does not exist")
             trySend(listOf<User>())
            }
        }

        awaitClose{subscription.remove()}
    }
}