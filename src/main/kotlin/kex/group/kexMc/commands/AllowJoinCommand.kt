package kex.group.kexMc.commands

import kex.group.kexMc.KexMc
import kex.group.kexMc.util.Config
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AllowJoinCommand(private val plugin: KexMc) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val isAllowed = sender.isOp || sender !is Player

        if(!isAllowed){
            sender.sendMessage("You don't have permission to use this command.")
            return false
        }

        if(args.isEmpty()){
            sender.sendMessage("Please specify a player to allow.")
            return false
        }

        val argPlayer = args[0]
        val config = Config(plugin, "config.yml")

        @Suppress("UNCHECKED_CAST")
        val allowedPlayers = config.getConfig().getList("allowed_players") as? MutableList<String>
        allowedPlayers?.add(argPlayer)
        config.getConfig().set("allowed_players", allowedPlayers)
        config.save()

        sender.sendMessage("Player $argPlayer is now allowed to join the server.")

        return true
    }
}