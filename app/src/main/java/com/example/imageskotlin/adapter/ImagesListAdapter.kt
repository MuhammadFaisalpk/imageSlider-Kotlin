package com.example.imageskotlin.adapter

import android.content.Intent
import android.os.Build
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.imageskotlin.ImageSliderActivity
import com.example.imageskotlin.R
import com.example.imageskotlin.model.ImagesModel
import java.io.File


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
    @RequiresApi(Build.VERSION_CODES.R)
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
                val intent = Intent(it.context, ImageSliderActivity::class.java)
                intent.putExtra("image_position", position)
                intent.putExtra("images_list", items)
                it.context.startActivity(intent)
            }

            holder.optionHolder.setOnClickListener() {
                val popupMenu = PopupMenu(it.context, holder.optionHolder)
                popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_rename -> {
                            val path =
                                items?.get(position)?.path
                            val str =
                                "/storage/emulated/0/Pictures/Screenshots/"

                            val fInternal = File(path.toString())
                            val fExternal = File(str, "New.png")
                            if (fInternal.exists()) {
                                val success = fInternal.renameTo(fExternal)

                                Toast.makeText(
                                    it.context,
                                    success.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        R.id.action_delete -> {
                            val path =
                                items?.get(position)?.path

                            val file = File(path.toString())

                            if (file.exists()) {

                                val deleted: Boolean = file.delete()
                                Toast.makeText(
                                    it.context,
                                    deleted.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    true
                }
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