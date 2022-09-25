package dao.objects

import dao.files.FileDao
import directory.Objects
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest

class ObjectsDaoImpl(
    private val objects: Objects,
    private val fileDao: FileDao
) : ObjectsDao {
    override fun createFromString(content: String): Hash {
        val hash = content.toByteArray().sha256()

        val path = objects.extend(hash)

        if (!fileDao.doesFileExist(path)) {
            fileDao.createFile(path)
            fileDao.writeFile(path) {
                write(content)
            }
        }

        return hash
    }

    override fun createFromPath(path: String): Hash {
        val bytes = Files.readAllBytes(Path.of(path))

        val hash = bytes.sha256()

        val pathToWrite = objects.extend(hash)
        if (!fileDao.doesFileExist(pathToWrite)) {
            fileDao.copyFile(path, pathToWrite)
        }

        return hash
    }

    override fun get(id: Hash): String {
        val lines = fileDao.readFile(objects.extend(id)) {
            generateSequence { readLine() }
        }

        return lines.joinToString("\n")
    }
}

fun ByteArray.sha256(): String {
    return MessageDigest
        .getInstance("SHA-256")
        .digest(this)
        .fold("") { str, it -> str + "%02x".format(it) }
}
