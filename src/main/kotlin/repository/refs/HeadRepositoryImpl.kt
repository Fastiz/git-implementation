package repository.refs

import dao.objects.head.HeadDao
import dao.objects.head.HeadDaoImpl

class HeadRepositoryImpl(private val headDao: HeadDao = HeadDaoImpl()) : HeadRepository {
    override fun getHead(): Hash {
        return headDao.getHead()
    }

    override fun setHead(commitId: String) {
        headDao.setHead(commitId)
    }
}