package controller.commandRunner

interface CommandRunner {
    fun commit(args: Array<String>)

    fun init()

    fun checkout(args: Array<String>)
}