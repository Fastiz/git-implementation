package service.branch

import repository.head.HeadRepository
import repository.ref.RefRepository
import java.lang.IllegalArgumentException

class BranchServiceImpl(
    private val headRepository: HeadRepository,
    private val refRepository: RefRepository,
) : BranchService {
    override fun run(branchName: String) {
        val current = headRepository.getHead() ?: throw IllegalArgumentException("HEAD is not pointing to a commit")

        refRepository.set(branchName, current)
    }
}