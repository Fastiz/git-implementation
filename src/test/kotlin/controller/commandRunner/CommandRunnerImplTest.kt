package controller.commandRunner

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import service.add.AddService
import service.branch.BranchService
import service.checkout.CheckoutService
import service.commit.CommitService
import service.init.InitService
import service.log.LogService

class CommandRunnerImplTest {
    private lateinit var commitService: CommitService
    private lateinit var initService: InitService
    private lateinit var checkoutService: CheckoutService
    private lateinit var logService: LogService
    private lateinit var addService: AddService
    private lateinit var branchService: BranchService

    private lateinit var commandRunnerImpl: CommandRunnerImpl

    @Before
    fun before() {
        commitService = mockk()
        initService = mockk()
        checkoutService = mockk()
        logService = mockk()
        addService = mockk()
        branchService = mockk()

        commandRunnerImpl = CommandRunnerImpl(
            commitService = commitService,
            initService = initService,
            checkoutService = checkoutService,
            logService = logService,
            addService = addService,
            branchService = branchService,
        )
    }

    @Test
    fun `calls commit service`() {
        every { commitService.run() } just runs

        commandRunnerImpl.commit()

        verify { commitService.run() }
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

    @Test
    fun `calls add service`() {
        val arguments = listOf("add", "file.kt")

        every { addService.run(any()) } just runs

        commandRunnerImpl.add(arguments)

        verify { addService.run(listOf("file.kt")) }
    }

    @Test
    fun `calls branch service`() {
        val arguments = listOf("branch", "branch-name")

        every { branchService.run(any()) } just runs

        commandRunnerImpl.branch(arguments)

        verify { branchService.run("branch-name") }
    }
}
