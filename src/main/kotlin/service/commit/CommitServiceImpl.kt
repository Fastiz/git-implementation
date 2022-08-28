package service.commit

import model.LambdaStep
import model.StepExecutorBuilder
import service.commit.step.CreateCommit
import service.commit.step.CreateFileBlobsIfNotExist
import service.commit.step.CreateFileBlobsIfNotExistInput
import service.commit.step.CreateNewTree
import service.commit.step.MoveHead

class CommitServiceImpl(
    private val createFileBlobsIfNotExist: CreateFileBlobsIfNotExist = CreateFileBlobsIfNotExist(),
    private val createNewTree: CreateNewTree = CreateNewTree(),
    private val createCommit: CreateCommit = CreateCommit(),
    private val moveHead: MoveHead = MoveHead()
) : CommitService {

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

    companion object {
        val logCommitStep = LambdaStep<String, Unit> {
            println("Commit created with hash: $it")
        }
    }
}