package service.commit.step

import logger.Logger
import model.CommitId
import model.Step
import repository.head.HeadRepository

class MoveHead(
    private val headRepository: HeadRepository,
    private val logger: Logger,
) : Step<CreateCommitOutput, CommitId> {
    override fun execute(input: CreateCommitOutput): CommitId {

        logger.printDebug("MoveHead - moving head to ${input.commitId.value}")
        headRepository.setHead(input.commitId)

        return input.commitId
    }
}