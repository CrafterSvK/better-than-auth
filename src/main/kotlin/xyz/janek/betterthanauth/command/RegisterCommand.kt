package xyz.janek.betterthanauth.command

import net.minecraft.core.net.command.*
import xyz.janek.betterthanauth.Mod
import xyz.janek.betterthanauth.Mod.Companion.dataSource

class RegisterCommand : Command("register") {

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

        when (strings.size) {
            2 -> {
                val (password, passwordConfirm) = strings

                if (password == passwordConfirm) {
                    val name = sender.player.username

                    if (dataSource.isRegistered(sender.player.username)) {
                        sender.sendMessage("Already registered!")

                        return true
                    }

                    val passwordHash = Mod.passwordAuthentication.hash(password.toCharArray())

                    if (dataSource.saveCredentials(name, passwordHash)) {
                        sender.sendMessage("Successfully registered!")
                    }
                } else {
                    sender.sendMessage("Passwords do not match")
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
            sender?.sendMessage("/register <password> <password_confirm>")
        }
    }
}