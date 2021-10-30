package com.uniandes.vinilosapplication.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.uniandes.vinilosapplication.R
import com.uniandes.vinilosapplication.data.model.CommentModel
import com.uniandes.vinilosapplication.databinding.CommentItemBinding

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {
    class CommentViewHolder(val viewDataBinding: CommentItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_item
        }
    }

    var comments: List<CommentModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val withDataBinding: CommentItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CommentViewHolder.LAYOUT,
            parent,
            false
        )
        return CommentViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.comment = comments[position]
        }
        /*holder.viewDataBinding.root.setOnClickListener {
            val action = CommentFragmentDirections.actionCommentFragment2ToCommentFragment2()
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
        }*/
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}