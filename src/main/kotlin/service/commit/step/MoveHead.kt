package service.commit.step

import model.Step
import repository.head.HeadRepository
import repository.head.HeadRepositoryImpl

class MoveHead(
    private val headRepository: HeadRepository = HeadRepositoryImpl(),
) : Step<CreateCommitOutput, Unit> {
    override fun execute(input: CreateCommitOutput) {
        headRepository.setHead(input.commitId)
    }
}