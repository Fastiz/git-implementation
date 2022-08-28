package service.log

import repository.head.HeadRepository
import repository.head.HeadRepositoryImpl
import repository.logger.Logger
import repository.logger.StdOutLogger

class LogServiceImpl(
    private val headRepository: HeadRepository = HeadRepositoryImpl(),
    private val commitHistoryFactory: CommitHistoryFactory = CommitHistoryFactory(),
    private val logger: Logger = StdOutLogger()
) : LogService {
    override fun run() {
        val headId = headRepository.getHead()

        if (headId == null) {
            logger.println("There are no commits yet")
            return
        }

        val commitHistory = commitHistoryFactory.create(headId)

        for (commit in commitHistory) {
            logger.println(LogItemFormatter.formatLogItem(commit.id))
        }
    }
}