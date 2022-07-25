package service.init

import dao.objects.files.FileDao
import dao.objects.files.FileDaoImpl

class InitServiceImpl(private val fileDao: FileDao = FileDaoImpl()) : InitService {
    override fun run() {
        fileDao.createDirectory(".git-fastiz/objects")
    }
}