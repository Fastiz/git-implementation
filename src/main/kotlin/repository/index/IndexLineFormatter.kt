package repository.index

import model.FileBlob
import model.FileBlobId

object IndexLineFormatter {
    fun parseLineToBlob(line: String): FileBlob {
        val (id, path) = line
            .replace("\n", "")
            .split(" ")

        return FileBlob(id = FileBlobId.from(id), path = path)
    }
}
