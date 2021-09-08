package com.example.uzmobile.adapters.serviceAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.uzmobile.ui.service.ViewPagerServiseFragment

class ServiseAdapter(var list: List<String>,fragmentActivity: FragmentActivity,var name:String):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return ViewPagerServiseFragment.newInstance(list[position],name)
    }
}