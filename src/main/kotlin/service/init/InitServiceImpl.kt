package service.init

import dao.files.FileDao
import model.Directory
import model.File

class InitServiceImpl(
    private val fileDao: FileDao,
) : InitService {
    override fun run() {
        fileDao.createDirectory(Directory.OBJECTS.path)
        fileDao.createFile(File.HEAD.path)
    }
}
