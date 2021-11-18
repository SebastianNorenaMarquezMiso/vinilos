package com.uniandes.vinilosapplication.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uniandes.vinilosapplication.R
import com.uniandes.vinilosapplication.data.model.AlbumModel
import com.uniandes.vinilosapplication.databinding.AlbumItemBinding
import com.uniandes.vinilosapplication.ui.fragments.AlbumFragmentDirections

class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {


    private val picasso = Picasso.get()

    var albums: List<AlbumModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumViewHolder.LAYOUT,
            parent,
            false
        )
        return AlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album = albums[position]
            val simpleYear = albums[position].releaseDate?.subSequence(0, 4)
            holder.viewDataBinding.textView7.text = albums[position].recordLabel + " - " + simpleYear
            val albumImage = holder.viewDataBinding.ivCoverAlbum
//            val albumImage = view!!.findViewById<ImageView>(R.id.iv_logo_album)
            picasso.load(albums[position].cover).into(albumImage)
        }
        holder.viewDataBinding.root.setOnClickListener {
            val action =
                AlbumFragmentDirections.actionAlbumFragmentToAlbumDetailFragment(albums[position].albumId!!)
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    @BindingAdapter("imageUrl")
    fun setImageUrl(view: ImageView, path: String) {
        picasso.load(path).into(view)
    }

    class AlbumViewHolder(val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }
    }
}