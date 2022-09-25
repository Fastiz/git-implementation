package repository.workingdirectory

import dao.files.FileDao
import directory.Git
import directory.Objects
import directory.Root
import model.FileBlob

class WorkingDirectoryRepositoryImpl(
    private val root: Root,
    private val git: Git,
    private val objects: Objects,
    private val fileDao: FileDao
) : WorkingDirectoryRepository {
    override fun clear() {
        fileDao.removeAllExcluding(
            directory = root.path,
            excluding = listOf(git.path)
        )
    }

    override fun bringBlob(fileBlob: FileBlob) {
        val originPath = objects.extend(fileBlob.id.value)
        val targetPath = root.extend(fileBlob.path)

        fileDao.copyFile(
            origin = originPath,
            target = targetPath
        )
    }
}
