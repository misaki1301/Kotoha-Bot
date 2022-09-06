package com.shibuyaxpress.kotohabot.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import com.shibuyaxpress.kotohabot.handlers.AudioPlayerSendHandler
import net.dv8tion.jda.api.Permission

class MusicYTCommand(waiter: EventWaiter): Command() {

    private var waiter: EventWaiter

    init {
        this.name = "play"
        this.help = "Moves a every faggot to another voice channel"
        this.botPermissions = arrayOf(
            Permission.ADMINISTRATOR,
            Permission.VOICE_START_ACTIVITIES,
            Permission.VOICE_MOVE_OTHERS
        )
        this.guildOnly = false
        this.waiter = waiter
    }

    override fun execute(event: CommandEvent?) {
        val messageEvent = event?.event
        println("I heard it")
            if (messageEvent?.isFromGuild == true) {
                val identifier = event.message.embeds[0].url
                println("the song to sing by Kotoha $identifier")
                val guild = messageEvent.guild
                //val channel = guild.getVoiceChannelsByName("road-to-ultra", true)[0]
                //get voice channel by member
                val voiceChannel = messageEvent.member?.voiceState?.channel
                val manager = guild.audioManager
                //open voice channel to connect in
                //manager.openAudioConnection(voiceChannel)

                val playerManager = DefaultAudioPlayerManager()
                AudioSourceManagers.registerRemoteSources(playerManager)
                AudioSourceManagers.registerLocalSource(playerManager)

                val player = playerManager.createPlayer()

                val trackScheduler = TrackScheduler(player)

                manager.sendingHandler = AudioPlayerSendHandler(player)

                //connects to channel
                manager.openAudioConnection(voiceChannel)
                println("nothing happened???")

                playerManager.loadItem(identifier, object : AudioLoadResultHandler {

                    override fun trackLoaded(track: AudioTrack?) {
                       //trackScheduler.queue(track)
                        event.reply("now playing")
                        player.playTrack(track)
                    }

                    override fun playlistLoaded(playlist: AudioPlaylist?) {
                        //for (track in playlist!!.tracks) {
                       //     trackScheduler.queue(track)
                        //}r
                    }

                    override fun noMatches() {
                        //notify user that Kotoha can play the item provided
                        event.reply("I can't found the song :(")
                    }

                    override fun loadFailed(exception: FriendlyException?) {
                        //notify user that Kotoha is embarrassed
                        event.reply("I can't sing that!!!")
                    }

                })
            }
        }
}

class TrackScheduler(player: AudioPlayer?): AudioEventAdapter() {

    override fun onPlayerPause(player: AudioPlayer?) {
        super.onPlayerPause(player)
        //player was paused
    }

    override fun onPlayerResume(player: AudioPlayer?) {
        super.onPlayerResume(player)
        //player was resumed
    }

    override fun onTrackStart(player: AudioPlayer?, track: AudioTrack?) {
        super.onTrackStart(player, track)
        //a track start playing
    }

    override fun onTrackEnd(player: AudioPlayer?, track: AudioTrack?, endReason: AudioTrackEndReason?) {
        super.onTrackEnd(player, track, endReason)
        if (endReason!!.mayStartNext) {
            // Start next track
        }

        // endReason == FINISHED: A track finished or died by an exception (mayStartNext = true).
        // endReason == LOAD_FAILED: Loading of a track failed (mayStartNext = true).
        // endReason == STOPPED: The player was stopped.
        // endReason == REPLACED: Another track started playing while this had not finished
        // endReason == CLEANUP: Player hasn't been queried for a while, if you want you can put a
        //
    }

    override fun onTrackException(player: AudioPlayer?, track: AudioTrack?, exception: FriendlyException?) {
        super.onTrackException(player, track, exception)
        //an already playing track threw an exception (tack end event will still be received separately!)
    }

    override fun onTrackStuck(player: AudioPlayer?, track: AudioTrack?, thresholdMs: Long) {
        super.onTrackStuck(player, track, thresholdMs)
        //audio track has been unable to provide us any audio. might want to just start a new track
    }
}