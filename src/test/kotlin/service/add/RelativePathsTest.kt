package service.add

import directory.DataProvider.buildRoot
import org.junit.Test
import service.add.RelativePaths.makePathRelativeToRoot
import kotlin.test.assertEquals

internal class RelativePathsTest {

    @Test
    fun `makePathRelativeToRoot - makes the path relative`() {
        val root = buildRoot("some/root/path")
        val path = "${root.path}/some/extension.kt"

        val result = makePathRelativeToRoot(root, path)

        assertEquals("some/extension.kt", result)
    }
}
