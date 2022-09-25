package service.commit

import logger.Logger
import model.CommitId
import model.LambdaStep
import model.StepExecutorBuilder
import repository.index.IndexRepository
import service.commit.step.CreateCommit
import service.commit.step.CreateNewTree
import service.commit.step.MoveHead

class CommitServiceImpl(
    private val indexRepository: IndexRepository,
    private val createNewTree: CreateNewTree,
    private val createCommit: CreateCommit,
    private val moveHead: MoveHead,
    private val logger: Logger
) : CommitService {
    private val logCommitStep = LambdaStep<CommitId, Unit> {
        logger.print("Commit created with hash: ${it.value}")
    }

    override fun run() {
        val executor = StepExecutorBuilder()
            .addStep(createNewTree)
            .addStep(createCommit)
            .addStep(moveHead)
            .addStep(logCommitStep)

        val fileBlobsInIndex = indexRepository.get()
        executor.execute(fileBlobsInIndex.asIterable())
    }
}
