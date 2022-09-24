package service.commit.step

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logger.TestLogger
import model.FileBlob
import model.FileBlobId
import org.junit.Before
import org.junit.Test
import repository.blob.FileBlobRepository
import kotlin.test.assertEquals

class CreateFileBlobsIfNotExistTest {
    private lateinit var fileBlobRepository: FileBlobRepository
    private var logger = TestLogger()

    private lateinit var createFileBlobsIfNotExist: CreateFileBlobsIfNotExist

    @Before
    fun before() {
        fileBlobRepository = mockk()
        createFileBlobsIfNotExist = CreateFileBlobsIfNotExist(
            fileBlobRepository = fileBlobRepository,
            logger = logger
        )
    }

    @Test
    fun `calls blob repository once for each staged file and returns the correct output`() {
        val stagedFiles = listOf("file-1.kt", "file-2.kt")
        val input = CreateFileBlobsIfNotExistInput(stagedFiles = stagedFiles)

        for (file in stagedFiles) {
            every { fileBlobRepository.createIfNotExists(file) } returns FileBlobId.from("$file-id")
        }

        val result = createFileBlobsIfNotExist.execute(input)

        for (file in stagedFiles) {
            verify { fileBlobRepository.createIfNotExists(file) }
        }

        assertEquals(
            listOf(
                FileBlob(path = "file-1.kt", id = FileBlobId.from("file-1.kt-id")),
                FileBlob(path = "file-2.kt", id = FileBlobId.from("file-2.kt-id")),
            ),
            result.fileBlobList
        )
    }
}