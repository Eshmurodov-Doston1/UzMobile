package com.example.uzmobile.adapters.serviceAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobile.databinding.ItemMinutBinding
import com.example.uzmobile.databinding.ItemReplaceBinding
import com.example.uzmobile.databinding.ItemServiceBinding
import com.example.uzmobile.models.Minut
import com.example.uzmobile.models.Replacement

class ReplaceAdapter (var onItemClickListener: OnItemClickListener):
    ListAdapter<Replacement, ReplaceAdapter.Vh>(MyDiffUtill()) {
    inner class Vh(var itemReplaceBinding: ItemReplaceBinding): RecyclerView.ViewHolder(itemReplaceBinding.root){
        fun onBind(replacement: Replacement, position:Int){
            itemReplaceBinding.code.text = replacement.code?.trim()
            itemReplaceBinding.info.text = replacement.name?.trim()
            itemReplaceBinding.price.text = replacement.getMinut?.trim()
            itemReplaceBinding.count.text = replacement.setMinut?.trim()
            itemReplaceBinding.clickOp.setOnClickListener {
                onItemClickListener.onItemClick(replacement,position)
            }
        }
    }


    interface OnItemClickListener{
        fun onItemClick(replacement: Replacement, position: Int)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position),position)
    }

    class MyDiffUtill: DiffUtil.ItemCallback<Replacement>(){
        override fun areItemsTheSame(oldItem: Replacement, newItem: Replacement): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(oldItem: Replacement, newItem: Replacement): Boolean {
            return oldItem.equals(newItem)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplaceAdapter.Vh {
        return Vh(ItemReplaceBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
}