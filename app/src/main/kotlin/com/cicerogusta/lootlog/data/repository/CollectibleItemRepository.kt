package com.cicerogusta.lootlog.data.repository

import com.cicerogusta.lootlog.data.dao.CollectibleItemDao
import com.cicerogusta.lootlog.data.model.CollectibleItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CollectibleItemRepository @Inject constructor(
    private val collectibleItemDao: CollectibleItemDao
) {
    fun getAllItems(): Flow<List<CollectibleItem>> = collectibleItemDao.getAllItems()

    fun getItemCount(): Flow<Int> = collectibleItemDao.getItemCount()

    suspend fun insertItem(item: CollectibleItem): Long = collectibleItemDao.insert(item)

    suspend fun updateItem(item: CollectibleItem) = collectibleItemDao.update(item)

    suspend fun deleteItem(item: CollectibleItem) = collectibleItemDao.delete(item)

    suspend fun getItemById(id: Long): CollectibleItem? = collectibleItemDao.getItemById(id)

    suspend fun deleteAllItems() = collectibleItemDao.deleteAll()
}
