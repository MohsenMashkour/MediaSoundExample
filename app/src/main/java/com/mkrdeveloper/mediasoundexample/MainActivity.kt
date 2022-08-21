package com.mkrdeveloper.mediasoundexample

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var fbtnPlay: FloatingActionButton
    lateinit var fbtnPause: FloatingActionButton
    lateinit var fbtnStop: FloatingActionButton

    lateinit var seekBar: SeekBar

    private var currentSong = mutableListOf(
        R.raw.song1,
        R.raw.bumper
    )

    private var mp: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fbtnPlay = findViewById(R.id.fbtnPlay)
        fbtnPause = findViewById(R.id.fbtnPause)
        fbtnStop = findViewById(R.id.fbtnStop)

        seekBar = findViewById(R.id.seekBar)


        controlSong(currentSong[1])

    }

    private fun controlSong(id: Int) {

        fbtnPlay.setOnClickListener {
            if (mp == null){
                mp = MediaPlayer.create(this, id)
            }
            mp?.start()

            initSeekBar()
        }

        fbtnPause.setOnClickListener {

            if (mp !== null) mp?.pause()
        }

        fbtnStop.setOnClickListener {

            if ( mp !== null ){
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, user: Boolean) {

                if (user) mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
    }



    private fun initSeekBar() {

        seekBar.max = mp!!.duration

        val handler = Handler()
        handler.postDelayed(object : Runnable{
            override fun run() {
                try{
                    seekBar.progress = mp!!.currentPosition
                    handler.postDelayed(this, 1000)
                }catch (e: Exception){
                    seekBar.progress = 0
                }
            }

        },0)
    }


}