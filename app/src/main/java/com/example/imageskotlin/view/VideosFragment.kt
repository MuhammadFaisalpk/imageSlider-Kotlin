package com.example.imageskotlin.view

import android.app.Activity
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imageskotlin.R
import com.example.imageskotlin.adapter.ImagesListAdapter
import com.example.imageskotlin.adapter.VideosListAdapter
import com.example.imageskotlin.databinding.FragmentImagesBinding
import com.example.imageskotlin.databinding.FragmentVideosBinding
import com.example.imageskotlin.model.ImagesModel

class VideosFragment : Fragment() {

    private lateinit var binding: FragmentVideosBinding
    lateinit var videosListAdapter: VideosListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_videos,
            container, false
        )

        val recyclerView = binding.recyclerView

        videosListAdapter = VideosListAdapter(this)
        recyclerView.adapter = videosListAdapter

        val list: ArrayList<ImagesModel> =
            fetchGalleryVideos(this@VideosFragment.context as Activity)

        videosListAdapter.setListItems(list)

        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL, false
        )

        return binding.root
    }

    private fun fetchGalleryVideos(context: Activity): ArrayList<ImagesModel> {
        val columns = arrayOf(
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media._ID
        ) //get all columns of type images
        val orderBy = MediaStore.Video.Media.DATE_TAKEN //order data by date
        val imageCursor: Cursor = context.managedQuery(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null,
            null, "$orderBy DESC"
        ) //get all data in Cursor by sorting in DESC order
        val galleryImageUrls: ArrayList<ImagesModel> = ArrayList()
        for (i in 0 until imageCursor.count) {
            imageCursor.moveToPosition(i)
            val dataColumnData: Int =
                imageCursor.getColumnIndex(MediaStore.Video.Media.DATA) //get column index
            val dataColumnName: Int =
                imageCursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME) //get column index

            val name: String = imageCursor.getString(dataColumnName)
            val path: String = imageCursor.getString(dataColumnData)


            galleryImageUrls.add(
                ImagesModel(
                    name,
                    path
                )
            ) //get Image from column index
        }

        return galleryImageUrls
    }

}
