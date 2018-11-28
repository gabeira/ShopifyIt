package com.shopifyit.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.shopifyit.data.model.Repository
import com.shopifyit.data.retrofit.RepositoriesNetwork
import com.shopifyit.data.room.RepositoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Singleton
class RepositoryDataRepository(private val repositoryDao: RepositoryDao) {

    //TODO Pagination
    val allRepositoriesLiveData: LiveData<List<Repository>> = repositoryDao.getAll()

    init {
        loadFromRemote()
    }

    fun loadFromRemote() {
        val service = RepositoriesNetwork.makeRetrofitService()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val request = service.requestRepositories()
                val repositoriesResponse = request.await()
                if (request.isCompleted) {
                    repositoryDao.insert(repositoriesResponse)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @WorkerThread
    suspend fun saveToLocalDatabase(repositories: List<Repository>) = repositoryDao.insert(repositories)

    @WorkerThread
    suspend fun deleteAll() = repositoryDao.deleteAll()
}