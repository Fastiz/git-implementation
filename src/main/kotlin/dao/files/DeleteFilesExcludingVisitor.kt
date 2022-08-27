package dao.files

import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

class DeleteFilesExcludingVisitor(private val excludingPaths: Collection<Path>) : SimpleFileVisitor<Path>() {
    override fun visitFile(file: Path, attrs: BasicFileAttributes?): FileVisitResult {
        if (!excludingPaths.contains(file)) {
            Files.delete(file)
        }

        return FileVisitResult.CONTINUE
    }

    override fun postVisitDirectory(dir: Path, exc: IOException?): FileVisitResult {
        val isContainsExcludedFile = excludingPaths.none { dir.contains(it) }

        if (!isContainsExcludedFile) {
            Files.delete(dir)
        }

        return FileVisitResult.CONTINUE
    }
}