package service.log

import logger.Logger
import repository.head.HeadRepository

class LogServiceImpl(
    private val headRepository: HeadRepository,
    private val commitHistoryFactory: CommitHistoryFactory,
    private val logger: Logger
) : LogService {
    override fun run() {
        val headId = headRepository.getHead()

        if (headId == null) {
            logger.print("There are no commits yet")
            return
        }

        val commitHistory = commitHistoryFactory.create(headId)

        for (commit in commitHistory) {
            logger.print(LogItemFormatter.formatLogItem(commit.id.value))
        }
    }
}
