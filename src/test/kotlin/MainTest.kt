import controller.commandRunner.CommandRunner
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

class MainTest {
    lateinit var commandRunner: CommandRunner
    lateinit var main: Main

    @Before
    fun before() {
        commandRunner = mockk()
        main = Main(commandRunner)
    }

    @Test
    fun `if the command doesn't exist then throw`() {
        val args = listOf("invalid")

        assertFailsWith<Exception> {
            main.run(args)
        }
    }

    @Test
    fun `call run commit`() {
        val args = listOf("commit")

        every { commandRunner.commit() } just runs

        main.run(args)

        verify { commandRunner.commit() }
    }

    @Test
    fun `call run init`() {
        val args = listOf("init")

        every { commandRunner.init() } just runs

        main.run(args)

        verify { commandRunner.init() }
    }

    @Test
    fun `call run checkout`() {
        val args = listOf("checkout", "commit-id")

        every { commandRunner.checkout(any()) } just runs

        main.run(args)

        verify { commandRunner.checkout(args) }
    }

    @Test
    fun `call run log`() {
        val args = listOf("log")

        every { commandRunner.log() } just runs

        main.run(args)

        verify { commandRunner.log() }
    }

    @Test
    fun `call run add`() {
        val args = listOf("add", "file1.kt", "file2.kt")

        every { commandRunner.add(any()) } just runs

        main.run(args)

        verify { commandRunner.add(args) }
    }

    @Test
    fun `call run branch`() {
        val args = listOf("branch", "branch-name")

        every { commandRunner.branch(any()) } just runs

        main.run(args)

        verify { commandRunner.branch(args) }
    }
}
