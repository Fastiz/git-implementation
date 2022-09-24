package repository.head

import dao.files.FileDao
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class HeadRepositoryImplTest {
    private lateinit var fileDao: FileDao
    private lateinit var headRepositoryImpl: HeadRepositoryImpl

    @Before
    fun before() {
        fileDao = mockk()
        headRepositoryImpl = HeadRepositoryImpl(fileDao)
    }

    @Test
    fun `getHead - retrieves the ref commit hash`() {
        TODO()
    }
}