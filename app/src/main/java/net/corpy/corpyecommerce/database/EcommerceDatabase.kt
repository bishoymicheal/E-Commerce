package net.corpy.corpyecommerce.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import net.corpy.corpyecommerce.model.User

@Database(entities = [User::class], version = 1,exportSchema = false)
abstract class EcommerceDatabase : RoomDatabase(){
    abstract fun userDao():UserDao
    companion object {
        var INSTANCE: EcommerceDatabase? = null
        fun getAppDataBase(context: Context): EcommerceDatabase? {
            if (INSTANCE == null){
                synchronized(EcommerceDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            EcommerceDatabase::class.java, "EcommerceDatabase").build()
                }
            }
            return INSTANCE
        }
        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}