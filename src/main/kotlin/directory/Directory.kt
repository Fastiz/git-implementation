package directory

open class ProjectFile(val path: String)

open class ProjectDirectory(path: String) : ProjectFile(path) {
    fun extend(relativePath: String) = "$path/$relativePath"
        .replace("//", "/")
}

class Root(path: String) : ProjectDirectory(path)
class Git(root: Root) : ProjectDirectory(root.extend(".git-implementation"))
class Objects(git: Git) : ProjectDirectory(git.extend("objects"))
class Head(git: Git) : ProjectFile(git.extend("head"))
class Index(git: Git) : ProjectFile(git.extend("index"))