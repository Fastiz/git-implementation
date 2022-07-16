package controller.commandRunner

import service.commit.CommitService
import service.commit.CommitServiceImpl

class CommandRunnerImpl(private val commit: CommitService = CommitServiceImpl()) : CommandRunner {
    override fun runCommit(args: Array<String>){
        val parameters = args.toList().drop(1)

        commit.run(parameters)
    }
}