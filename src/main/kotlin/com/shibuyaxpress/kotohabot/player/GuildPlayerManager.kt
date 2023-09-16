package com.shibuyaxpress.kotohabot.player

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.shibuyaxpress.kotohabot.commands.TrackScheduler

object GuildPlayerManager {
    private val players: MutableMap<Long, AudioPlayer> = mutableMapOf()
    //private val playerManager = DefaultAudioPlayerManager()
    lateinit var trackScheduler: TrackScheduler

    fun getPlayer(guildId: Long): AudioPlayer {
        return players.computeIfAbsent(guildId) {createPlayer()}
    }

    private fun createPlayer(): AudioPlayer {
        val playerManager = DefaultAudioPlayerManager()
        val player = playerManager.createPlayer()
        trackScheduler = TrackScheduler(player)
        player.addListener(trackScheduler)
        return player
    }
}