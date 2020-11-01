package com.poc.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.poc.R
import com.poc.data.response.ImageItem
import com.poc.utility.loadImage
import kotlinx.android.synthetic.main.image_list_item_view.view.*

class ImagesListAdapter(
    private val listener: ItemClickListener
) :
    RecyclerView.Adapter<ImagesListAdapter.ViewHolder>() {
    private var imageList: MutableList<ImageItem>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_list_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            imageList?.get(position)?.apply {
                txtImgTitle.text = this.title
                imgSearcheItem.loadImage(
                    context = context,
                    link = link,
                    errorResId = R.drawable.ic_error,
                    placeHolderRes = R.drawable.ic_launcher_background,
                    pbImageLoader = pbImageLoader
                )
                setOnClickListener {
                    listener.showLargeImage(this)
                }
            }
        }


    }

    override fun getItemCount() = imageList?.size ?: 0
    fun setImageList(data: MutableList<ImageItem>?) {
        this.imageList = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    interface ItemClickListener {
        fun showLargeImage(imageItem: ImageItem)
    }
}