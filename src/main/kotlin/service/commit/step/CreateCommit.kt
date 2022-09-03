package service.commit.step

import model.Step
import repository.commit.CommitRepository
import repository.head.HeadRepository

data class CreateCommitOutput(
    val commitId: String
)

class CreateCommit(
    private val headRepository: HeadRepository,
    private val commitRepository: CommitRepository,
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