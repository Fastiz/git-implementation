package service.checkout

import model.CommitId
import model.FileBlob
import model.FileTreeEntry
import model.SubtreeTreeEntry
import model.Tree
import repository.commit.CommitRepository
import repository.head.HeadRepository
import repository.index.IndexRepository
import repository.tree.TreeRepository
import repository.workingdirectory.WorkingDirectoryRepository

class CheckoutServiceImpl(
    private val workingDirectoryRepository: WorkingDirectoryRepository,
    private val headRepository: HeadRepository,
    private val treeRepository: TreeRepository,
    private val commitRepository: CommitRepository,
    private val indexRepository: IndexRepository,
) : CheckoutService {
    override fun run(id: String) {
        val commitId = CommitId.from(id)

        workingDirectoryRepository.clear()

        val treeId = commitRepository.get(commitId).treeId
        val tree = treeRepository.get(treeId)

        val sequenceFromTree = sequenceFromTree(tree)

        sequenceFromTree.forEach {
            workingDirectoryRepository.bringBlob(it)
        }

        indexRepository.set(sequenceFromTree.asIterable())

        headRepository.setHead(commitId)
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
