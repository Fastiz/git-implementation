package repository.index

import dao.files.FileDao
import dao.files.Reader
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import model.File
import model.FileBlob
import model.FileBlobId
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class IndexRepositoryImplTest {
    private lateinit var fileDao: FileDao
    private lateinit var indexRepository: IndexRepository

    @BeforeEach
    fun beforeEach() {
        fileDao = mockk()
        indexRepository = IndexRepositoryImpl(
            fileDao = fileDao
        )
    }

    @Test
    fun `add - writes to index`() {
        val indexedLines = listOf(
            "file1-id file1-path",
        )

        every { fileDao.readFile(any(), any<Reader.() -> Sequence<String>>()) } returns indexedLines.asSequence()
        every { fileDao.writeFile(any(), any()) } just runs

        val fileBlobList = listOf(FileBlob(path = "file1-path", id = FileBlobId.from("file1-id")))
        indexRepository.add(fileBlobList)

        verify { fileDao.writeFile(File.INDEX.path, any()) }
    }
}
