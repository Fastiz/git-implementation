package service.log

import repository.head.HeadRepository
import repository.head.HeadRepositoryImpl

class LogServiceImpl(
    private val headRepository: HeadRepository = HeadRepositoryImpl(),
    private val commitHistoryFactory: CommitHistoryFactory = CommitHistoryFactory()
) : LogService {
    override fun run() {
        val headId = headRepository.getHead()

        if (headId == null) {
            println("There are no commits yet")
            return
        }

        val commitHistory = commitHistoryFactory.create(headId)

        for (commit in commitHistory) {
            println(LogItemFormatter.formatLogItem(commit.id))
        }
    }
}