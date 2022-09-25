package service.commit.step

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import logger.TestLogger
import model.CommitId
import org.junit.Before
import org.junit.Test
import repository.head.HeadRepository
import repository.ref.RefRepository

class MoveHeadTest {
    private lateinit var refRepository: RefRepository
    private lateinit var headRepository: HeadRepository
    private var logger = TestLogger()

    private lateinit var moveHead: MoveHead

    @Before
    fun before() {
        refRepository = mockk()
        headRepository = mockk()
        moveHead = MoveHead(
            refRepository = refRepository,
            headRepository = headRepository,
            logger = logger
        )
    }

    @Test
    fun `calls head repository`() {
        val input = CreateCommitOutput(commitId = CommitId.from("commit-id"))

        every { headRepository.setHead(any()) } just runs
        every { headRepository.getHead() } returns null

        moveHead.execute(input)

        verify { headRepository.setHead("commit-id") }
    }
}
