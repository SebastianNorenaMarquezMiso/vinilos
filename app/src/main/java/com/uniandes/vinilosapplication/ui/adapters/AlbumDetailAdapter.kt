package com.uniandes.vinilosapplication.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.uniandes.vinilosapplication.R
import com.uniandes.vinilosapplication.data.model.TrackModel
import com.uniandes.vinilosapplication.databinding.TrackItemBinding

class AlbumDetailAdapter : RecyclerView.Adapter<AlbumDetailAdapter.AlbumDetailViewHolder>() {


    var tracks: List<TrackModel> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumDetailViewHolder {
        val withDataBinding: TrackItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumDetailViewHolder.LAYOUT,
            parent,
            false
        )
        return AlbumDetailViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumDetailViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.track = tracks[position]

        }
        /*  holder.viewDataBinding.root.setOnClickListener {
              val action = AlbumFragmentDirections.actionAlbumFragmentToCommentFragment(tracks[position].trackId)
              // Navigate using that action
              holder.viewDataBinding.root.findNavController().navigate(action)
          }*/
    }

    override fun getItemCount(): Int {
        return tracks.size
    }


    class AlbumDetailViewHolder(val viewDataBinding: TrackItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.track_item
        }
    }
}