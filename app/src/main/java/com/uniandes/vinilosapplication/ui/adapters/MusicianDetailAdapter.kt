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
import com.uniandes.vinilosapplication.ui.fragments.MusicianDetailFragmentDirections

class MusicianDetailAdapter :
    RecyclerView.Adapter<MusicianDetailAdapter.MusicianDetailViewHolder>() {

    private val picasso = Picasso.get()

    var albums: List<AlbumModel> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicianDetailViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            MusicianDetailViewHolder.LAYOUT,
            parent,
            false
        )
        return MusicianDetailViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: MusicianDetailViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album = albums[position]

            val albumImage = holder.viewDataBinding.ivCoverAlbum
            picasso.load(albums[position].cover).into(albumImage)
        }
        holder.viewDataBinding.root.setOnClickListener {
            val action =
                MusicianDetailFragmentDirections.actionMusicianDetailFragmentToAlbumDetailFragment(
                    albums[position].albumId!!
                )
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }


    class MusicianDetailViewHolder(val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }
    }
}