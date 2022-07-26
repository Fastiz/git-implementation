package service.step

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import repository.head.HeadRepository
import service.commit.step.CreateCommitOutput
import service.commit.step.MoveHead

class MoveHeadTest {
    private lateinit var headRepository: HeadRepository
    private lateinit var moveHead: MoveHead

    @Before
    fun before(){
        headRepository = mockk()
        moveHead = MoveHead(headRepository)
    }

    @Test
    fun `calls head repository`() {
        val input = CreateCommitOutput(commitId = "commit-id")

        every { headRepository.setHead(any()) } just runs

        moveHead.execute(input)

        verify { headRepository.setHead("commit-id") }
    }
}