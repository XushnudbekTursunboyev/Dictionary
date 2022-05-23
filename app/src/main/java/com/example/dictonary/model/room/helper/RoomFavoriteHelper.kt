package com.example.dictonary.model.room.helper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dictonary.model.room.dao.FavoriteDao
import com.example.dictonary.model.room.dao.WordDao
import com.example.dictonary.model.room.entity.Word

@Database(
    entities = [Word::class],
    version = 1,
    exportSchema = false
)
abstract class RoomFavoriteHelper : RoomDatabase() {

    //abstract fun wordService(): WordDao
    abstract fun favoriteService(): FavoriteDao

    object DatabaseBuilder {

        private var instance: RoomDbHelper? = null
        fun getInstance(context: Context): RoomDbHelper {
            if (instance == null) {
                synchronized(this) {
                    instance = buildRoomDb(context)
                }
            }
            return instance!!
        }

        private fun buildRoomDb(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RoomDbHelper::class.java,
                "room_favorite.db"
            )
                .allowMainThreadQueries()
                .build()

    }
}