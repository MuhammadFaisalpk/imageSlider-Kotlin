package com.example.imageskotlin.view

import android.os.Bundle
import android.os.Environment
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
import com.example.imageskotlin.databinding.FragmentDocsBinding
import java.io.File


class DocsFragment : Fragment() {

    lateinit var imagesListAdapter: ImagesListAdapter

    private lateinit var binding: FragmentDocsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //   userViewModel = ViewModelProviders.of(activity!!).get(UserListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_docs,
            container, false
        )
        val recyclerView = binding.recyclerView

        imagesListAdapter = ImagesListAdapter(this)
        recyclerView.adapter = imagesListAdapter

        var gpath: String = Environment.getExternalStorageDirectory().absolutePath
        var fullpath = File(gpath + File.separator + "Download")

        imageReaderNew(fullpath)

//        val list: ArrayList<ImagesModel> =
//            fetchGalleryImages(this@ImagesFragment.context as Activity)

//        imagesListAdapter.setListItems(list)

        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL, false
        )

        return binding.root
    }

    fun imageReaderNew(root: File) {
        val fileList: ArrayList<File> = ArrayList()
        val listAllFiles = root.listFiles()

        if (listAllFiles != null && listAllFiles.isNotEmpty()) {
            for (currentFile in listAllFiles) {
//                if (currentFile.name.endsWith(".pdf")) {
                // File absolute path
                Log.e("downloadFilePath", currentFile.absolutePath)
                // File Name
                Log.e("downloadFileName", currentFile.name)
                fileList.add(currentFile.absoluteFile)
//                }
            }
            Log.w("fileList", "" + fileList.size)
        }
    }
}
