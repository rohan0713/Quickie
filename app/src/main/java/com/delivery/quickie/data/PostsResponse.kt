package com.delivery.quickie.data

data class PostsResponse(
	val posts: List<PostsItem>
)

data class CommentsItem(
	val name: String? = null,
	val comment: String? = null
)

data class PostsItem(
	val postImg: String? = null,
	val comments: List<CommentsItem>,
	val caption: String? = null,
	val location: String? = null,
	val userImg: String? = null,
	val username: String? = null,
	val likes: Int? = null
)

