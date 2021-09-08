package com.example.uzmobile.adapters.operatorAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobile.databinding.ItemRvBinding
import com.example.uzmobile.models.Operator
import java.util.*

class Adapteroperator(var onItemClickListener: OnItemClickListener):ListAdapter<Operator,Adapteroperator.Vh>(MyDiffUtill()) {
    inner class Vh(var itemRvBinding: ItemRvBinding):RecyclerView.ViewHolder(itemRvBinding.root){
        fun onBind(operator: Operator,position: Int){
            itemRvBinding.code.text = operator.code
            itemRvBinding.info.text = operator.information

            itemRvBinding.clickOp.setOnClickListener {
                onItemClickListener.onItemClick(operator,position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
     holder.onBind(getItem(position),position)
    }

    class MyDiffUtill:DiffUtil.ItemCallback<Operator>(){
        override fun areItemsTheSame(oldItem: Operator, newItem: Operator): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(oldItem: Operator, newItem: Operator): Boolean {
            return oldItem.equals(newItem)
        }
    }
    interface OnItemClickListener{
        fun onItemClick(operator: Operator,position: Int)
    }
}