package com.shibuyaxpress.kotohabot.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import com.shibuyaxpress.kotohabot.constants.Stores.stores
import net.dv8tion.jda.api.Permission

class SpringCleaningChatCommand: Command() {
    init {
        this.name = "springcleaning"
        this.help = "Spring cleaning chat from stores like Amazon, Wallmart, etc."
        this.guildOnly = false
        this.botPermissions =  arrayOf(Permission.MESSAGE_HISTORY, Permission.ADMINISTRATOR, Permission.MESSAGE_MANAGE)
    }

    //array with name of stores online


    override fun execute(event: CommandEvent?) {
        //delete messages which contains "amazon"
        //val messages = event?.channel?.history?.retrievePast(100)?.complete()
        val messages = event?.textChannel?.getHistoryBefore(event.message, 100)!!.queue { messageHistory ->
            val message = messageHistory.retrievedHistory.filter { message ->
                stores.any { store -> message.contentRaw.contains(store)}
            }
            val embeds = messageHistory.retrievedHistory.filter {
                println("messages from user ${it.author.name} has ${it.embeds} images on messages")
                it.embeds.isNotEmpty() || it.attachments.isNotEmpty()
            }
            val messageZero = messageHistory.retrievedHistory.find {
                it.id == "574467183032270848-1014295150933053450"
            }
            println("Message ${messageZero}")
            println("Embed ${messageZero?.embeds}")
            println("Attachment ${messageZero?.attachments}")
            if (embeds.isNotEmpty()) {
                embeds.forEach { embed ->
                    embed.embeds.forEach {
                        println("images name${it.url}")
                    }
                    embed.delete().queue()
                }
                event.replySuccess("Listo!")
            } else {
                event.reply("No messages found")
            }
        }
    }

}