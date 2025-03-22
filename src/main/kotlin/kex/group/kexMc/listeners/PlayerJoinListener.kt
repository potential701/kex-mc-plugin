package kex.group.kexMc.listeners

import kex.group.kexMc.KexMc
import kex.group.kexMc.util.Config
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.time.Instant

class PlayerJoinListener(private val plugin: KexMc) : Listener {
    init {
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player: Player = event.player

        val config = Config(plugin, "config.yml")
        val allowedPlayers = config.getConfig().getStringList("allowed_players")

        if(!allowedPlayers.contains(player.player?.name)){
            player.ban("You are not allowed to join this server.", Instant.now(), "Bye")
        }
    }
}