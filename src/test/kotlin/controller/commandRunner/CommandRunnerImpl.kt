package controller.commandRunner

import service.commit.CommitService
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class CommandRunnerImplTestTest {
    lateinit var commit: CommitService
    lateinit var commandRunnerImpl: CommandRunnerImpl

    @Before
    fun before(){
        commit = mockk()
        commandRunnerImpl = CommandRunnerImpl(commit = commit)
    }

    @Test
    fun `commit calls commit command`(){
        val args = listOf("commit", "a", "b").toTypedArray()

        every { commit.run(any()) } just runs

        commandRunnerImpl.runCommit(args)

        verify { commit.run(listOf("a", "b")) }
    }
}