package logger.util

import logger.Logger
import model.FileBlob

object FileBlob {
    fun Logger.debugFileBlobs(fileBlobs: Iterable<FileBlob>) {
        fileBlobs.forEach { fileBlob ->
            printDebug("\t${fileBlob.id.value} ${fileBlob.path}")
        }
    }
}