package repository.workingdirectory

import dao.files.FileDao
import model.Directory
import model.FileBlob
import model.extendPath

class WorkingDirectoryRepositoryImpl(
    private val fileDao: FileDao
) : WorkingDirectoryRepository {
    override fun clear() {
        fileDao.removeAllExcluding(
            directory = Directory.ROOT.path,
            excluding = listOf(Directory.GIT.path)
        )
    }

    override fun bringBlob(fileBlob: FileBlob) {
        val originPath = Directory.OBJECTS.extendPath(fileBlob.id.value)
        val targetPath = Directory.ROOT.extendPath(fileBlob.path)

        fileDao.copyFile(
            origin = originPath,
            target = targetPath
        )
    }
}
