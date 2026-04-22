package com.cicerogusta.lootlog.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collectible_items")
data class CollectibleItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val rarity: String,
    val pricePaid: Double,
    val imageUrl: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
