package service.step

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import model.CommitDataProvider.buildCommit
import model.FileBlob
import model.TreeDataProvider.buildTree
import org.junit.Before
import org.junit.Test
import repository.blob.FileBlobRepository
import repository.commit.CommitRepository
import repository.head.HeadRepository
import repository.tree.TreeRepository
import service.commit.step.CreateFileBlobsIfNotExist
import service.commit.step.CreateFileBlobsIfNotExistInput
import kotlin.test.assertEquals

class CreateFileBlobsIfNotExistTest {
    private lateinit var treeRepository: TreeRepository
    private lateinit var commitRepository: CommitRepository
    private lateinit var headRepository: HeadRepository
    private lateinit var fileBlobRepository: FileBlobRepository
    private lateinit var createFileBlobsIfNotExist: CreateFileBlobsIfNotExist

    @Before
    fun before() {
        treeRepository = mockk()
        commitRepository = mockk()
        headRepository = mockk()
        fileBlobRepository = mockk()
        createFileBlobsIfNotExist = CreateFileBlobsIfNotExist(
            treeRepository = treeRepository,
            commitRepository = commitRepository,
            headRepository = headRepository,
            fileBlobRepository = fileBlobRepository
        )
    }

    @Test
    fun `calls blob repository once for each staged file and returns the correct output`() {
        val stagedFiles = listOf("file-1.kt", "file-2.kt")
        val input = CreateFileBlobsIfNotExistInput(stagedFiles = stagedFiles)
        val currentCommit = buildCommit(
            id = "current-commit-id"
        )
        val currentTree = buildTree()

        every { headRepository.getHead() } returns "current-commit-id"
        every { commitRepository.get(any()) } returns currentCommit
        every { treeRepository.get(any()) } returns currentTree
        for (file in stagedFiles) {
            every { fileBlobRepository.createIfNotExists(file) } returns "$file-id"
        }

        val result = createFileBlobsIfNotExist.execute(input)

        for (file in stagedFiles) {
            verify { fileBlobRepository.createIfNotExists(file) }
        }

        assertEquals("current-commit-id", result.commitId)
        assertEquals(currentTree, result.currentTree)
        assertEquals(
            listOf(
                FileBlob(path = "file-1.kt", id = "file-1.kt-id"),
                FileBlob(path = "file-2.kt", id = "file-2.kt-id"),
            ),
            result.fileBlobList
        )
    }
}