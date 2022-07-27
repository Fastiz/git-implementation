package repository.head

import dao.objects.head.HeadDao
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class HeadRepositoryImplTest {
    private lateinit var headDao: HeadDao
    private lateinit var headRepositoryImpl: HeadRepositoryImpl

    @Before
    fun before() {
        headDao = mockk()
        headRepositoryImpl = HeadRepositoryImpl(headDao)
    }

    @Test
    fun `getHead - retrieves the ref commit hash`(){
        every { headDao.getHead() } returns "ref: commit-id"

        val result = headRepositoryImpl.getHead()

        assertEquals("commit-id", result)
    }
}