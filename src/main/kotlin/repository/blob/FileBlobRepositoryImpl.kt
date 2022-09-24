package repository.blob

import dao.files.FileDao
import dao.objects.ObjectsDao
import model.FileBlobId

class FileBlobRepositoryImpl(
    private val objectsDao: ObjectsDao
) : FileBlobRepository {
    override fun createIfNotExists(path: Path): FileBlobId {
        val objectId = objectsDao.createFromPath(path)

        return FileBlobId.from(objectId)
    }
}
