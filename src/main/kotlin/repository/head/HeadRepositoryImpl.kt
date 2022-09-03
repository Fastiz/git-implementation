package repository.head

import dao.head.HeadDao

class HeadRepositoryImpl(private val headDao: HeadDao) : HeadRepository {
    override fun getHead(): Hash? {
        val content = headDao.getHead()

        return content?.substringAfter("ref: ")
    }

    override fun setHead(commitId: String) {
        headDao.setHead(commitId)
    }
}