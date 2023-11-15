package com.example.emptyactivity.DataModels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.time.LocalDateTime

const val collectionName = "Posts"

class PostRepositoryFirestore() : PostRepository{
    val db = Firebase.firestore

    override suspend fun savePost(post : Post) : Boolean{
        var success = false;

        val hashedPost = hashMapOf(
            "username" to post._username,
            "postDate" to post._postDate.toString(),
            "title" to post._title,
            "content" to post._content,
            "likes" to post._likes,
            "controversialRating" to post._controversialRating,
            "controversialType" to post._controversialType
        )

        db.collection(collectionName)
            .add(hashedPost)
            .addOnSuccessListener {
                success = true
            }
            .addOnFailureListener{e ->
                Log.e(null,e.message!!)
            }

        return success
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAllPosts(): Flow<List<Post>> = callbackFlow {

        val subscription = db.collection(collectionName).addSnapshotListener { snapshot, error ->
            if (error != null) {
                println("Listen failed: $error")
                return@addSnapshotListener
            }
            if (snapshot != null) {
                var list = mutableListOf<Post>()
                snapshot.documents.forEach { document ->
                    list.add(getPostFromDocument(document)!!)
                }
                if (list != null) {
                    println("real time update to posts")
                    trySend(list)
                } else {
                    println("Posts has become null")
                    trySend(listOf<Post>())
                }
            } else {
                println("Posts collection does not exist")
                trySend(listOf<Post>())
            }
        }

        awaitClose { subscription.remove() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getPostFromDocument(doc: DocumentSnapshot) : Post?
    {
        var id = doc.id
        var username = doc.get("username")
        var test = doc.get("postDate")
        var postDate= LocalDateTime.parse(doc.get("postDate").toString())
        var title = doc.get("title")
        var content = doc.get("content")
        var likes = doc.get("likes")
        var controversialRating= doc.get("controversialRating") as Long
        var controversialRatingParsed = controversialRating.toInt()
        var controversialType = Post.ControversialType.valueOf(doc.get("controversialType").toString())

        return Post(id, username.toString(), postDate, title.toString(), content.toString(),
            likes as MutableList<String>, controversialRatingParsed, controversialType)
    }

    override suspend fun likePost(post: Post) {
        db.collection(collectionName).document(post._id).update("likes", post._likes)
    }

    override suspend fun delete(id: String) {
        db.collection(collectionName).document(id).delete()
    }
}