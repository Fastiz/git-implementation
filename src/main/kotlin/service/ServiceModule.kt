package service

import org.koin.dsl.module
import repository.blob.FileBlobRepository
import repository.blob.FileBlobRepositoryImpl
import repository.index.IndexRepositoryImpl
import service.add.AddService
import service.add.AddServiceImpl
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
    val module = module {
        single<AddService> {
            AddServiceImpl(
                fileBlobRepository = get(),
                indexRepository = get(),
            )
        }

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
