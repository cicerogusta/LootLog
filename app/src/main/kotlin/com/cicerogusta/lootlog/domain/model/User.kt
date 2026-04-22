package com.cicerogusta.lootlog.domain.model

data class User(
    val id: String,
    val email: String,
    val displayName: String? = null,
    val photoUrl: String? = null,
    val isPremium: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
