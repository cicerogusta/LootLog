package com.cicerogusta.lootlog.domain.model

data class SubscriptionState(
    val isPremium: Boolean = false,
    val itemCount: Int = 0,
    val canAddMoreItems: Boolean = true,
    val freeItemLimit: Int = 15
) {
    val remainingItems: Int = if (isPremium) Int.MAX_VALUE else (freeItemLimit - itemCount)
}
