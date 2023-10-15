package uz.xushnudbek.dictonary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dictonary.R
import uz.xushnudbek.dictonary.model.room.helper.RoomDbHelper
import uz.xushnudbek.dictonary.model.room.helper.RoomMax

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Log.d("TTT", "onCreate: ${RoomMax.DatabaseBuilder.getInstance(this).wordService().getAllCategory()}")

    }
}