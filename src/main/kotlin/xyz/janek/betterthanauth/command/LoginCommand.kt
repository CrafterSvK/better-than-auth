package xyz.janek.betterthanauth.command

import net.minecraft.core.net.command.*
import xyz.janek.betterthanauth.Mod

class LoginCommand : Command("login") {
    override fun execute(
        handler: CommandHandler?,
        sender: CommandSender?,
        strings: Array<out String>?
    ): Boolean {
        if (sender !is PlayerCommandSender) {
            return false
        }

        if (strings == null) {
            return false
        }

        val username = sender.player.username

        if (!Mod.dataSource.isRegistered(username)) {
            sender.sendMessage("You are not registered!")
            return true
        }

        when (strings.size) {
            1 -> {
                val password = strings[0]
                val passwordHash = Mod.dataSource.getPasswordHash(username)

                if (Mod.passwordAuthentication.authenticate(password.toCharArray(), passwordHash)) {
                    sender.sendMessage("Logged in")
                }
            }
            else -> {
                return false
            }
        }

        return true
    }

    override fun opRequired(strings: Array<out String>?): Boolean {
        return false
    }

    override fun sendCommandSyntax(handler: CommandHandler?, sender: CommandSender?) {
        val server = handler is ServerCommandHandler
        val sentByPlayer = sender is PlayerCommandSender

        if (sentByPlayer) {
            sender?.sendMessage("/login <password>")
        }
    }
}