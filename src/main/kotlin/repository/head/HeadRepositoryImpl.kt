package repository.head

import dao.objects.head.HeadDao
import dao.objects.head.HeadDaoImpl

class HeadRepositoryImpl(private val headDao: HeadDao = HeadDaoImpl()) : HeadRepository {
    override fun getHead(): Hash {
        val content = headDao.getHead()

        return content.substringAfter("ref: ")
    }

    override fun setHead(commitId: String) {
        headDao.setHead(commitId)
    }
}