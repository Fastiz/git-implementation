package repository.workingdirectory

import model.FileBlob

interface WorkingDirectoryRepository {
    fun clear()

    fun bringBlob(fileBlob: FileBlob)
}
