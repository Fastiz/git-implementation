package service.commit.step

object DirectoriesParser {
    fun getChildrenDirectories(directory: String, allDirectories: List<String>): List<String> {
        val directorySegments = directory.split("/")

        val result = mutableSetOf<String>()
        val descendants = allDirectories
            .filter { it.contains(directory) }
            .filter { it != directory }

        descendants.forEach {
            val segments = it.split("/")
            val childrenDirectory = segments[directorySegments.size]
            result.add("$directory/$childrenDirectory")
        }

        return result.toList()
    }

    fun getAllDirectories(directoriesWithContent: List<String>): List<String> {
        val directories = mutableSetOf<String>()

        directoriesWithContent.forEach {
            val subdirectories = getAllSubdirectories(it)
            directories.addAll(subdirectories)
        }

        return directories.toList()
    }

    private fun getAllSubdirectories(directory: String): List<String> {
        val result = mutableListOf<String>()
        val segments = directory.split("/")

        var lastPath = ""
        segments.forEach {
            lastPath = if (lastPath.isBlank()) {
                it
            } else {
                "$lastPath/$it"
            }

            result.add(lastPath)
        }
        return result
    }
}