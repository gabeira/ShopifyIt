package com.shopifyit

import com.shopifyit.data.model.Repository
import com.shopifyit.domain.getSortedRepositories
import com.shopifyit.domain.parseIsoDate
import com.shopifyit.domain.toStringDateFormat
import com.shopifyit.domain.toStringYesOrNo
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class FormatExtensionsUnitTest {

    var repos = mutableListOf<Repository>()

    @Before
    fun setup() {
        repos.add(Repository(1, "Test1", "http://", "2018-05-17T02:00:00Z", 5, true))
        repos.add(Repository(2, "Test2", "http://", "2018-05-27T02:00:00Z", 5, true))
    }

    @Test
    fun parseIsoDate() {
        assertEquals(1525622400000L, "2018-05-07T02:00:00Z".parseIsoDate().time)
    }

    @Test
    fun stringDateToFormat() {
        assertEquals("May 7 2018 - 02:33:44", "2018-05-07T02:33:44Z".parseIsoDate().toStringDateFormat())
    }

    @Test
    fun parseIsoDateError() {
        try {
            "2018-11-27".parseIsoDate().time
        } catch (e: Exception) {
            assertEquals("Unparseable date: \"2018-11-27\"", e.message)
        }
    }

    @Test
    fun booleanToStringYes() {
        assertEquals("Y", true.toStringYesOrNo())
    }

    @Test
    fun booleanToStringNo() {
        assertEquals("N", false.toStringYesOrNo())
    }

    @Test
    fun sortedRepositories() {
        assertEquals(2, repos.getSortedRepositories().first().id)
    }
}