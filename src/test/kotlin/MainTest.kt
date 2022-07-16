import command.commandRunner.CommandRunner
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
    fun before(){
        commandRunner = mockk()
        main = Main(commandRunner)
    }


    @Test
    fun `if the command doesn't exist then throw`() {
        val args = listOf("invalid")

        assertFailsWith<Exception> {
            main.run(args.toTypedArray())
        }

    }

    @Test
    fun `call run commit`() {
        val args = listOf("commit", "a", "b").toTypedArray()

        every { commandRunner.runCommit(any()) } just runs

        main.run(args)

        verify { commandRunner.runCommit(args) }
    }
}