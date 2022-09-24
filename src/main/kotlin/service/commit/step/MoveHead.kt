package service.commit.step

import logger.Logger
import model.Step
import repository.head.HeadRepository

class MoveHead(
    private val headRepository: HeadRepository,
    private val logger: Logger,
) : Step<CreateCommitOutput, String> {
    override fun execute(input: CreateCommitOutput): String {

        logger.printDebug("MoveHead - moving head to ${input.commitId}")
        headRepository.setHead(input.commitId)

        return input.commitId
    }
}