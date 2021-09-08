package com.example.uzmobile.adapters.serviceAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobile.databinding.ItemInternetBinding
import com.example.uzmobile.databinding.ItemService1Binding
import com.example.uzmobile.databinding.ItemServiceBinding
import com.example.uzmobile.models.SMS
import com.example.uzmobile.models.SMSCountry
import com.example.uzmobile.models.SMSMoney

class RvSMSCountry(var onItemClickListener: OnItemClickListener):ListAdapter<SMSCountry,RvSMSCountry.Vh>(MyDiffUtill()) {
    inner class Vh(var itemServiceBinding: ItemService1Binding): RecyclerView.ViewHolder(itemServiceBinding.root){
        fun onBind(sms: SMSCountry,position:Int){
            itemServiceBinding.code.text = sms.name?.trim()
            itemServiceBinding.info.text = sms.name?.trim()
            itemServiceBinding.info2.text = "${sms.price}\n${sms.count_sms}\n${sms.day_sms}"
            itemServiceBinding.clickOp.setOnClickListener {
                onItemClickListener.onItemClick(sms,position)
            }
        }
    }



    interface OnItemClickListener{
        fun onItemClick(sms:SMSCountry, position: Int)
    }



    class MyDiffUtill: DiffUtil.ItemCallback<SMSCountry>(){
        override fun areItemsTheSame(oldItem: SMSCountry, newItem: SMSCountry): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(oldItem: SMSCountry, newItem: SMSCountry): Boolean {
            return oldItem.equals(newItem)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvSMSCountry.Vh {
        return Vh(ItemService1Binding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position),position)
    }
}