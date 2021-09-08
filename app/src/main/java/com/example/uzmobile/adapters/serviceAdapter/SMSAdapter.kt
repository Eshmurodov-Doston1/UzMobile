package com.example.uzmobile.adapters.serviceAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobile.databinding.ItemServiceBinding
import com.example.uzmobile.models.SMSMoney

class SMSAdapter(var onItemClickListener: OnItemClickListener):ListAdapter<SMSMoney,SMSAdapter.Vh>(MyDiffUtill()) {
    inner class Vh(var itemServiceBinding: ItemServiceBinding): RecyclerView.ViewHolder(itemServiceBinding.root){
        fun onBind(smsMoney: SMSMoney,position:Int){
            itemServiceBinding.code.text = smsMoney.code?.trim()
            itemServiceBinding.info.text = smsMoney.name?.trim()
            itemServiceBinding.info2.text = smsMoney.info?.trim()
            itemServiceBinding.clickOp.setOnClickListener {
                onItemClickListener.onItemClick(smsMoney,position)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SMSAdapter.Vh {
        return Vh(ItemServiceBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    interface OnItemClickListener{
        fun onItemClick(smsMoney: SMSMoney, position: Int)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position),position)
    }

    class MyDiffUtill: DiffUtil.ItemCallback<SMSMoney>(){
        override fun areItemsTheSame(oldItem: SMSMoney, newItem: SMSMoney): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(oldItem: SMSMoney, newItem: SMSMoney): Boolean {
            return oldItem.equals(newItem)
        }

    }
}