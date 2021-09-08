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
import com.example.uzmobile.models.SMSMoney

class RvSMS(var onItemClickListener: OnItemClickListener):ListAdapter<SMS,RvSMS.Vh>(MyDiffUtill()) {
    inner class Vh(var itemServiceBinding: ItemService1Binding): RecyclerView.ViewHolder(itemServiceBinding.root){
        fun onBind(sms: SMS,position:Int){
            itemServiceBinding.code.text = sms.name?.trim()
            itemServiceBinding.info.text = sms.name?.trim()
            itemServiceBinding.info2.text = "${sms.price}\n${sms.summ_user}\n${sms.count_sms}"
            itemServiceBinding.clickOp.setOnClickListener {
                onItemClickListener.onItemClick(sms,position)
            }
        }
    }



    interface OnItemClickListener{
        fun onItemClick(sms:SMS, position: Int)
    }



    class MyDiffUtill: DiffUtil.ItemCallback<SMS>(){
        override fun areItemsTheSame(oldItem: SMS, newItem: SMS): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(oldItem: SMS, newItem: SMS): Boolean {
            return oldItem.equals(newItem)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvSMS.Vh {
        return Vh(ItemService1Binding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position),position)
    }
}