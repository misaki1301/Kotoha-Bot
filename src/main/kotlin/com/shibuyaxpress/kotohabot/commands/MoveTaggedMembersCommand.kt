package com.shibuyaxpress.kotohabot.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

/*
class MoveTaggedMembersCommand(waiter: EventWaiter): Command() {
    private var waiter: EventWaiter

    init {
        this.name = "movefaggots"
        this.help = "Moves a bunch of faggots tagged to another voice channel"
        this.botPermissions = arrayOf(
            Permission.ADMINISTRATOR,
            Permission.VOICE_MOVE_OTHERS,
            Permission.VOICE_START_ACTIVITIES
        )
        this.guildOnly = false
        this.waiter = waiter
    }

    override fun execute(event: CommandEvent?) {
        event?.reply("A quienes deseas mover?")
        waiter.waitForEvent(MessageReceivedEvent::class.java, {
            e: MessageReceivedEvent ->
            e.author === event?.author && e.channel == event.channel && e.message !== event.message
        },
        {
            e: MessageReceivedEvent ->
            try {
                val isValidUser = e!!.message.member?.roles?.any { it.id == "347249679668346880" || it.id == "347970224999366656" }
                if (isValidUser!!) {
                    println("cantidad de channels")
                    var members = e!!.message.mentionedMembers
                    event.event.
                } else {
                  event.replyError("No tienes permisos!")
                }
            } catch (e: Exception) {
                event?.reply("Intentalo mas tarde")
            }
        })
    }


}*/

class MoveTaggedMembersCommand: ListenerAdapter() {

}
