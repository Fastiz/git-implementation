package service.commit.step

import logger.Logger
import model.CommitId
import model.Step
import repository.head.HeadRepository
import repository.ref.RefRepository

class MoveHead(
    private val refRepository: RefRepository,
    private val headRepository: HeadRepository,
    private val logger: Logger,
) : Step<CreateCommitOutput, CommitId> {
    override fun execute(input: CreateCommitOutput): CommitId {
        logger.printDebug("MoveHead")

        val head = headRepository.getHead()
        if (head == null) {
            logger.printDebug("Head is not pointing anywhere therefore moving head to ${input.commitId.value}")

            headRepository.setHead(input.commitId.value)

            return input.commitId
        }

        val ref = refRepository.get(head.value)
        if (ref == null) {
            logger.printDebug("Head is detached therefore moving head to ${input.commitId.value}")

            headRepository.setHead(input.commitId.value)
        } else {
            logger.printDebug("Head is not detached therefore moving ref to ${input.commitId.value}")

            refRepository.set(head.value, input.commitId)
        }

        return input.commitId
    }
}
