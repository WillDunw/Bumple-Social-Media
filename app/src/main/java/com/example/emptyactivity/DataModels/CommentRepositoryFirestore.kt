package com.example.emptyactivity.DataModels


import android.annotation.SuppressLint
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

const val collectionNameComments = "Comments"

class CommentRepositoryFirestore() : CommentRepository{
    val db = Firebase.firestore


    override suspend fun saveComment(comment: Comment): Boolean {
        var success = false;

        val hashedComment = hashMapOf(
            "username" to comment._userId,
            "postId" to comment._postId,
            "comment" to comment._comment
        )

        db.collection(collectionNameComments)
            .add(hashedComment)
            .addOnSuccessListener {
                success = true
            }
            .addOnFailureListener{e ->
                Log.e(null,e.message!!)
            }

        return success
    }

    @SuppressLint("NewApi")
    override suspend fun getAllComments(): Flow<List<Comment>> = callbackFlow {
        val subscription = db.collection(collectionNameComments).addSnapshotListener { snapshot, error ->
            if (error != null) {
                println("Listen failed: $error")
                return@addSnapshotListener
            }
            if (snapshot != null) {
                var list = mutableListOf<Comment>()
                snapshot.documents.forEach { document ->
                    list.add(getAllCommentsFromDocument(document)!!)
                }
                if (list != null) {
                    println("real time update to posts")
                    trySend(list)
                } else {
                    println("Posts has become null")
                    trySend(listOf<Comment>())
                }
            } else {
                println("Posts collection does not exist")
                trySend(listOf<Comment>())
            }
        }

        awaitClose { subscription.remove() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAllCommentsFromDocument(doc: DocumentSnapshot) : Comment
    {
        var id = doc.id
        var userId = doc.get("username").toString()
        var postId = doc.get("postId").toString()
        var comment = doc.get("comment").toString()


        return Comment(id, userId, postId, comment);
    }

    override suspend fun delete(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun commentOnPost(postId: String, comment: Comment) {
        db.collection(collectionName).document(comment._id).update("comments", comment._postId)
    }
}