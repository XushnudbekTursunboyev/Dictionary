package com.example.dictonary.model.room.dao

import androidx.room.*
import com.example.dictonary.model.room.entity.Word


@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavorite(word: Word): List<Long>

    @Query("select * from favorites")
    fun getFavorites(): List<Word>

    @Delete
    fun delete(word: Word)

    @Update
    fun update(word: Word)

}