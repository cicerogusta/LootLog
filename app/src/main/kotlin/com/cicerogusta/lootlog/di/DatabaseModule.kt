package com.cicerogusta.lootlog.di

import android.content.Context
import androidx.room.Room
import com.cicerogusta.lootlog.data.dao.CollectibleItemDao
import com.cicerogusta.lootlog.data.database.LootLogDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideLootLogDatabase(
        @ApplicationContext context: Context
    ): LootLogDatabase {
        return Room.databaseBuilder(
            context,
            LootLogDatabase::class.java,
            "lootlog_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideCollectibleItemDao(
        database: LootLogDatabase
    ): CollectibleItemDao {
        return database.collectibleItemDao()
    }
}
