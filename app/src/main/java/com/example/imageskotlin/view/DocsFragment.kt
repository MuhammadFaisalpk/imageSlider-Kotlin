package com.example.imageskotlin.view

import android.app.Activity
import android.database.Cursor
import android.os.Bundle
import android.os.Environment
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
import com.example.imageskotlin.databinding.FragmentDocsBinding
import com.example.imageskotlin.model.ImagesModel
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

        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL, false
        )

        return binding.root
    }
}
