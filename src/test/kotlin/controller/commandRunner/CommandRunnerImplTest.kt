package controller.commandRunner

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import service.checkout.CheckoutService
import service.commit.CommitService
import service.init.InitService
import service.log.LogService

class CommandRunnerImplTest {
    private lateinit var commitService: CommitService
    private lateinit var initService: InitService
    private lateinit var checkoutService: CheckoutService
    private lateinit var logService: LogService

    private lateinit var commandRunnerImpl: CommandRunnerImpl

    @Before
    fun before() {
        commitService = mockk()
        initService = mockk()
        checkoutService = mockk()
        logService = mockk()

        commandRunnerImpl = CommandRunnerImpl(
            commitService = commitService,
            initService = initService,
            checkoutService = checkoutService,
            logService = logService
        )
    }

    @Test
    fun `calls commit service`() {
        val args = listOf("commit", "a", "b")

        every { commitService.run(any()) } just runs

        commandRunnerImpl.commit(args)

        verify { commitService.run(listOf("a", "b")) }
    }

    @Test
    fun `calls init service`() {
        every { initService.run() } just runs

        commandRunnerImpl.init()

        verify { initService.run() }
    }

    @Test
    fun `calls checkout service`() {
        every { checkoutService.run(any()) } just runs

        commandRunnerImpl.checkout(listOf("checkout", "commit-id"))

        verify { checkoutService.run("commit-id") }
    }

    @Test
    fun `calls log service`() {
        every { logService.run() } just runs

        commandRunnerImpl.log()

        verify { logService.run() }
    }
}
