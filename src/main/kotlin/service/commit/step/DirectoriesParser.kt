package service.commit.step

object DirectoriesParser {
    fun getDirectChildrenDirectories(directory: String, allDirectories: Collection<String>): List<String> {
        return allDirectories.filter { isChildrenDirectory(directory, it) }
    }

    fun isChildrenDirectory(directory: String, other: String): Boolean {
        val regex = "^$directory/[^/]+/[^/]+".toRegex()
        return other.matches(regex)
    }

    fun getAllDirectories(directoriesWithContent: List<String>): Set<String> {
        val directories = mutableSetOf<String>()

        directoriesWithContent.forEach {
            val subdirectories = getAllSubdirectories(it)
            directories.addAll(subdirectories)
        }

        return directories
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
