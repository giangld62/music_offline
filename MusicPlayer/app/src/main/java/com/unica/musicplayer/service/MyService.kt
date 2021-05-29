package com.unica.musicplayer.service

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import com.unica.musicplayer.PlayerActivity
import com.unica.musicplayer.R
import com.unica.musicplayer.SongFragment
import com.unica.musicplayer.application.MyApplication
import com.unica.musicplayer.broadcastreceiver.MyReceiver

class MyService : Service() {
    companion object {
        val ACTION_PLAY_PAUSE = 1
        val ACTION_NEXT = 2
        val ACTION_PREVIOUS = 3
    }

    private var mediaPlayer : MediaPlayer? = null
    private var isPlaying = false


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer = PlayerActivity.mediaPlayer
        val position = intent?.getIntExtra("position", -1)
        sendNotification(position!!)
        isPlaying = true
        val actionMusic = intent.getIntExtra("action_music_service",-1)
        handleActionMusic(actionMusic)
        return START_NOT_STICKY
    }

//    private fun sendNotification(position: Int?) {
//        val intent = Intent(this,PlayerActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
//        val songData = SongFragment.listOfSong[position!!]
//        val notification = NotificationCompat.Builder(this,MyApplication.CHANNEL_ID)
//            .setContentTitle("Music")
//            .setContentText(songData.title)
//            .setSmallIcon(R.drawable.ic_baseline_music_note_24)
//            .setContentIntent(pendingIntent)
//            .build()
//        startForeground(1,notification)
//    }

    private fun sendNotification(position: Int) {
        val songData = SongFragment.listOfSong[position]
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.music)
        val mediaSessionCompat = MediaSessionCompat(this, "tag")
        val notification = NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_music_note_24)
            .setSubText("Giang Le")
            .setContentTitle(songData.title)
            .setContentText(songData.artist)
            .setLargeIcon(bitmap)
            .addAction(R.drawable.ic_baseline_skip_previous_24, "Previous", getPendingIntent(this, ACTION_PREVIOUS))
            .addAction(R.drawable.ic_baseline_pause_24, "Pause", getPendingIntent(this, ACTION_PLAY_PAUSE))
            .addAction(R.drawable.ic_baseline_skip_next_24, "Next", getPendingIntent(this, ACTION_NEXT))
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(1)
                    .setMediaSession(mediaSessionCompat.sessionToken)
            )
            .build()
        startForeground(1, notification)
    }

    private fun handleActionMusic(action: Int) {
        when (action) {
            ACTION_PLAY_PAUSE -> {
                playPauseMusic()
            }
            ACTION_PREVIOUS -> {
                previousSong()
            }
            ACTION_NEXT -> {
                nextSong()
            }
        }
    }

    private fun getPendingIntent(context: Context,action: Int):PendingIntent{
        val intent = Intent(this,MyReceiver::class.java)
        intent.putExtra("action_music",action)
        return PendingIntent.getBroadcast(context.applicationContext,action,intent,PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun nextSong() {
        if (mediaPlayer != null) {
            PlayerActivity().nextThreadBtn()
        }
    }

    private fun previousSong() {
        if (mediaPlayer != null) {
            PlayerActivity().prevThreadBtn()
        }
    }


    private fun playPauseMusic() {
        if (mediaPlayer != null && isPlaying) {
            mediaPlayer!!.pause()
            isPlaying = false
        }
    }

    override fun onDestroy() {
        Log.e("tag","onDestroy")
        super.onDestroy()
    }
}