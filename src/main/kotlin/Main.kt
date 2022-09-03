import controller.commandRunner.CommandRunner
import controller.commandRunner.ControllerModule
import dao.DaoModule
import logger.Logger
import logger.StdOutLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.dsl.module
import repository.RepositoryModule
import service.ServiceModule

fun main(args: Array<String>) {
    startKoin {
        modules(
            DaoModule.module,
            RepositoryModule.module,
            ServiceModule.module,
            ControllerModule.module,
            MainModule.module,
        )
    }

    val component = object : KoinComponent {
        val commandRunner = get<CommandRunner>()
    }

    Main(commandRunner = component.commandRunner).run(args)
}

object MainModule {
    val module = module {
        single { Main(commandRunner = get()) }
        single<Logger> { StdOutLogger() }
    }
}

class Main(private val commandRunner: CommandRunner) {
    fun run(args: Array<String>) {
        if (args.isEmpty()) {
            throw Exception("No command was specified")
        }

        when (val commandName = args[0]) {
            "commit" -> commandRunner.commit(args)
            "init" -> commandRunner.init()
            "checkout" -> commandRunner.checkout(args)
            "log" -> commandRunner.log()
            else -> throw Exception("There is not command named $commandName")
        }
    }
}