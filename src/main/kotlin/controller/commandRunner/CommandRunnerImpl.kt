package controller.commandRunner

import service.commit.CommitService
import service.commit.CommitServiceImpl
import service.init.InitService
import service.init.InitServiceImpl

class CommandRunnerImpl(
    private val commitService: CommitService = CommitServiceImpl(),
    private val initService: InitService = InitServiceImpl()
) : CommandRunner {
    override fun runCommit(args: Array<String>) {
        val parameters = args.toList().drop(1)

        commitService.run(parameters)
    }

    override fun runInit() {
        initService.run()
    }
}