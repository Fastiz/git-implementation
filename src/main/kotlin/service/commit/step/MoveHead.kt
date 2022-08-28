package service.commit.step

import model.Step
import repository.head.HeadRepository
import repository.head.HeadRepositoryImpl

class MoveHead(
    private val headRepository: HeadRepository = HeadRepositoryImpl(),
) : Step<CreateCommitOutput, String> {
    override fun execute(input: CreateCommitOutput): String {
        headRepository.setHead(input.commitId)

        return input.commitId
    }
}