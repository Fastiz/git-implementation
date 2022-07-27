package controller.commandRunner

import service.commit.CommitService
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import service.checkout.CheckoutService
import service.init.InitService

class CommandRunnerImplTest {
    private lateinit var commitService: CommitService
    private lateinit var initService: InitService
    private lateinit var checkoutService: CheckoutService
    private lateinit var commandRunnerImpl: CommandRunnerImpl

    @Before
    fun before(){
        commitService = mockk()
        initService = mockk()
        checkoutService = mockk()
        commandRunnerImpl = CommandRunnerImpl(
            commitService = commitService,
            initService = initService,
            checkoutService = checkoutService
        )
    }

    @Test
    fun `calls commit service`(){
        val args = listOf("commit", "a", "b").toTypedArray()

        every { commitService.run(any()) } just runs

        commandRunnerImpl.commit(args)

        verify { commitService.run(listOf("a", "b")) }
    }

    @Test
    fun `calls init service`(){
        every { initService.run() } just runs

        commandRunnerImpl.init()

        verify { initService.run() }
    }

    @Test
    fun `calls checkout service`(){
        every { checkoutService.run(any()) } just runs

        commandRunnerImpl.checkout(listOf("checkout", "commit-id").toTypedArray())

        verify { checkoutService.run("commit-id") }
    }
}