package kex.group.kexMc

import kex.group.kexMc.commands.AllowJoinCommand
import kex.group.kexMc.commands.DisallowJoinCommand
import kex.group.kexMc.listeners.PlayerJoinListener
import kex.group.kexMc.util.Config
import org.bukkit.plugin.java.JavaPlugin

class KexMc : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic
        logger.info("KEX MC is enabled.")

        createAllowedPlayersConfig()

        PlayerJoinListener(this)

        getCommand("allow_join")?.setExecutor(AllowJoinCommand(this))
        getCommand("disallow_join")?.setExecutor(DisallowJoinCommand(this))
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("KEX MC is disabled.")
    }

    private fun createAllowedPlayersConfig(){
        val config = Config(this, "config.yml")
        val allowedPlayers = config.getConfig().getList("allowed_players")
        if(allowedPlayers?.isEmpty() == false) return

        config.getConfig().set("allowed_players", listOf<String>())
        config.save()
    }
}
