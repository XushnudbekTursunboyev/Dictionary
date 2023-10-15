package uz.xushnudbek.dictonary.model.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary")
data class WordModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val english: String?,
    val type: String?,
    val transcript: String?,
    val uzbek: String?,
    val countable: String?,
    @ColumnInfo(name = "is_favourite")
    var isFavourite: Int?
)