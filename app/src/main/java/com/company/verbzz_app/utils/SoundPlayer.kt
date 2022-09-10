package com.company.verbzz_app.utils

import android.content.Context
import android.media.MediaPlayer

fun playSound(context: Context, audio: Int) {
    val sound = MediaPlayer.create(context, audio)
    if(sound.isPlaying) {
        sound.seekTo(0)
    }
    sound.start()
    sound.setOnCompletionListener(MediaPlayer::release)
}