package com.shopifyit.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shopifyit.data.model.Repository
import com.shopifyit.data.retrofit.RepositoriesNetwork
import com.shopifyit.data.room.RepositoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Singleton
class RepositoryDataRepository(private val repositoryDao: RepositoryDao) {

    private var lastRequestedPage = 1
    val allRepositoriesLiveData: LiveData<List<Repository>> = repositoryDao.getAll()
    val networkErrors = MutableLiveData<String>()

    init {
        loadMoreFromRemote()
    }

    fun loadMoreFromRemote() {
        val service = RepositoriesNetwork.makeRetrofitService()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val request = service.requestRepositories(lastRequestedPage, NETWORK_PAGE_SIZE)
                val repositoriesResponse = request.await()
                if (request.isCompleted) {
                    repositoryDao.insert(repositoriesResponse)
                    lastRequestedPage++
                }
            } catch (e: Exception) {
                e.printStackTrace()
                networkErrors.postValue(e.message)
            }
        }
    }

    @WorkerThread
    suspend fun saveToLocalDatabase(repositories: List<Repository>) = repositoryDao.insert(repositories)

    @WorkerThread
    suspend fun deleteAll() = repositoryDao.deleteAll()

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }
}