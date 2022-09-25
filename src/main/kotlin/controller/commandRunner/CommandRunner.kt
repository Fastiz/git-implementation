package controller.commandRunner

interface CommandRunner {
    fun commit()

    fun init()

    fun checkout(args: List<String>)

    fun log()

    fun add(args: List<String>)

    fun branch(args: List<String>)
}
