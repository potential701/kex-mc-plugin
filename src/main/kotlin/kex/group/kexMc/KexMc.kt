package kex.group.kexMc

import kex.group.kexMc.commands.AllowJoinCommand
import kex.group.kexMc.listeners.PlayerJoinListener
import kex.group.kexMc.util.Config
import org.bukkit.plugin.java.JavaPlugin

class KexMc : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic
        logger.info("KEX MC is enabled.")

        val config = Config(this, "config.yml")
        config.getConfig().set("allowed_players", ArrayList<String>())
        config.save()

        PlayerJoinListener(this)

        getCommand("allow_join")?.setExecutor(AllowJoinCommand(this))
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("KEX MC is disabled.")
    }
}
