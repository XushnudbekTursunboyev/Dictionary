package com.example.dictonary.model.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uz: String? = "",
    val en: String? = ""
):Serializable

