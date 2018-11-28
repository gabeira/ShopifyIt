package com.shopifyit.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shopifyit.data.model.Repository

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM repositories")
    fun getAll(): LiveData<List<Repository>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repositories: List<Repository>)

    @Query("DELETE FROM repositories")
    fun deleteAll()

    @Delete
    fun delete(repository: Repository)
}