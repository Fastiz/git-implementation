package service.init

import dao.files.FileDao
import dao.files.FileDaoImpl
import model.Directory
import model.File

class InitServiceImpl(
    private val fileDao: FileDao = FileDaoImpl(),
) : InitService {
    override fun run() {
        fileDao.createDirectory(Directory.OBJECTS.path)
        fileDao.createFile(File.HEAD.path)
    }
}