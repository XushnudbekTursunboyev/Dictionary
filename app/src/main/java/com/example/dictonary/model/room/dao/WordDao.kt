package com.example.dictonary.model.room.dao

import androidx.room.*
import com.example.dictonary.model.room.entity.Word

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListWord(list: List<Word>): List<Long>

    @Query("select * from words")
    fun getUsers(): List<Word>

    @Delete
    fun delete(wordModel: Word)

    @Update
    fun update(wordModel: Word)

}