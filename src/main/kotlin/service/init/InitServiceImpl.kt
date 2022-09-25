package service.init

import dao.files.FileDao
import directory.Head
import directory.Index
import directory.Objects
import directory.Refs
import directory.RefsHeads

class InitServiceImpl(
    private val objects: Objects,
    private val head: Head,
    private val index: Index,
    private val refs: Refs,
    private val refsHeads: RefsHeads,
    private val fileDao: FileDao,
) : InitService {
    override fun run() {
        fileDao.createDirectory(objects.path)
        fileDao.createFile(head.path)
        fileDao.createFile(index.path)
        fileDao.createDirectory(refs.path)
        fileDao.createDirectory(refsHeads.path)
    }
}
