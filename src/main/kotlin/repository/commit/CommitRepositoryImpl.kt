package repository.commit

import repository.blobRepository.BlobRepository
import repository.commit.CommitContentFormatter.formatCommitMessage

class CommitRepositoryImpl(val blobRepository: BlobRepository) : CommitRepository {
    override fun create(treeId: String, parentId: String, message: String): Hash {
        val content = formatCommitMessage(treeId, parentId, message)

        return blobRepository.createFromString(content)
    }
}