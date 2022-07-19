package service.init

import java.nio.file.Files
import java.nio.file.Paths

class InitServiceImpl : InitService {
    override fun run(){
        Files.createDirectories(Paths.get(".git-fastiz/objects"))
        Files.createDirectories(Paths.get(".git-fastiz/refs"))
    }
}