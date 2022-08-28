package service.commit.step

import model.Step
import repository.commit.CommitRepository
import repository.commit.CommitRepositoryImpl
import repository.head.HeadRepository
import repository.head.HeadRepositoryImpl

data class CreateCommitOutput(
    val commitId: String
)

class CreateCommit(
    private val headRepository: HeadRepository = HeadRepositoryImpl(),
    private val commitRepository: CommitRepository = CommitRepositoryImpl(),
) : Step<OutputCreateNewTree, CreateCommitOutput> {
    override fun execute(input: OutputCreateNewTree): CreateCommitOutput {
        val head = headRepository.getHead()

        val currentCommit = head?.let { commitRepository.get(it) }?.id

        val commitId = commitRepository.create(
            treeId = input.treeId,
            parentId = currentCommit,
            message = ""
        )

        return CreateCommitOutput(commitId = commitId)
    }
}