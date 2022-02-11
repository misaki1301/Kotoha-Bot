package com.shibuyaxpress.kotohabot.config

import com.jagrosh.jdautilities.command.CommandClientBuilder
import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.shibuyaxpress.kotohabot.commands.MoveVoiceChatMembersCommand
import com.shibuyaxpress.kotohabot.commands.PurifyChatCommand
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Configuration
class BotConfiguration {
    private var token: String = "Njk5NDIxMTU2NTI4MjI2MzE2.XpUItg.dWHHnY5P7W_YcPn0vWkX5YxqUFo"
    private var waiter = EventWaiter()
    private var client = CommandClientBuilder()

    init {
     createInstance()
    }

    private final fun createInstance() {
        client.setPrefix("!koto")
        client.setOwnerId("199802242038104064")

        client.addCommands(
            PurifyChatCommand(),
            MoveVoiceChatMembersCommand(waiter)
        )

        val game = Activity.playing("Hello World!")

        JDABuilder.createDefault(token)
            .addEventListeners(waiter)
            .setStatus(OnlineStatus.ONLINE)
            .setActivity(game)
            .addEventListeners(waiter, client.build())
            .build()
    }

}