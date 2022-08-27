package dao.files

import java.nio.file.Files
import java.nio.file.Path

class FileDaoImpl : FileDao {
    override fun createDirectory(path: String) {
        Files.createDirectory(Path.of(path))
    }

    override fun createFile(path: String) {
        Files.createFile(Path.of(path))
    }

    override fun writeFile(path: String, executor: Writer.() -> Unit) {
        val writer = WriterImpl()

        writer.executor()

        Files.write(Path.of(path), writer.getFullContent().toByteArray())
    }

    override fun <T> readFile(path: String, executor: Reader.() -> T): T {
        val lines = Files.lines(Path.of(path))

        val reader = ReaderImpl(lines)

        return reader.executor()
    }

    override fun copyFile(origin: String, target: String) {
        val originPath = Path.of(origin)
        val targetPath = Path.of(target)

        Files.copy(originPath, targetPath)
    }

    override fun removeAllExcluding(directory: String, excluding: List<String>) {
        val excludingPaths = excluding.map(Path::of)
        val startDirectoryPath = Path.of(directory)

        Files.walkFileTree(startDirectoryPath, DeleteFilesExcludingVisitor(excludingPaths))
    }

}