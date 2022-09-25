package controller.commandRunner

import service.checkout.CheckoutService
import service.commit.CommitService
import service.init.InitService
import service.log.LogService

class CommandRunnerImpl(
    private val commitService: CommitService,
    private val initService: InitService,
    private val checkoutService: CheckoutService,
    private val logService: LogService,
) : CommandRunner {
    override fun commit(args: List<String>) {
        val parameters = args.toList().drop(1)

        commitService.run(parameters)
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
        TODO("Not yet implemented")
    }
}
