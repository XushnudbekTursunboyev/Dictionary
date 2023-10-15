package uz.xushnudbek.dictonary.model.room.dao

import androidx.room.*
import uz.xushnudbek.dictonary.model.room.entity.WordModel

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListWord(list: List<WordModel>): List<Long>

    @Query("select * from dictionary")
    fun getWords(): List<WordModel>

    @Query("select * from dictionary where dictionary.is_favourite=1")
    fun getFavorite(): List<WordModel>

    @Query("SELECT * FROM dictionary WHERE length(english) = 3 AND english LIKE :word || '%'")
    fun getNearestWordsEng(word:String):List<WordModel>


    @Delete
    fun delete(word: WordModel)

    @Update
    fun update(word: WordModel)

}