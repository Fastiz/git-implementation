package controller.commandRunner

import service.add.AddService
import service.branch.BranchService
import service.checkout.CheckoutService
import service.commit.CommitService
import service.init.InitService
import service.log.LogService

class CommandRunnerImpl(
    private val commitService: CommitService,
    private val initService: InitService,
    private val checkoutService: CheckoutService,
    private val logService: LogService,
    private val addService: AddService,
    private val branchService: BranchService,
) : CommandRunner {
    override fun commit() {
        commitService.run()
    }

    override fun init() {
        initService.run()
    }

    override fun checkout(args: List<String>) {
        val id = args.drop(1)[0]

        checkoutService.run(id)
    }

    override fun log() {
        logService.run()
    }

    override fun add(args: List<String>) {
        val parameters = args.drop(1)

        addService.run(parameters)
    }

    override fun branch(args: List<String>) {
        val branchName = args[1]

        branchService.run(branchName)
    }
}
