package directory

object DataProvider {
    fun buildRoot(path: String = ""): Root = Root(path)

    fun buildGit(): Git = Git(buildRoot())

    fun buildObjects(): Objects = Objects(buildGit())

    fun buildHead(): Head = Head(buildGit())

    fun buildIndex(): Index = Index(buildGit())
}
