package xyz.janek.betterthanauth.command

import net.minecraft.core.net.command.*
import xyz.janek.betterthanauth.Mod.Companion.dataSource

class RegisterCommand : Command("register", "password", "passwordConfirm") {

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

        if (strings.size == 2) {
            val password = strings[0]
            val passwordConfirm = strings[1]

            if (password == passwordConfirm) {
                if (dataSource.isRegistered(sender.player.username)) {
                    sender.sendMessage("Already registered!")

                    return true
                }

                if (dataSource.saveCredentials())
                sender.sendMessage("Successfully registered!")
            } else {
                sender.sendMessage("Passwords do not match")
            }

            return true
        }

        return false
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