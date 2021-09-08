package com.example.uzmobile.adapters.definitionsAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobile.databinding.ItemDefinitionBinding
import com.example.uzmobile.models.Definitions

class DefinitionsAdapter(var onItemClickListener: OnItemClickListener):ListAdapter<Definitions,DefinitionsAdapter.Vh>(MyDiffUtill()) {
    inner class Vh(var itemDefinitionBinding: ItemDefinitionBinding):RecyclerView.ViewHolder(itemDefinitionBinding.root){
        fun onBind(definitions: Definitions,position: Int){
            itemDefinitionBinding.infoMin.text = definitions.info_min
            itemDefinitionBinding.name.text = definitions.name
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(definitions,position)
            }
        }
    }

    class MyDiffUtill:DiffUtil.ItemCallback<Definitions>(){
        override fun areItemsTheSame(oldItem: Definitions, newItem: Definitions): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(oldItem: Definitions, newItem: Definitions): Boolean {
            return oldItem.equals(newItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemDefinitionBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position),position)
    }
    interface OnItemClickListener{
        fun onItemClick(definitions: Definitions,position: Int)
    }
}