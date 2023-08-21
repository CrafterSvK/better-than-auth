package xyz.janek.betterthanauth.command

import net.minecraft.core.net.command.Command
import net.minecraft.core.net.command.CommandHandler
import net.minecraft.core.net.command.CommandSender

class LoginCommand : Command("login", "password") {
    override fun execute(
        commandHandler: CommandHandler?,
        commandSender: CommandSender?,
        strings: Array<out String>?
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun opRequired(strings: Array<out String>?): Boolean {
        TODO("Not yet implemented")
    }

    override fun sendCommandSyntax(commandHandler: CommandHandler?, commandSender: CommandSender?) {
        TODO("Not yet implemented")
    }
}