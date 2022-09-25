package service.init

import dao.files.FileDao
import directory.DataProvider.buildHead
import directory.DataProvider.buildIndex
import directory.DataProvider.buildObjects
import directory.DataProvider.buildRefs
import directory.DataProvider.buildRefsHeads
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class InitServiceImplTest {
    private val objects = buildObjects()
    private val head = buildHead()
    private val index = buildIndex()
    private val refs = buildRefs()
    private val refsHeads = buildRefsHeads()
    private lateinit var fileDao: FileDao
    private lateinit var initServiceImpl: InitServiceImpl

    @Before
    fun before() {
        fileDao = mockk()
        initServiceImpl = InitServiceImpl(
            refs = refs,
            refsHeads = refsHeads,
            objects = objects,
            head = head,
            index = index,
            fileDao = fileDao
        )
    }

    @Test
    fun `creates objects directory`() {
        every { fileDao.createDirectory(any()) } just runs
        every { fileDao.createFile(any()) } just runs

        initServiceImpl.run()

        verify { fileDao.createDirectory(objects.path) }
    }
}
