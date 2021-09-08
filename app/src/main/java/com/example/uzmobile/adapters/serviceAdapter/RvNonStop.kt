package com.example.uzmobile.adapters.serviceAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobile.databinding.ItemInternet1Binding
import com.example.uzmobile.databinding.ItemInternetBinding
import com.example.uzmobile.databinding.ItemServiceBinding
import com.example.uzmobile.models.Internet
import com.example.uzmobile.models.NonStop
import com.example.uzmobile.models.SMSMoney

class RvNonStop(var onItemClickListener: OnItemClickListener):
    ListAdapter<NonStop, RvNonStop.Vh>(MyDiffUtill()) {
    inner class Vh(var itemInternetBinding: ItemInternet1Binding): RecyclerView.ViewHolder(itemInternetBinding.root){
        fun onBind(nonStop: NonStop, position:Int){
            itemInternetBinding.mb.text = nonStop.mb?.trim()
            itemInternetBinding.mbText.text = nonStop.gb?.trim()
            itemInternetBinding.summText.text = nonStop.name_price?.trim()
            itemInternetBinding.summ.text = nonStop.price?.trim()
            itemInternetBinding.mbGet.text = nonStop.mb_text?.trim()
            itemInternetBinding.mbCount.text = nonStop.mb_count?.trim()
            itemInternetBinding.day.text  =nonStop.day_text?.trim()
            itemInternetBinding.dayCount.text  =nonStop.day_count?.trim()
            itemInternetBinding.clickOp.setOnClickListener {
                onItemClickListener.onItemClick(nonStop,position)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvNonStop.Vh {
        return Vh(ItemInternet1Binding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    interface OnItemClickListener{
        fun onItemClick(smsMoney: NonStop, position: Int)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position),position)
    }

    class MyDiffUtill: DiffUtil.ItemCallback<NonStop>(){
        override fun areItemsTheSame(oldItem: NonStop, newItem: NonStop): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(oldItem: NonStop, newItem: NonStop): Boolean {
            return oldItem.equals(newItem)
        }

    }
}