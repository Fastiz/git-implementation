package controller.commandRunner

interface CommandRunner {
    fun commit(args: List<String>)

    fun init()

    fun checkout(args: List<String>)

    fun log()
}