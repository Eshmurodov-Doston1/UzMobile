package com.example.uzmobile.adapters.ussdAdapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobile.R
import com.example.uzmobile.databinding.ItemUssdBinding
import com.example.uzmobile.language.LocaleHelper
import com.example.uzmobile.models.Operator
import com.example.uzmobile.models.USSD
import com.github.florent37.expansionpanel.ExpansionLayout
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection

class UssdAdapter(var context: Context,var onItemClickListener: OnItemClickListener):ListAdapter<USSD,UssdAdapter.Vh>(MyDiffUtill()) {
    var isExpanded = false
    val expansionLayoutCollection = ExpansionLayoutCollection()
    inner class Vh(var itemUssdBinding: ItemUssdBinding):RecyclerView.ViewHolder(itemUssdBinding.root){
        fun onBind(ussd: USSD, position: Int){
            itemUssdBinding.code.text = ussd.code
            itemUssdBinding.name.text = ussd.name
            itemUssdBinding.connect.text = LocaleHelper.onAttach(context).getText(R.string.ulanish)
            if (ussd.code=="" && ussd.name==""){
                itemUssdBinding.header.visibility = View.GONE
                itemUssdBinding.headerIndicator.visibility = View.GONE
                itemUssdBinding.consMy.visibility = View.VISIBLE
            }
             itemUssdBinding.expansionLayout.addListener { expansionLayout, expanded ->
                 expansionLayoutCollection.add(expansionLayout)
             }
            itemUssdBinding.connect.setOnClickListener {
                onItemClickListener.onItemClick(ussd,position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemUssdBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position),position)
    }

    class MyDiffUtill:DiffUtil.ItemCallback<USSD>(){
        override fun areItemsTheSame(oldItem: USSD, newItem: USSD): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(oldItem: USSD, newItem: USSD): Boolean {
            return oldItem.equals(newItem)
        }
    }
    interface OnItemClickListener{
        fun onItemClick(ussd: USSD,position: Int)
        //fun onItemExpansionLayoutClick(ussd: USSD,position: Int,expansionLayout: ExpansionLayout)
    }
}