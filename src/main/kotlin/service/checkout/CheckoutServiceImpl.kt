package service.checkout

import model.FileBlob
import model.FileTreeEntry
import model.SubtreeTreeEntry
import model.Tree
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

        sequenceFromTree(tree).forEach {
            workingDirectoryRepository.bringBlob(it)
        }

        headRepository.setHead(id)
    }

    private fun sequenceFromTree(tree: Tree): Sequence<FileBlob> {
        return sequence {
            tree.entries.forEach {
                when (it) {
                    is FileTreeEntry -> yield(FileBlob(path = it.path, id = it.fileBlobId))
                    is SubtreeTreeEntry -> {
                        val subtree = treeRepository.get(it.subtreeId)
                        yieldAll(sequenceFromTree(subtree))
                    }
                }
            }
        }
    }
}