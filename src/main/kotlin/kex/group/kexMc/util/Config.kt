package kex.group.kexMc.util

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File

class Config {
    private var file: File
    private var config: FileConfiguration

    constructor(plugin: Plugin, path: String) : this(plugin.dataFolder.absolutePath + "/" + path)

    constructor(path: String){
        file = File(path)
        config = YamlConfiguration.loadConfiguration(file)
    }

    fun save(): Boolean{
        try {
            config.save(file)

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun getConfig(): FileConfiguration{
        return config
    }
}