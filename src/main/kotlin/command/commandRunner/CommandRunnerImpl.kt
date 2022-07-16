package command.commandRunner

import command.commit.Commit
import command.commit.CommitImpl

class CommandRunnerImpl(val commit: Commit = CommitImpl()) : CommandRunner {
    override fun runCommit(args: Array<String>){

    }
}