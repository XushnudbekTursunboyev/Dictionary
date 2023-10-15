package uz.xushnudbek.dictonary.model.room.helper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.xushnudbek.dictonary.model.room.dao.WordDao
import uz.xushnudbek.dictonary.model.room.entity.WordModel

@Database(
    entities = [WordModel::class],
    version = 3
)
abstract class RoomDbHelper : RoomDatabase() {

    abstract fun wordService(): WordDao

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
                "SmartDictionary.db"
            )
                .createFromAsset("dictionary.db")
                .allowMainThreadQueries()
                .build()

    }
}