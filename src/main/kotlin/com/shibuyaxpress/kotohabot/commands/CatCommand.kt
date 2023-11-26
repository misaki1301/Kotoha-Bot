package com.shibuyaxpress.kotohabot.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import com.shibuyaxpress.kotohabot.network.AnimeDetectionAPI
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import java.awt.Color

class CatCommand: Command() {
    init {
        this.name = "cat"
        this.help = "shows a random cat"
        this.botPermissions = arrayOf(Permission.MESSAGE_EMBED_LINKS)
        this.guildOnly = false
    }
    override fun execute(event: CommandEvent?) {
        runBlocking {
            val image = call()
            event?.reply(EmbedBuilder()
                .setColor(Color.GREEN)
                .setImage(image)
                .build())
        }
    }

    private suspend fun call(): String {
        return AnimeDetectionAPI.meowClient.getRandomCatAsync().await().file
    }
}