package service.init

import dao.files.FileDao
import dao.files.FileDaoImpl
import model.Directory

class InitServiceImpl(private val fileDao: FileDao = FileDaoImpl()) : InitService {
    override fun run() {
        fileDao.createDirectory(Directory.OBJECTS.path)
    }
}