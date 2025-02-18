package kex.group.kexMc.commands

import kex.group.kexMc.KexMc
import kex.group.kexMc.util.Config
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AllowJoinCommand(private val plugin: KexMc) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender !is Player){
            return false
        }

        if(args.isEmpty()){
            sender.sendMessage("Please specify a player to allow.")
            return false
        }

        val config = Config(plugin, "config.yml")
        val allowedPlayers = config.getConfig().getList("allowed_players")
        config.getConfig().set("allowed_players", listOf(allowedPlayers, args[0]))
        config.save()

        sender.sendMessage("Player ${args[0]} is now allowed to join the server.")

        return true
    }
}