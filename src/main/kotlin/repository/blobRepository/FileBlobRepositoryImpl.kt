package repository.blobRepository

import dao.objects.ObjectsDao
import dao.objects.ObjectsDaoImpl

class FileBlobRepositoryImpl(
    val objectsDao: ObjectsDao = ObjectsDaoImpl()
) : FileBlobRepository {
    override fun create(path: Path): Hash {
        TODO("Not yet implemented")
    }
}