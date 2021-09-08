package com.example.uzmobile.adapters.serviceAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobile.databinding.ItemInternetBinding
import com.example.uzmobile.databinding.ItemServiceBinding
import com.example.uzmobile.models.Internet
import com.example.uzmobile.models.SMSMoney

class RvInternet(var onItemClickListener: OnItemClickListener):
    ListAdapter<Internet, RvInternet.Vh>(MyDiffUtill()) {
    inner class Vh(var itemInternetBinding: ItemInternetBinding): RecyclerView.ViewHolder(itemInternetBinding.root){
        fun onBind(internet: Internet, position:Int){
            itemInternetBinding.mb.text = internet.mb?.trim()
            itemInternetBinding.mbText.text = internet.gb?.trim()
            itemInternetBinding.summText.text = internet.name_price?.trim()
            itemInternetBinding.summ.text = internet.price?.trim()
            itemInternetBinding.mbGet.text = internet.mb_text?.trim()
            itemInternetBinding.mbCount.text = internet.mb_count?.trim()
            itemInternetBinding.day.text  =internet.day_text?.trim()
            itemInternetBinding.dayCount.text  =internet.day_count?.trim()
//            itemServiceBinding.code.text = smsMoney.code
//            itemServiceBinding.info.text = smsMoney.name
//            itemServiceBinding.info2.text = smsMoney.info
            itemInternetBinding.clickOp.setOnClickListener {
                onItemClickListener.onItemClick(internet,position)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvInternet.Vh {
        return Vh(ItemInternetBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    interface OnItemClickListener{
        fun onItemClick(smsMoney: Internet, position: Int)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position),position)
    }

    class MyDiffUtill: DiffUtil.ItemCallback<Internet>(){
        override fun areItemsTheSame(oldItem: Internet, newItem: Internet): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(oldItem: Internet, newItem: Internet): Boolean {
            return oldItem.equals(newItem)
        }

    }
}