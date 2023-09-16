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
import com.shibuyaxpress.kotohabot.player.GuildPlayerManager
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.MessageEmbed

class MusicYTCommand(waiter: EventWaiter): Command() {

    private var waiter: EventWaiter

    init {
        this.name = "play"
        this.help = "Moves a every faggot to another voice channel"
        this.botPermissions = arrayOf(
            Permission.ADMINISTRATOR,
            Permission.VOICE_START_ACTIVITIES,
            Permission.VOICE_MOVE_OTHERS,
        )
        this.guildOnly = false
        this.waiter = waiter
    }

    override fun execute(event: CommandEvent?) {
        val messageEvent = event?.event
        println("I heard it")
            if (messageEvent?.isFromGuild == true) {
                val identifier = event.args
                println("the song to sing by Kotoha $identifier")
                val guild = messageEvent.guild

                val voiceChannel = messageEvent.member?.voiceState?.channel
                val manager = guild.audioManager

                val playerManager = DefaultAudioPlayerManager()
                AudioSourceManagers.registerRemoteSources(playerManager)
                AudioSourceManagers.registerLocalSource(playerManager)

                //val player = playerManager.createPlayer()
                val player = GuildPlayerManager.getPlayer(guild.idLong)
                val trackScheduler = GuildPlayerManager.trackScheduler

                manager.sendingHandler = AudioPlayerSendHandler(player)

                //connects to channel
                if (!manager.isConnected) {
                    manager.openAudioConnection(voiceChannel)
                }
                println("nothing happened???")

                playerManager.loadItem(identifier, object : AudioLoadResultHandler {

                    override fun trackLoaded(track: AudioTrack?) {
                       //trackScheduler.queue(track)
                        event.reply("now playing")
                        //player.playTrack(track)
                        if (track != null) {
                            trackScheduler.queue(track)
                            event.reply("Added to queue: ${track.info.title}")
                        }
                    }

                    override fun playlistLoaded(playlist: AudioPlaylist?) {
                        if (playlist != null) {
                            for (track in playlist.tracks) {
                                trackScheduler.queue(track)
                            }
                            event.reply("Added ${playlist.tracks.size} tracks to queue")
                        }
                    }

                    override fun noMatches() {
                        //notify user that Kotoha can play the item provided
                        event.reply("I can't found the song :(")
                    }

                    override fun loadFailed(exception: FriendlyException?) {
                        //notify user that Kotoha is embarrassed
                        event.reply("I can't sing that!!! ${exception?.message}")
                    }

                })
            }
        }
}

class TrackScheduler(private val player: AudioPlayer?): AudioEventAdapter() {

    private val queue: MutableList<AudioTrack> = mutableListOf()

    fun queue(track: AudioTrack) {
        if (!queue.contains(track)) {
            queue.add(track.makeClone())
            if (player?.playingTrack == null) {
                player?.startTrack(track, false)
            }
        }
    }

    fun nextTrack() {
        queue.removeAt(0)
        val nextTrack = queue.firstOrNull()
        player?.startTrack(nextTrack, false)
    }

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

        when (endReason) {
            AudioTrackEndReason.FINISHED -> {
                println("Finish track")
                println(queue)
                nextTrack()
            }
            AudioTrackEndReason.LOAD_FAILED -> println("Failed to Load")
            AudioTrackEndReason.STOPPED -> println("Track Stopped")
            AudioTrackEndReason.REPLACED -> println("Replaced current track")
            AudioTrackEndReason.CLEANUP -> println("Cleaning mess")
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
        println(exception?.localizedMessage)
    }

    override fun onTrackStuck(player: AudioPlayer?, track: AudioTrack?, thresholdMs: Long) {
        super.onTrackStuck(player, track, thresholdMs)
        //audio track has been unable to provide us any audio. might want to just start a new track
    }
}