package kex.group.kexMc.commands

import kex.group.kexMc.KexMc
import kex.group.kexMc.util.Config
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class DisallowJoinCommand(private val plugin: KexMc) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val isAllowed = sender.isOp || sender !is Player

        if (!isAllowed) {
            sender.sendMessage("You do not have permission to perform this command!")
            return false
        }

        if (args.isEmpty()) {
            sender.sendMessage("Please specify a player to disallow.")
            return false
        }

        val playerToDisallow = args[0]
        val config = Config(plugin, "config.yml")
        val allowedPlayers = config.getConfig().getStringList("allowed_players")

        allowedPlayers.removeAll { it == playerToDisallow }

        config.getConfig().set("allowed_players", allowedPlayers)
        config.save()

        sender.sendMessage("Player $playerToDisallow is now disallowed from joining the server.")

        return true
    }
}