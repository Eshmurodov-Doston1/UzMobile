package com.example.uzmobile.adapters.serviceAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobile.databinding.ItemMinutBinding
import com.example.uzmobile.databinding.ItemServiceBinding
import com.example.uzmobile.models.Minut

class MinutAdapter (var onItemClickListener: OnItemClickListener):
    ListAdapter<Minut, MinutAdapter.Vh>(MyDiffUtill()) {
    inner class Vh(var itemMinutBinding: ItemMinutBinding): RecyclerView.ViewHolder(itemMinutBinding.root){
        fun onBind(minut: Minut, position:Int){
            itemMinutBinding.code.text = minut.name?.trim()
            itemMinutBinding.info.text = minut.name?.trim()
            itemMinutBinding.price.text = minut.summ?.trim()
            itemMinutBinding.count.text = minut.count_min?.trim()
            itemMinutBinding.day.text = minut.day_month?.trim()
            itemMinutBinding.clickOp.setOnClickListener {
                onItemClickListener.onItemClick(minut,position)
            }
        }
    }


    interface OnItemClickListener{
        fun onItemClick(smsMoney: Minut, position: Int)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position),position)
    }

    class MyDiffUtill: DiffUtil.ItemCallback<Minut>(){
        override fun areItemsTheSame(oldItem: Minut, newItem: Minut): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(oldItem: Minut, newItem: Minut): Boolean {
            return oldItem.equals(newItem)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MinutAdapter.Vh {
        return Vh(ItemMinutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
}