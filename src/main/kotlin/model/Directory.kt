package model

enum class Directory(val path: String) {
    ROOT("."),
    GIT(ROOT.extendPath(".fastiz-git")),
    OBJECTS(GIT.extendPath("objects")),
}

enum class File(val path: String) {
    HEAD(Directory.GIT.extendPath("head")),
}

fun Directory.extendPath(filePath: String): String {
    return "$path/$filePath"
        .replace("//", "/")
}
