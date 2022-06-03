package com.example.imageskotlin.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.imageskotlin.ImageSliderActivity
import com.example.imageskotlin.R
import com.example.imageskotlin.model.ImagesModel


class ImagesListAdapter(private val context: Fragment) :
    RecyclerView.Adapter<ImagesListAdapter.ViewHolder>() {

    var items: ArrayList<ImagesModel>? = null

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.images_list_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {

            holder.nameHolder.text = items?.get(position)?.name

            val imagePath: String? = items?.get(position)?.path
            Glide.with(context)
                .load(imagePath)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.imageHolder)

            holder.itemView.setOnClickListener() {
//                val data = Bundle()
//                data.putParcelableArrayList("images_list", items)
                val intent = Intent(it.context, ImageSliderActivity::class.java)
                intent.putExtra("image_position", imagePath)
                intent.putExtra("images_list", items)
                it.context.startActivity(intent)
            }

            holder.optionHolder.setOnClickListener() {
                val popupMenu: PopupMenu = PopupMenu(it.context, holder.optionHolder)
                popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_rename ->
                            Toast.makeText(
                                it.context,
                                "You Clicked : " + item.title,
                                Toast.LENGTH_SHORT
                            ).show()
                        R.id.action_delete ->
                            Toast.makeText(
                                it.context,
                                "You Clicked : " + item.title,
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                    true
                })
                popupMenu.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return if (items != null) {
            items!!.size
        } else {
            0
        }
    }

    public fun setListItems(items: ArrayList<ImagesModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageHolder: ImageView = itemView.findViewById(R.id.imageView)
        val optionHolder: ImageView = itemView.findViewById(R.id.option)
        val nameHolder: TextView = itemView.findViewById(R.id.name)
    }
}