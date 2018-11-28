package com.shopifyit.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.shopifyit.data.RepositoryDataRepository
import com.shopifyit.data.model.Repository
import com.shopifyit.data.room.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RepositoryViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repositoryDataRepository: RepositoryDataRepository
    val repositoryList: LiveData<List<Repository>>

    init {
        val repositoryDao = AppDatabase.getDatabase(application, scope).repositoryDao()
        repositoryDataRepository = RepositoryDataRepository(repositoryDao)
        repositoryList = repositoryDataRepository.allRepositoriesLiveData
    }

    fun insert(item: Repository) = scope.launch(Dispatchers.IO) {
        repositoryDataRepository.insert(item)
    }

    fun insertAll(list: List<Repository>) = scope.launch(Dispatchers.IO) {
        repositoryDataRepository.insertAll(list)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun reload() {
        repositoryDataRepository.loadFromRemote()
    }
}