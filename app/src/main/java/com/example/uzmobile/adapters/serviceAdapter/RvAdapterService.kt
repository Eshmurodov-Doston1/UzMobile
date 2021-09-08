package com.example.uzmobile.adapters.serviceAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobile.databinding.ItemServiceBinding
import com.example.uzmobile.models.Service

class RvAdapterService(var onItemClickListener: OnItemClickListener):ListAdapter<Service,RvAdapterService.Vh>(MyDiffUtill()) {
    inner class Vh(var itemServiceBinding: ItemServiceBinding):RecyclerView.ViewHolder(itemServiceBinding.root){
      fun onBind(service:Service,position:Int){
          itemServiceBinding.code.text = service.name?.trim()
          itemServiceBinding.info.text = service.name?.trim()
          itemServiceBinding.info2.text = service.info1?.trim()
          itemServiceBinding.clickOp.setOnClickListener {
              onItemClickListener.onItemClick(service,position)
          }
      }
    }

    class MyDiffUtill:DiffUtil.ItemCallback<Service>(){
        override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem.equals(newItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemServiceBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position),position)
    }
    interface OnItemClickListener{
        fun onItemClick(servicew: Service,position: Int)
    }
}