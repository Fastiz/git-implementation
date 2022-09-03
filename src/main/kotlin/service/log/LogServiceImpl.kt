package service.log

import repository.head.HeadRepository
import logger.Logger

class LogServiceImpl(
    private val headRepository: HeadRepository,
    private val commitHistoryFactory: CommitHistoryFactory,
    private val logger: Logger
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