package com.delivery.quickie.network

data class ProfileResponse(
	val profile: String? = null,
	val posts: List<String>? = null,
	val status: Boolean? = null,
	val message: String? = null,
	val username: String? = null
)

