package service

import org.koin.dsl.module
import service.checkout.CheckoutService
import service.checkout.CheckoutServiceImpl
import service.commit.CommitService
import service.commit.CommitServiceImpl
import service.init.InitService
import service.init.InitServiceImpl
import service.log.LogService
import service.log.LogServiceImpl

object ServiceModule {
    val module = module {
        single<CheckoutService> { CheckoutServiceImpl() }
        single<CommitService> { CommitServiceImpl() }
        single<InitService> { InitServiceImpl() }
        single<LogService> { LogServiceImpl() }
    }
}