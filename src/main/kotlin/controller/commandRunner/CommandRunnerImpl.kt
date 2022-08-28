package controller.commandRunner

import service.checkout.CheckoutService
import service.checkout.CheckoutServiceImpl
import service.commit.CommitService
import service.commit.CommitServiceImpl
import service.init.InitService
import service.init.InitServiceImpl
import service.log.LogService
import service.log.LogServiceImpl

class CommandRunnerImpl(
    private val commitService: CommitService = CommitServiceImpl(),
    private val initService: InitService = InitServiceImpl(),
    private val checkoutService: CheckoutService = CheckoutServiceImpl(),
    private val logService: LogService = LogServiceImpl(),
) : CommandRunner {
    override fun commit(args: Array<String>) {
        val parameters = args.toList().drop(1)

        commitService.run(parameters)
    }

    override fun init() {
        initService.run()
    }

    override fun checkout(args: Array<String>) {
        val id = args.drop(1)[0]

        checkoutService.run(id)
    }

    override fun log() {
        logService.run()
    }
}