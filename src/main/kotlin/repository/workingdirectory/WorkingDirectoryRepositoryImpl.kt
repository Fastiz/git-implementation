package repository.workingdirectory

import dao.objects.files.FileDao
import dao.objects.files.FileDaoImpl
import model.Directory
import model.FileBlob
import model.extendPath

class WorkingDirectoryRepositoryImpl(
    private val fileDao: FileDao = FileDaoImpl()
) : WorkingDirectoryRepository {
    override fun clear() {
        fileDao.removeAllExcluding(
            directory = Directory.ROOT.path,
            excluding = listOf(Directory.GIT.path)
        )
    }

    override fun bringBlob(fileBlob: FileBlob) {
        val originPath = Directory.OBJECTS.extendPath(fileBlob.id)
        val targetPath = Directory.ROOT.extendPath(fileBlob.path)

        fileDao.copyFile(
            origin = originPath,
            target = targetPath
        )
    }
}