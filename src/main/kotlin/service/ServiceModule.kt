package service

import org.koin.dsl.module
import service.checkout.CheckoutService
import service.checkout.CheckoutServiceImpl
import service.commit.CommitService
import service.commit.CommitServiceImpl
import service.commit.step.CreateCommit
import service.commit.step.CreateFileBlobsIfNotExist
import service.commit.step.CreateNewTree
import service.commit.step.MoveHead
import service.init.InitService
import service.init.InitServiceImpl
import service.log.CommitHistoryFactory
import service.log.LogService
import service.log.LogServiceImpl

object ServiceModule {
    private val commitHistory = org.koin.dsl.module {
        single {
            CommitHistoryFactory(
                commitRepository = get()
            )
        }
    }

    private val commitSteps = org.koin.dsl.module {
        single {
            CreateFileBlobsIfNotExist(
                fileBlobRepository = get(),
                logger = get(),
            )
        }
        single {
            CreateNewTree(
                commitRepository = get(),
                headRepository = get(),
                treeRepository = get(),
                logger = get(),
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
    }

    val module = module {
        includes(commitSteps)

        single<CommitService> {
            CommitServiceImpl(
                createFileBlobsIfNotExist = get(),
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
                commitRepository = get()
            )
        }

        single<InitService> {
            InitServiceImpl(
                fileDao = get()
            )
        }

        includes(commitHistory)
        single<LogService> {
            LogServiceImpl(
                headRepository = get(),
                commitHistoryFactory = get(),
                logger = get(),
            )
        }
    }
}