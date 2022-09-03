package controller.commandRunner

import org.koin.dsl.module

object ControllerModule {
    val module = module {
        single<CommandRunner> {
            CommandRunnerImpl(
                commitService = get(),
                initService = get(),
                checkoutService = get(),
                logService = get()
            )
        }
    }
}