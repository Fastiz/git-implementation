package service.add

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import repository.index.IndexRepository

internal class AddServiceImplTest {
    private lateinit var indexRepository: IndexRepository
    private lateinit var addService: AddService

    @BeforeEach
    fun beforeEach() {
        indexRepository = mockk()
        addService = AddServiceImpl(
            indexRepository = indexRepository
        )
    }

    @Test
    fun `run - calls index repository`() {
        val stagedFiles = listOf("file1.kt", "file2.kt")

        every { indexRepository.add(any()) } just runs

        addService.run(stagedFiles)

        verify { indexRepository.add(stagedFiles) }
    }
}