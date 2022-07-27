package repository.commit

import dao.objects.ObjectsDao
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class CommitRepositoryImplTest {
    private lateinit var objectsDao: ObjectsDao
    private lateinit var commitRepositoryImpl: CommitRepositoryImpl

    @Before
    fun before(){
        objectsDao = mockk()
        commitRepositoryImpl = CommitRepositoryImpl(objectsDao)
    }

    @Test
    fun `create - calls objects dao`(){
        every { objectsDao.createFromString(any()) } returns "commit-id"

        val result = commitRepositoryImpl.create("tree-id", "parent-id", "")

        verify { objectsDao.createFromString(any()) }
        assertEquals("commit-id", result)
    }
}