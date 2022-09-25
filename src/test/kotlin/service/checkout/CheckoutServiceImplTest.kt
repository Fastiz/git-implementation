package service.checkout

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import model.CommitDataProvider.buildCommit
import model.CommitId
import model.FileBlobDataProvider.buildFileBlob
import model.FileBlobId
import model.FileTreeEntry
import model.TreeDataProvider.buildTree
import org.junit.Before
import org.junit.Test
import repository.commit.CommitRepository
import repository.head.HeadRepository
import repository.index.IndexRepository
import repository.ref.RefRepository
import repository.tree.TreeRepository
import repository.workingdirectory.WorkingDirectoryRepository

class CheckoutServiceImplTest {
    private lateinit var refRepository: RefRepository
    private lateinit var workingDirectoryRepository: WorkingDirectoryRepository
    private lateinit var headRepository: HeadRepository
    private lateinit var treeRepository: TreeRepository
    private lateinit var commitRepository: CommitRepository
    private lateinit var indexRepository: IndexRepository
    private lateinit var checkoutServiceImpl: CheckoutServiceImpl

    @Before
    fun before() {
        refRepository = mockk()
        workingDirectoryRepository = mockk()
        headRepository = mockk()
        treeRepository = mockk()
        commitRepository = mockk()
        indexRepository = mockk()

        checkoutServiceImpl = CheckoutServiceImpl(
            refRepository = refRepository,
            workingDirectoryRepository = workingDirectoryRepository,
            headRepository = headRepository,
            treeRepository = treeRepository,
            commitRepository = commitRepository,
            indexRepository = indexRepository
        )
    }

    @Test
    fun `run - calls dependencies`() {
        val blob1 = buildFileBlob(id = FileBlobId.from("blob-1"))
        val blob2 = buildFileBlob(id = FileBlobId.from("blob-2"))
        val tree = buildTree(
            entries = listOf(
                FileTreeEntry(path = blob1.path, fileBlobId = blob1.id),
                FileTreeEntry(path = blob2.path, fileBlobId = blob2.id),
            )
        )
        val commitId = CommitId.from("commit-id")

        every { refRepository.get(any()) } returns null
        every { workingDirectoryRepository.clear() } just runs
        every { commitRepository.get(any()) } returns buildCommit(id = commitId)
        every { treeRepository.get(any()) } returns tree
        every { workingDirectoryRepository.bringBlob(any()) } just runs
        every { headRepository.setHead(any()) } just runs
        every { indexRepository.set(any()) } just runs

        checkoutServiceImpl.run("commit-id")

        verify { workingDirectoryRepository.clear() }
        verify { headRepository.setHead(commitId) }
        verify { workingDirectoryRepository.bringBlob(blob1) }
        verify { workingDirectoryRepository.bringBlob(blob2) }
        verify { indexRepository.set(any()) }
    }
}
