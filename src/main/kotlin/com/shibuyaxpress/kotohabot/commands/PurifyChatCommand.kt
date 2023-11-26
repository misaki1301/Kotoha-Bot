package com.shibuyaxpress.kotohabot.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.Permission

class PurifyChatCommand: Command() {

    init {
        this.name = "purify"
        this.help = "Deletes chat generated from bots"
        this.botPermissions = arrayOf(Permission.ADMINISTRATOR, Permission.MESSAGE_HISTORY, Permission.MESSAGE_MANAGE)
        this.guildOnly = false
    }

    override fun execute(event: CommandEvent?) {
        if (event?.member!!.id == "199802242038104064" || event.member.id == "342864920687280128" || event.member.id == "424137718961012737") {
            val member = "235088799074484224"
            event.replyWarning("Starting delete process")
            event.textChannel?.getHistoryBefore(event.message, 100)!!.queue {
                val messages = it.retrievedHistory
                    //.filter { x -> x.author.isBot || x.contentRaw.contains("!play") || x.author.id == member}
                println("numero de mensajes encontrados $messages")
                for (message in messages) {
                    println(message.author.id+ ' '+message.contentRaw)
                    message.delete().queue()
                }
            }
            event.replySuccess("Finish cleaning task!")
            println("objeto member: $member")

        } else {
            event.replyError("Need to be admin or related u moron!")
        }
    }

}