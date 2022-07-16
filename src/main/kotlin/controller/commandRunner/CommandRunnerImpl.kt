package controller.commandRunner

import service.commit.CommitService
import service.commit.CommitImpl

class CommandRunnerImpl(private val commit: CommitService = CommitImpl()) : CommandRunner {
    override fun runCommit(args: Array<String>){
        val parameters = args.toList().drop(1)

        commit.run(parameters)
    }
}