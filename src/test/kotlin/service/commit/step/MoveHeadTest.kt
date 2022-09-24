package service.commit.step

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import logger.TestLogger
import org.junit.Before
import org.junit.Test
import repository.head.HeadRepository

class MoveHeadTest {
    private lateinit var headRepository: HeadRepository
    private var logger = TestLogger()

    private lateinit var moveHead: MoveHead

    @Before
    fun before() {
        headRepository = mockk()
        moveHead = MoveHead(headRepository, logger)
    }

    @Test
    fun `calls head repository`() {
        val input = CreateCommitOutput(commitId = "commit-id")

        every { headRepository.setHead(any()) } just runs

        moveHead.execute(input)

        verify { headRepository.setHead("commit-id") }
    }
}