package net.corpy.corpyecommerce.database

import android.arch.persistence.room.*
import net.corpy.corpyecommerce.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)
    @Query("DELETE FROM User")
    fun deleteAll()
    @Query("SELECT * FROM User limit 1")
    fun getCurrentUser(): User
}