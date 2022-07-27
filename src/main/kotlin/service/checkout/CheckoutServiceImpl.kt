package service.checkout

import repository.commit.CommitRepository
import repository.commit.CommitRepositoryImpl
import repository.head.HeadRepository
import repository.head.HeadRepositoryImpl
import repository.tree.TreeRepository
import repository.tree.TreeRepositoryImpl
import repository.workingdirectory.WorkingDirectoryRepository
import repository.workingdirectory.WorkingDirectoryRepositoryImpl

class CheckoutServiceImpl(
    private val workingDirectoryRepository: WorkingDirectoryRepository = WorkingDirectoryRepositoryImpl(),
    private val headRepository: HeadRepository = HeadRepositoryImpl(),
    private val treeRepository: TreeRepository = TreeRepositoryImpl(),
    private val commitRepository: CommitRepository = CommitRepositoryImpl()
) : CheckoutService {
    override fun run(id: String) {
        workingDirectoryRepository.clear()

        val treeId = commitRepository.get(id).treeId
        val tree = treeRepository.get(treeId)

        tree.fileBlobList.forEach { workingDirectoryRepository.bringBlob(it) }

        headRepository.setHead(id)
    }
}