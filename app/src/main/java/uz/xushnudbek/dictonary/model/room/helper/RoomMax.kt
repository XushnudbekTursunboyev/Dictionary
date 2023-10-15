package uz.xushnudbek.dictonary.model.room.helper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.xushnudbek.dictonary.model.room.dao.WordDao
import uz.xushnudbek.dictonary.model.room.entity.WordModel

@Database(
    entities = [CategoryEntity::class],
    version = 3
)
abstract class RoomMax : RoomDatabase() {

    abstract fun wordService(): CategoryDao

    object DatabaseBuilder {

        private var instance: RoomMax? = null
        fun getInstance(context: Context): RoomMax {
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
                RoomMax::class.java,
                "categories.db"
            )
                .createFromAsset("categories.db")
                .allowMainThreadQueries()
                .build()

    }
}