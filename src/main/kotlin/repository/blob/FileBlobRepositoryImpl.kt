package repository.blob

import dao.files.FileDao
import dao.objects.ObjectsDao
import model.FileBlobId

class FileBlobRepositoryImpl(
    private val fileDao: FileDao,
    private val objectsDao: ObjectsDao
) : FileBlobRepository {
    override fun createIfNotExists(path: Path): FileBlobId {
        val lines = fileDao.readFile(path) {
            generateSequence { readLine() }
        }

        val objectId = objectsDao.createFromString(lines.joinToString("\n"))

        return FileBlobId.from(objectId)
    }
}