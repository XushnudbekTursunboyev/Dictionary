package uz.xushnudbek.dictonary.model.room.helper

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id :Int,
    val titleUz:String,
    val titleRu:String,
    val titleEn:String,
)