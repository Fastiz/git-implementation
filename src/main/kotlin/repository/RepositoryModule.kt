package repository

import org.koin.dsl.module
import repository.blob.FileBlobRepository
import repository.blob.FileBlobRepositoryImpl
import repository.commit.CommitRepository
import repository.commit.CommitRepositoryImpl
import repository.head.HeadRepository
import repository.head.HeadRepositoryImpl
import repository.index.IndexRepository
import repository.index.IndexRepositoryImpl
import repository.tree.TreeRepository
import repository.tree.TreeRepositoryImpl
import repository.workingdirectory.WorkingDirectoryRepository
import repository.workingdirectory.WorkingDirectoryRepositoryImpl

object RepositoryModule {
    val module = module {
        single<FileBlobRepository> {
            FileBlobRepositoryImpl(
                objectsDao = get()
            )
        }
        single<CommitRepository> {
            CommitRepositoryImpl(
                objectsDao = get()
            )
        }
        single<HeadRepository> {
            HeadRepositoryImpl(
                fileDao = get(),
                head = get(),
            )
        }
        single<TreeRepository> {
            TreeRepositoryImpl(
                objectsDao = get()
            )
        }
        single<WorkingDirectoryRepository> {
            WorkingDirectoryRepositoryImpl(
                fileDao = get(),
                root = get(),
                git = get(),
                objects = get(),
            )
        }
        single<IndexRepository> {
            IndexRepositoryImpl(
                fileDao = get(),
                logger = get(),
                index = get(),
            )
        }
    }
}
