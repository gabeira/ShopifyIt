package com.shopifyit

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.shopifyit.data.model.Repository
import com.shopifyit.data.room.AppDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class DatabaseInstrumentedTest {

    private lateinit var database: AppDatabase
    private lateinit var appContext: Context

    @Before
    fun setup() {
        appContext = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
    }

    @Test
    fun insertAndGetRepositories() {
        val repo1 = Repository(1, "Test1", "http://", "2018-05-17T02:00:00Z", 5, true)
        val repo2 = Repository(2, "Test2", "http://", "2018-05-27T02:00:00Z", 5, false)

        database.repositoryDao().insert(listOf(repo1, repo2))

        database.repositoryDao().getAll().value?.forEachIndexed { index, repository ->
            assertEquals(index + 1, repository.id)
        }
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        database.close()
    }
}