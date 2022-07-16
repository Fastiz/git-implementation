import controller.commandRunner.CommandRunner
import controller.commandRunner.CommandRunnerImpl

fun main(args: Array<String>) {
    Main().run(args)
}

class Main(private val commandRunner: CommandRunner = CommandRunnerImpl()) {
    fun run(args: Array<String>) {
        if (args.isEmpty()) {
            throw Exception("No command was specified")
        }

        when (val commandName = args[0]) {
            "commit" -> commandRunner.runCommit(args)
            else -> throw Exception("There is not command named $commandName")
        }
    }
}