package service.add

import directory.DataProvider.buildRoot
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import model.FileBlob
import model.FileBlobId
import org.junit.Before
import org.junit.Test
import repository.blob.FileBlobRepository
import repository.index.IndexRepository

internal class AddServiceImplTest {
    private var root = buildRoot()
    private lateinit var fileBlobRepository: FileBlobRepository
    private lateinit var indexRepository: IndexRepository
    private lateinit var addService: AddService

    @Before
    fun beforeEach() {
        fileBlobRepository = mockk()
        indexRepository = mockk()
        addService = AddServiceImpl(
            root = root,
            fileBlobRepository = fileBlobRepository,
            indexRepository = indexRepository,
        )
    }

    @Test
    fun `run - calls index repository`() {
        val stagedFiles = listOf("file1.kt")
        val blobId = FileBlobId.from("blob-id")

        every { fileBlobRepository.createIfNotExists(any()) } returns blobId
        every { indexRepository.add(any()) } just runs

        addService.run(stagedFiles)

        val expectedFileBlob = FileBlob(path = "file1.kt", id = blobId)
        verify { indexRepository.add(listOf(expectedFileBlob)) }
    }
}
