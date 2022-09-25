package dao.files

import directory.Root
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

class DeleteFilesExcludingVisitor(
    private val root: Root,
    private val excludingPaths: Collection<Path>
) : SimpleFileVisitor<Path>() {
    override fun visitFile(file: Path, attrs: BasicFileAttributes?): FileVisitResult {
        if (!excludingPaths.contains(file)) {
            Files.delete(file)
        }

        return FileVisitResult.CONTINUE
    }

    override fun postVisitDirectory(dir: Path, exc: IOException?): FileVisitResult {
        val containsExcludedFile = excludingPaths.any { dir.contains(it) }

        if (!containsExcludedFile && dir != Path.of(root.path)) {
            Files.delete(dir)
        }

        return FileVisitResult.CONTINUE
    }

    override fun preVisitDirectory(dir: Path?, attrs: BasicFileAttributes?): FileVisitResult {
        if (excludingPaths.contains(dir)) {
            return FileVisitResult.SKIP_SUBTREE
        }

        return FileVisitResult.CONTINUE
    }
}
