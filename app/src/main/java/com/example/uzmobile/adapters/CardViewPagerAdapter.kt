package com.example.uzmobile.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.uzmobile.R
import com.example.uzmobile.databinding.ItemViewpagerBinding
import com.example.uzmobile.language.LocaleHelper
import com.example.uzmobile.models.CardAnim
import java.util.*

class CardViewPagerAdapter(var onItemClickListener:OnItemClickListener,var context:Context,var list: List<CardAnim>):RecyclerView.Adapter<CardViewPagerAdapter.Vh>(){
   inner class Vh(var itemViewpagerBinding: ItemViewpagerBinding):RecyclerView.ViewHolder(itemViewpagerBinding.root){
       fun onBind(cardAnim: CardAnim,position: Int){
           val onAttach = LocaleHelper.onAttach(context)
           if (cardAnim.countTime?.lowercase(Locale.getDefault())=="Cheksiz".lowercase(Locale.getDefault())){
               itemViewpagerBinding.textMin.text = "${cardAnim.countTime}"
           }else {
               itemViewpagerBinding.textMin.text = "${cardAnim.countTime} ${onAttach.getText(R.string.min)}"
           }
           itemViewpagerBinding.summ.text = "${cardAnim.summ} ${onAttach.getText(R.string.month_summ)}"
           itemViewpagerBinding.textMb.text = "${cardAnim.MB} MB"
           itemViewpagerBinding.tv1.text = cardAnim.nameMenu
           itemViewpagerBinding.textSms.text = "${cardAnim.smsCount} ${onAttach.getText(R.string.sms_my)}"
           itemView.setOnClickListener {
               onItemClickListener.onItemClick(cardAnim,position)
           }
       }
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemViewpagerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    interface OnItemClickListener{
        fun onItemClick(cardAnim: CardAnim,position: Int)
    }
}