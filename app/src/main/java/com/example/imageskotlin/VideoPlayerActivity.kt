package com.example.imageskotlin

import android.net.Uri
import android.net.Uri.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.MediaController
import android.widget.VideoView
import androidx.databinding.DataBindingUtil
import com.example.imageskotlin.databinding.ActivityVideoPlayerBinding

class VideoPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoPlayerBinding
    lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        getSetItems()
    }

    private fun getSetItems() {
        var bundle: Bundle? = intent.extras
        var path: String? = bundle!!.getString("video_path")

        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        //specify the location of media file
        val uri: Uri =
            parse(Environment.getExternalStorageDirectory().getPath() + "/Movies/video.mp4")
        //Setting MediaController and URI, then starting the videoView
        videoView.setMediaController(mediaController)
        videoView.setVideoPath(path)
        videoView.requestFocus()
        videoView.start()
    }

    private fun initViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_player)

        videoView = binding.videoView

    }
}