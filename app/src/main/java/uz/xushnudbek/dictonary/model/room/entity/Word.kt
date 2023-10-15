package uz.xushnudbek.dictonary.model.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uz: String = "",
    val en: String = "",
    var favorite: Boolean = false,
    val type: String? = ""
) : Serializable

