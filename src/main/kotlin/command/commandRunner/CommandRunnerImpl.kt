package command.commandRunner

import command.commit.Commit
import command.commit.CommitImpl

class CommandRunnerImpl(private val commit: Commit = CommitImpl()) : CommandRunner {
    override fun runCommit(args: Array<String>){
        val parameters = args.toList().drop(1)

        commit.run(parameters)
    }
}