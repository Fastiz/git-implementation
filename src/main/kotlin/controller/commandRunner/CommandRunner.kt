package controller.commandRunner

interface CommandRunner {
    fun runCommit(args: Array<String>)

    fun runInit()
}