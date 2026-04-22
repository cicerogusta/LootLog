package com.cicerogusta.lootlog.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cicerogusta.lootlog.data.model.CollectibleItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectibleItemDao {
    @Insert
    suspend fun insert(item: CollectibleItem): Long

    @Update
    suspend fun update(item: CollectibleItem)

    @Delete
    suspend fun delete(item: CollectibleItem)

    @Query("SELECT * FROM collectible_items ORDER BY createdAt DESC")
    fun getAllItems(): Flow<List<CollectibleItem>>

    @Query("SELECT * FROM collectible_items WHERE id = :id")
    suspend fun getItemById(id: Long): CollectibleItem?

    @Query("SELECT COUNT(*) FROM collectible_items")
    fun getItemCount(): Flow<Int>

    @Query("DELETE FROM collectible_items")
    suspend fun deleteAll()
}
