package service.checkout

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import model.CommitDataProvider.buildCommit
import model.FileBlobDataProvider.buildFileBlob
import model.FileTreeEntry
import model.TreeDataProvider.buildTree
import org.junit.Before
import org.junit.Test
import repository.commit.CommitRepository
import repository.head.HeadRepository
import repository.tree.TreeRepository
import repository.workingdirectory.WorkingDirectoryRepository

class CheckoutServiceImplTest {
    private lateinit var workingDirectoryRepository: WorkingDirectoryRepository
    private lateinit var headRepository: HeadRepository
    private lateinit var treeRepository: TreeRepository
    private lateinit var commitRepository: CommitRepository
    private lateinit var checkoutServiceImpl: CheckoutServiceImpl

    @Before
    fun before(){
        workingDirectoryRepository = mockk()
        headRepository = mockk()
        treeRepository = mockk()
        commitRepository = mockk()

        checkoutServiceImpl = CheckoutServiceImpl(
            workingDirectoryRepository = workingDirectoryRepository,
            headRepository = headRepository,
            treeRepository = treeRepository,
            commitRepository = commitRepository
        )
    }

    @Test
    fun `run - calls dependencies`(){
        val blob1 = buildFileBlob(id = "blob-1")
        val blob2 = buildFileBlob(id = "blob-2")
        val tree = buildTree(
            entries = listOf(
                FileTreeEntry(path = blob1.path, fileBlobId = blob1.id),
                FileTreeEntry(path = blob2.path, fileBlobId = blob2.id),
            )
        )

        every { workingDirectoryRepository.clear() } just runs
        every { commitRepository.get(any()) } returns buildCommit(id = "commit-id")
        every { treeRepository.get(any()) } returns tree
        every { workingDirectoryRepository.bringBlob(any()) } just runs
        every { headRepository.setHead(any()) } just runs

        checkoutServiceImpl.run("commit-id")

        verify { workingDirectoryRepository.clear() }
        verify { headRepository.setHead("commit-id") }
        verify { workingDirectoryRepository.bringBlob(blob1) }
        verify { workingDirectoryRepository.bringBlob(blob2) }
    }
}