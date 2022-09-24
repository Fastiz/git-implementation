package service.commit

import logger.Logger
import model.LambdaStep
import model.StepExecutorBuilder
import service.commit.step.CreateCommit
import service.commit.step.CreateFileBlobsIfNotExist
import service.commit.step.CreateFileBlobsIfNotExistInput
import service.commit.step.CreateNewTree
import service.commit.step.MoveHead

class CommitServiceImpl(
    private val createFileBlobsIfNotExist: CreateFileBlobsIfNotExist,
    private val createNewTree: CreateNewTree,
    private val createCommit: CreateCommit,
    private val moveHead: MoveHead,
    private val logger: Logger
) : CommitService {
    private val logCommitStep = LambdaStep<String, Unit> {
        logger.print("Commit created with hash: $it")
    }

    override fun run(stagedFiles: List<String>) {
        val executor = StepExecutorBuilder()
            .addStep(createFileBlobsIfNotExist)
            .addStep(createNewTree)
            .addStep(createCommit)
            .addStep(moveHead)
            .addStep(logCommitStep)

        val input = CreateFileBlobsIfNotExistInput(stagedFiles)
        executor.execute(input)
    }
}