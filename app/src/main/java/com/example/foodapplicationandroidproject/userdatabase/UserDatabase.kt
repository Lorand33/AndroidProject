package com.example.foodapplicationandroidproject.userdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodapplicationandroidproject.favourites.Favourites
import com.example.foodapplicationandroidproject.favourites.FavouritesDao
import com.example.foodapplicationandroidproject.userdatabase.model.User

@Database(entities = [User::class, Favourites::class], version = 5, exportSchema = false)
abstract class UserDatabase : RoomDatabase(){
    abstract fun userDao() : UserDao
    abstract fun favoritesDao() : FavouritesDao

    companion object{
        @Volatile
        private var INSTANCE : UserDatabase? = null

        fun getDatabase(context: Context) : UserDatabase{
            val tempInstance = INSTANCE

            if(tempInstance !=null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,UserDatabase::class.java,"user_database").fallbackToDestructiveMigration().allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }

        }
    }
}