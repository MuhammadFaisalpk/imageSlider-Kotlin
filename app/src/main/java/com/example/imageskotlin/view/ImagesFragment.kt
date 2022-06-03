package com.example.imageskotlin.view

import android.app.Activity
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imageskotlin.R
import com.example.imageskotlin.adapter.ImagesListAdapter
import com.example.imageskotlin.databinding.FragmentImagesBinding
import com.example.imageskotlin.model.ImagesModel

class ImagesFragment : Fragment() {

    private lateinit var binding: FragmentImagesBinding
    lateinit var imagesListAdapter: ImagesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_images,
            container, false
        )

        val recyclerView = binding.recyclerView

        imagesListAdapter = ImagesListAdapter(this)
        recyclerView.adapter = imagesListAdapter

        val list: ArrayList<ImagesModel> =
            fetchGalleryImages(this@ImagesFragment.context as Activity)

        imagesListAdapter.setListItems(list)

        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL, false
        )

        return binding.root
    }

    private fun fetchGalleryImages(context: Activity): ArrayList<ImagesModel> {
        val columns = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media._ID
        ) //get all columns of type images
        val orderBy = MediaStore.Images.Media.DATE_TAKEN //order data by date
        val imageCursor: Cursor = context.managedQuery(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
            null, "$orderBy DESC"
        ) //get all data in Cursor by sorting in DESC order
        val galleryImageUrls: ArrayList<ImagesModel> = ArrayList()
        for (i in 0 until imageCursor.count) {
            imageCursor.moveToPosition(i)
            val dataColumnData: Int =
                imageCursor.getColumnIndex(MediaStore.Images.Media.DATA) //get column index
            val dataColumnName: Int =
                imageCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME) //get column index

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
