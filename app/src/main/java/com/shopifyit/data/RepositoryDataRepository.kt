package com.shopifyit.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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

    //TODO Pagination

    val allRepositoriesLiveData = MediatorLiveData<List<Repository>>()
    private val remoteRepositories: MutableLiveData<List<Repository>> = MutableLiveData()
    private val localRepositories: LiveData<List<Repository>> = repositoryDao.getAll()

    init {
        allRepositoriesLiveData.addSource(remoteRepositories) { allRepositoriesLiveData.setValue(it) }
        allRepositoriesLiveData.addSource(localRepositories) { allRepositoriesLiveData.setValue(it) }
        loadFromRemote()
    }

    fun loadFromRemote() {
        val service = RepositoriesNetwork.makeRetrofitService()
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val request = service.requestRepositories()
                val repositoriesResponse = request.await()
                if (request.isCompleted) {
                    remoteRepositories.value = repositoriesResponse
                    insertAll(repositoriesResponse)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @WorkerThread
    suspend fun insert(repository: Repository) = repositoryDao.insert(repository)


    @WorkerThread
    suspend fun insertAll(repositories: List<Repository>) = repositoryDao.insertAll(repositories)


    @WorkerThread
    suspend fun deleteAll() = repositoryDao.deleteAll()

}