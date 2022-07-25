package service.commit.step

import model.Step
import repository.commit.CommitRepository
import repository.commit.CommitRepositoryImpl

data class CreateCommitOutput(
    val commitId: String
)

class CreateCommit(
    private val commitRepository: CommitRepository = CommitRepositoryImpl(),
) : Step<OutputCreateNewTree, CreateCommitOutput> {
    override fun execute(input: OutputCreateNewTree): CreateCommitOutput {
        val commitId = commitRepository.create(
            treeId = input.treeId,
            parentId = input.parentId,
            message = ""
        )

        return CreateCommitOutput(commitId = commitId)
    }
}