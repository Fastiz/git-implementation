package service

import org.koin.dsl.module
import service.add.AddService
import service.add.AddServiceImpl
import service.checkout.CheckoutService
import service.checkout.CheckoutServiceImpl
import service.commit.CommitService
import service.commit.CommitServiceImpl
import service.commit.step.CreateCommit
import service.commit.step.CreateNewTree
import service.commit.step.MoveHead
import service.init.InitService
import service.init.InitServiceImpl
import service.log.CommitHistoryFactory
import service.log.LogService
import service.log.LogServiceImpl

object ServiceModule {
    val module = module {
        single<AddService> {
            AddServiceImpl(
                fileBlobRepository = get(),
                indexRepository = get(),
            )
        }

        single {
            CreateNewTree(
                treeRepository = get(),
                logger = get(),
                root = get(),
            )
        }
        single {
            CreateCommit(
                headRepository = get(),
                commitRepository = get(),
                logger = get(),
            )
        }
        single {
            MoveHead(
                headRepository = get(),
                logger = get(),
            )
        }
        single<CommitService> {
            CommitServiceImpl(
                indexRepository = get(),
                createNewTree = get(),
                createCommit = get(),
                moveHead = get(),
                logger = get()
            )
        }

        single<CheckoutService> {
            CheckoutServiceImpl(
                workingDirectoryRepository = get(),
                headRepository = get(),
                treeRepository = get(),
                commitRepository = get(),
                indexRepository = get(),
            )
        }

        single<InitService> {
            InitServiceImpl(
                objects = get(),
                head = get(),
                index = get(),
                fileDao = get()
            )
        }

        single {
            CommitHistoryFactory(
                commitRepository = get()
            )
        }

        single<LogService> {
            LogServiceImpl(
                headRepository = get(),
                commitHistoryFactory = get(),
                logger = get(),
            )
        }
    }
}
