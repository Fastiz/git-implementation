package service

import dao.objects.files.FileDao
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import service.init.InitServiceImpl

class InitServiceImplTest {
    private lateinit var fileDao: FileDao
    private lateinit var initServiceImpl: InitServiceImpl

    @Before
    fun before() {
        fileDao = mockk()
        initServiceImpl = InitServiceImpl(fileDao = fileDao)
    }

    @Test
    fun `creates objects directory`(){
        every { fileDao.createDirectory(any()) } just runs

        initServiceImpl.run()

        verify { fileDao.createDirectory(".git-fastiz/objects") }
    }
}