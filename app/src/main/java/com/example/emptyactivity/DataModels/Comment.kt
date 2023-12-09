package com.example.emptyactivity.DataModels

/**
 * A container to represent the data a comment holds.
 *
 * @property _id The individual comment id.
 * @property _userId The id of the user that posted the comment(typically the username).
 * @property _postId The id of the post that the comment was made on.
 * @property _comment The actual contents of the comment.
 * */
public class Comment(val _id: String, val _userId: String, val _postId: String, val _comment: String) {

}