package service.checkout

import repository.commit.CommitRepository
import repository.head.HeadRepository
import repository.tree.TreeRepository
import repository.workingdirectory.WorkingDirectoryRepository

class CheckoutServiceImpl(
    private val workingDirectoryRepository: WorkingDirectoryRepository,
    private val headRepository: HeadRepository,
    private val treeRepository: TreeRepository,
    private val commitRepository: CommitRepository
) : CheckoutService {
    override fun run(id: String) {
        workingDirectoryRepository.clear()

        val treeId = commitRepository.get(id).treeId
        val tree = treeRepository.get(treeId)

        tree.fileBlobList.forEach { workingDirectoryRepository.bringBlob(it) }

        headRepository.setHead(id)
    }
}