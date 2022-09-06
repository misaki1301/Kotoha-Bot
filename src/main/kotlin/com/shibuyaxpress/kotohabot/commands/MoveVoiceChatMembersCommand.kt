package com.shibuyaxpress.kotohabot.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class MoveVoiceChatMembersCommand(waiter: EventWaiter): Command() {
    private var waiter: EventWaiter
    init {
        this.name = "moveit"
        this.help = "Moves a every faggot to another voice channel"
        this.botPermissions = arrayOf(
            Permission.ADMINISTRATOR,
            Permission.VOICE_START_ACTIVITIES,
            Permission.VOICE_MOVE_OTHERS)
        this.guildOnly = false
        this.waiter = waiter
    }

    override fun execute(event: CommandEvent?) {
        event?.reply("A que canal deseas moverlos?")
        waiter.waitForEvent(MessageReceivedEvent::class.java,
            {
                e: MessageReceivedEvent ->
                e.author === event?.author && e.channel == event.channel && e.message !== event.message
            },
            {
                e: MessageReceivedEvent ->
                try {
                    val isValidUser = event!!.message.member?.roles?.any { it.id == "347249679668346880" || it.id == "347970224999366656" }
                    if (isValidUser!!) {
                        println("cantidad de channels ${event.message.guild.channels.size}")
                        val currentMember = event.message.member
                        val currentVoiceChannel = event.message.guild.voiceChannels.find { it.members.contains(currentMember) }
                        println(currentMember)
                        println(currentVoiceChannel)
                        val membersOnVoiceChannel = currentVoiceChannel!!.members
                        val voiceList = event.message.guild.voiceChannels
                        println(voiceList)
                        println(voiceList.size)
                        val desiredVoiceChannel = currentMember?.guild?.voiceChannels?.find { it.name.contains(e.message.contentRaw, true) }
                        println("VOICE CHANEL TO MOVE $desiredVoiceChannel")
                        println("MEBMERS TO MOVE $membersOnVoiceChannel")
                        for (member in membersOnVoiceChannel) {
                            event.guild.moveVoiceMember(member, desiredVoiceChannel).submit()
                        }
                        event.replySuccess("Listo")
                }
            } catch (e: Exception) {
                event?.reply("Try again later")
            }
        })
    }
}