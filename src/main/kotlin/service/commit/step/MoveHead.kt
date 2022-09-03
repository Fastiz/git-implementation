package service.commit.step

import model.Step
import repository.head.HeadRepository

class MoveHead(
    private val headRepository: HeadRepository,
) : Step<CreateCommitOutput, String> {
    override fun execute(input: CreateCommitOutput): String {
        headRepository.setHead(input.commitId)

        return input.commitId
    }
}