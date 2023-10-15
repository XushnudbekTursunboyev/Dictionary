package uz.xushnudbek.dictonary.model.room.helper

import androidx.room.Dao
import androidx.room.Query

@Dao
interface CategoryDao {

    @Query("select * from category")
    fun getAllCategory():List<CategoryEntity>
}