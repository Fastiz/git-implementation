package service.commit.step

import logger.Logger
import model.CommitId
import model.Step
import repository.commit.CommitRepository
import repository.head.HeadRepository

data class CreateCommitOutput(
    val commitId: CommitId
)

class CreateCommit(
    private val headRepository: HeadRepository,
    private val commitRepository: CommitRepository,
    private val logger: Logger,
) : Step<OutputCreateNewTree, CreateCommitOutput> {
    override fun execute(input: OutputCreateNewTree): CreateCommitOutput {
        val head = headRepository.getHead()

        val currentCommit = head?.let { commitRepository.get(it) }?.id

        logger.printDebug("CreateCommit - creating commit with treeId (${input.treeId}) and parentId ($currentCommit)")

        val commitId = commitRepository.create(
            treeId = input.treeId,
            parentCommitId = currentCommit,
            message = ""
        )

        return CreateCommitOutput(commitId = commitId)
    }
}