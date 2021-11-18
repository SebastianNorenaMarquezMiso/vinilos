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
import com.uniandes.vinilosapplication.data.model.MusicianModel
import com.uniandes.vinilosapplication.databinding.MusicianItemBinding
import com.uniandes.vinilosapplication.ui.fragments.MusicianFragmentDirections

class MusiciansAdapter : RecyclerView.Adapter<MusiciansAdapter.MusicianViewHolder>() {


    private val picasso = Picasso.get()

    var musicians: List<MusicianModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicianViewHolder {
        val withDataBinding: MusicianItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            MusicianViewHolder.LAYOUT,
            parent,
            false
        )
        return MusicianViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: MusicianViewHolder, position: Int) {
        holder.viewDataBinding.also {

            it.musician = musicians[position]

            holder.viewDataBinding.genreMusician.text = musicians[position].albums?.get(1)?.genre

            val musicianImage = holder.viewDataBinding.ivCoverMusician
            picasso.load(musicians[position].image).resize(400, 400).into(musicianImage)
        }
        holder.viewDataBinding.root.setOnClickListener {
            val action =
                MusicianFragmentDirections.actionMusicianFragmentToMusicianDetailFragment(musicians[position].id!!)
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return musicians.size
    }

    @BindingAdapter("imageUrl")
    fun setImageUrl(view: ImageView, path: String) {
        picasso.load(path).into(view)
    }

    class MusicianViewHolder(val viewDataBinding: MusicianItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.musician_item
        }
    }
}