package kex.group.kexMc.commands

import kex.group.kexMc.KexMc
import kex.group.kexMc.data.ConfigListConstants
import kex.group.kexMc.data.Landmark
import kex.group.kexMc.util.Config
import kotlinx.serialization.json.Json
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class LandmarkCommand(private val plugin: KexMc) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player || args.isEmpty()) return false

        val config = Config(plugin,"config.yml")
        val landmarks: MutableList<Landmark> =
            config
                .getConfig()
                .getStringList(ConfigListConstants.LANDMARKS)
                .map { Json.decodeFromString<Landmark>(it) }
                .toMutableList()
        val landmark = Landmark(args.getOrNull(1), sender.location.x, sender.location.y, sender.location.z)

        return when (args[0]) {
            "get" -> getLandmark(landmark, sender)
            "set" -> setLandmark(landmark, landmarks, config, sender)
            "remove" -> removeLandmark(landmark, landmarks, config, sender)
            "list" -> listLandmarks(landmarks, sender)
            else -> false
        }
    }

    private fun getLandmark(landmark: Landmark, sender: Player): Boolean {
        if(landmark.name.isNullOrEmpty()) {
            sender.sendMessage("Please enter a landmark name.")
            return false
        }

        sender.sendMessage("${ChatColor.GOLD}${ChatColor.ITALIC}${landmark.name}:")
        sender.sendMessage("${ChatColor.RED}X: ${landmark.x.toInt()}")
        sender.sendMessage("${ChatColor.GREEN}Y: ${landmark.y.toInt()}")
        sender.sendMessage("${ChatColor.BLUE}Z: ${landmark.z.toInt()}")

        return true
    }

    private fun setLandmark(
        landmark: Landmark,
        landmarks: MutableList<Landmark>,
        config: Config,
        sender: CommandSender
    ): Boolean {
        if(landmark.name.isNullOrEmpty()) {
            sender.sendMessage("Please enter a landmark name.")
            return false
        }

        if (landmarks.firstOrNull { it.name == landmark.name } != null) {
            sender.sendMessage("Landmark with the name ${landmark.name} already exists.")
            return false
        }

        landmarks.add(landmark)

        config.getConfig().set(ConfigListConstants.LANDMARKS, landmarks.map { Json.encodeToString(it) })
        config.save()

        return true
    }

    private fun removeLandmark(
        landmark: Landmark,
        landmarks: MutableList<Landmark>,
        config: Config,
        sender: CommandSender
    ): Boolean {
        if(landmark.name.isNullOrEmpty()) {
            sender.sendMessage("Please enter a landmark name.")
            return false
        }

        val landmarksToRemove = landmarks.filter { it.name == landmark.name }
        if (landmarksToRemove.isEmpty()) {
            sender.sendMessage("Landmark with the name ${landmark.name} does not exist.")
            return false
        }

        landmarks.removeAll { landmarksToRemove.contains(it) }

        config.getConfig().set(ConfigListConstants.LANDMARKS, landmarks.map { Json.encodeToString(it) })
        config.save()

        return true
    }

    private fun listLandmarks(landmarks: List<Landmark>, sender: CommandSender): Boolean {
        if (landmarks.isEmpty()) {
            sender.sendMessage("There are no landmarks.")
            return false
        }

        landmarks.forEachIndexed { index, landmark ->
            sender.sendMessage("${ChatColor.GOLD}${index + 1}. ${landmark.name}")
        }

        return true
    }
}