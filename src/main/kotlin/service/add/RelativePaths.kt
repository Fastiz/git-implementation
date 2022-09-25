package service.add

import directory.Root

object RelativePaths {
    fun makePathRelativeToRoot(root: Root, path: String): String {
        if (root.path.isEmpty()) {
            return path
        }

        return path.replace("${root.path}/", "")
    }
}
