package directory

object DirectoryModule {
    fun module(rootPath: String) = org.koin.dsl.module {
        single { Root(rootPath) }
        single { Git(root = get()) }
        single { Objects(git = get()) }
        single { Index(git = get()) }
        single { Head(git = get()) }
    }
}
