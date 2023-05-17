package com.aiiksveryown.previouslyon.util.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aiiksveryown.previouslyon.feature.tv_show.data.database.converter.TvShowConverter
import com.aiiksveryown.previouslyon.feature.tv_show.data.database.dao.TvShowDao
import com.aiiksveryown.previouslyon.feature.tv_show.data.database.entity.TvShowEntity
import com.aiiksveryown.previouslyon.util.Constants.DATABASE_VERSION

@Database(
    entities = [
        TvShowEntity::class,
        TvShowEntity.TvShowGenreEntity::class,
        TvShowEntity.TvShowCastEntity::class,
        TvShowEntity.TvShowEpisodeEntity::class
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(TvShowConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tvShowDao(): TvShowDao

}