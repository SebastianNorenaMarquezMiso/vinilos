package com.uniandes.vinilosapplication.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uniandes.vinilosapplication.R
import com.uniandes.vinilosapplication.data.model.AlbumModel
import com.uniandes.vinilosapplication.databinding.AlbumItemBinding
import com.uniandes.vinilosapplication.ui.fragments.CollectorDetailFragmentDirections

class CollectorDetailAdapter :
    RecyclerView.Adapter<CollectorDetailAdapter.CollectorDetailViewHolder>() {

    private val picasso = Picasso.get()

    var albums: List<AlbumModel> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorDetailViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorDetailViewHolder.LAYOUT,
            parent,
            false
        )
        return CollectorDetailViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorDetailViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album = albums[position]

            val albumImage = holder.viewDataBinding.ivCoverAlbum
            picasso.load(albums[position].cover).into(albumImage)
        }
        holder.viewDataBinding.root.setOnClickListener {
            val action =
                CollectorDetailFragmentDirections.actionCollectorDetailFragmentToAlbumDetailFragment(
                    albums[position].albumId!!
                )
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }


    class CollectorDetailViewHolder(val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }
    }
}