package com.example.uzmobile.ui.service

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainer
import com.example.uzmobile.R
import com.example.uzmobile.adapters.serviceAdapter.ServiseAdapter
import com.example.uzmobile.databinding.FragmentServiceBinding
import com.example.uzmobile.databinding.TabItem1Binding
import com.example.uzmobile.databinding.TabItem2Binding
import com.example.uzmobile.databinding.TabItemBinding
import com.example.uzmobile.language.LocaleHelper
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.util.*
import kotlin.collections.ArrayList

class ServiceFragment : Fragment(R.layout.fragment_service) {
    private val binding by viewBinding(FragmentServiceBinding::bind)
    lateinit var serviseAdapter:ServiseAdapter
    lateinit var listCategory:ArrayList<String>
    lateinit var listMinut:ArrayList<String>
    lateinit var listInternet:ArrayList<String>
    lateinit var listSMS:ArrayList<String>
    lateinit var name:String
    lateinit var name1:String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name = arguments?.getString("name").toString()
        name1 = arguments?.getString("name1").toString()
        (activity as AppCompatActivity).supportActionBar?.title = name1
        loadCategory()
        loadMinut()
        loadInternet()
        loadSMS()
        when(name.lowercase(Locale.getDefault())){
            "Xizmatlar".lowercase(Locale.getDefault())->{
                binding.apply {
                    serviseAdapter = ServiseAdapter(listCategory,requireActivity(),"Xizmatlar")
                    viewPager.adapter = serviseAdapter
                    TabLayoutMediator(tabLayout,viewPager){tab,position->
                        tab.text = listCategory[position]
                    }.attach()
                    tabAttach()
                    tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
                        override fun onTabReselected(tab: TabLayout.Tab?) {

                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {
                            val customView = tab?.customView
                            var tabItemBinding = customView?.let { TabItemBinding.bind(it) }
                            tabItemBinding?.cons?.setBackgroundResource(R.drawable.tab_item1)
                            tabItemBinding?.text?.setTextColor(Color.WHITE)
                        }

                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            val customView = tab?.customView
                            var tabItemBinding = customView?.let { TabItemBinding.bind(it) }
                            tabItemBinding?.cons?.setBackgroundResource(R.drawable.tab_item)
                            tabItemBinding?.text?.setTextColor(Color.parseColor("#01B4FF"))
                        }

                    })
                }
            }

            "Daqiqa".lowercase(Locale.getDefault())->{
                binding.apply {
                    serviseAdapter = ServiseAdapter(listMinut,requireActivity(),"Daqiqa")
                    viewPager.adapter = serviseAdapter
                    TabLayoutMediator(tabLayout,viewPager){tab,position->
                        tab.text = listMinut[position]
                    }.attach()
                    tabAttach()
                    tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
                        override fun onTabReselected(tab: TabLayout.Tab?) {

                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {
                            val customView = tab?.customView
                            var tabItemBinding = customView?.let { TabItemBinding.bind(it) }
                            tabItemBinding?.cons?.setBackgroundResource(R.drawable.tab_item1)
                            tabItemBinding?.text?.setTextColor(Color.WHITE)
                        }

                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            val customView = tab?.customView
                            var tabItemBinding = customView?.let { TabItemBinding.bind(it) }
                            tabItemBinding?.cons?.setBackgroundResource(R.drawable.tab_item)
                            tabItemBinding?.text?.setTextColor(Color.parseColor("#01B4FF"))
                        }

                    })
                }
            }
            "Internet".lowercase(Locale.getDefault())->{
                binding.apply {
                    serviseAdapter = ServiseAdapter(listInternet,requireActivity(),"Internet")
                    viewPager.adapter = serviseAdapter
                    TabLayoutMediator(tabLayout,viewPager){ tab,position->
                        tab.text = listInternet[position]
                    }.attach()
                    tabAttach()
                    tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
                        override fun onTabReselected(tab: TabLayout.Tab?) {

                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {
                            val customView = tab?.customView
                            var tabItemBinding = customView?.let { TabItemBinding.bind(it) }
                            tabItemBinding?.cons?.setBackgroundResource(R.drawable.tab_item1)
                            tabItemBinding?.text?.setTextColor(Color.WHITE)
                        }

                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            val customView = tab?.customView
                            var tabItemBinding = customView?.let { TabItemBinding.bind(it) }
                            tabItemBinding?.cons?.setBackgroundResource(R.drawable.tab_item)
                            tabItemBinding?.text?.setTextColor(Color.parseColor("#01B4FF"))
                        }

                    })
                }
            }
            "SMS".lowercase(Locale.getDefault())->{
                binding.apply {
                    serviseAdapter = ServiseAdapter(listSMS,requireActivity(),"SMS")
                    viewPager.adapter = serviseAdapter
                    TabLayoutMediator(tabLayout,viewPager){ tab,position->
                        tab.text = listSMS[position]
                    }.attach()
                    tabAttach()
                    tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
                        override fun onTabReselected(tab: TabLayout.Tab?) {

                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {
                            val customView = tab?.customView
                            var tabItemBinding = customView?.let { TabItemBinding.bind(it) }
                            tabItemBinding?.cons?.setBackgroundResource(R.drawable.tab_item1)
                            tabItemBinding?.text?.setTextColor(Color.WHITE)
                        }

                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            val customView = tab?.customView
                            var tabItemBinding = customView?.let { TabItemBinding.bind(it) }
                            tabItemBinding?.cons?.setBackgroundResource(R.drawable.tab_item)
                            tabItemBinding?.text?.setTextColor(Color.parseColor("#01B4FF"))
                        }

                    })
                }
            }
        }

    }

    private fun loadSMS() {
        listSMS = ArrayList()
        val language = LocaleHelper.getLanguage(requireContext())
        when(language.lowercase(Locale.getDefault())){
            "uz".lowercase(Locale.getDefault())->{
                listSMS.add("Kunlik SMS paketlar")
                listSMS.add("Oylik SMS paketlar")
                listSMS.add("Xalqaro SMS paketlar")
                listSMS.add("<<Constructor>> TR abonentlari uchun SMS paketlar")
            }
            "ru".lowercase(Locale.getDefault())->{
                listSMS.add("???????????????????? SMS-????????????")
                listSMS.add("?????????????????????? SMS-????????????")
                listSMS.add("?????????????????????????? SMS-????????????")
                listSMS.add("<<??????????????????????>> SMS-???????????? ?????? ?????????????????? TR")
            }
            "en".lowercase(Locale.getDefault())->{
                listSMS.add("???????????? ?????? ????????????????")
                listSMS.add("?????????? ?????? ????????????????")
                listSMS.add("?????????????? ?????? ????????????????")
                listSMS.add("<<C????????????c??????>> ???? ?????????????????????? ???????? ?????? ????????????????")
            }
        }
    }

    private fun loadInternet() {
        listInternet = ArrayList()
        val language = LocaleHelper.getLanguage(requireContext())
        when(language.lowercase(Locale.getDefault())){
            "uz".lowercase(Locale.getDefault())->{
                listInternet.add("Oylik paketlar")
                listInternet.add("Kunlik paketlar")
                listInternet.add("Tungi internet")
                listInternet.add("TAS-IX uchun internet paketlari")
                listInternet.add("Internet non-stop")
                listInternet.add("TA'LIM tarif rejasi uchun maxsus\ninternet-paketlar")
                listInternet.add("<<Constructor>> TR abonentlari\nuchun internet paketlari")
            }
            "ru".lowercase(Locale.getDefault())->{
                listInternet.add("?????????????????????? ????????????")
                listInternet.add("???????????????????? ????????????")
                listInternet.add("???????????? ????????????????")
                listInternet.add("????????????????-???????????? ?????? TAS-IX")
                listInternet.add("???????????????? ?????? ??????????????????")
                listInternet.add("???????????????????? ?????? ?????????????????? ?????????? ??????????????????????\n????????????????-????????????")
                listInternet.add("<<Constructor>> ???????????????????? TR\n????????????????-???????????? ??????")
            }
            "en".lowercase(Locale.getDefault())->{
                listInternet.add("?????????? ????????????????")
                listInternet.add("???????????? ????????????????")
                listInternet.add("?????????? ????????????????")
                listInternet.add("??A??-???? ???????? ???????????????? ??????????????????")
                listInternet.add("???????????????? ??????-????????")
                listInternet.add("??A???????? ?????????? ???????????? ???????? ????????????\n????????????????-????????????????")
                listInternet.add("<<C????????????c??????>> ???? ??????????????????????\n???????? ???????????????? ??????????????????")
            }
        }
    }

    private fun loadMinut() {
        listMinut = ArrayList()
        val language = LocaleHelper.getLanguage(requireContext())
        when(language.lowercase(Locale.getDefault())){
            "uz".lowercase(Locale.getDefault())->{
                listMinut.add("Daqiqa\nto'plamlari")
                listMinut.add("Foydali\nalmashtiruv")
                listMinut.add("Constructor\nabonentlari")
            }
            "ru".lowercase(Locale.getDefault())->{
                listMinut.add("????????????????\n????????????")
                listMinut.add("????????????????\n????????????")
                listMinut.add("????????????????\n????????????????????????")
            }
            "en".lowercase(Locale.getDefault())->{
                listMinut.add("????????????\n????????????????????")
                listMinut.add("??????????????\n????????????????????")
                listMinut.add("C????????????c??????\n??????????????????????")
            }
        }
    }

    private fun tabAttach() {
        val tabCount = binding.tabLayout.tabCount
        for (i in 0 until tabCount){

            when(name.lowercase(Locale.getDefault())){
                "Xizmatlar".lowercase(Locale.getDefault())->{
                    var tabItemBinding = TabItemBinding.inflate(LayoutInflater.from(requireContext()),null,false)
                    val tabAt = binding.tabLayout.getTabAt(i)
                    tabAt!!.customView = tabItemBinding.root
                    tabItemBinding.text.text = listCategory[i]
                    if (i==0){
                        tabItemBinding.cons.setBackgroundResource(R.drawable.tab_item)
                        tabItemBinding.text.setTextColor(Color.parseColor("#01B4FF"))
                    }else{
                        tabItemBinding.cons.setBackgroundResource(R.drawable.tab_item1)
                        tabItemBinding.text.setTextColor(Color.WHITE)
                    }
                }
                "Daqiqa".lowercase(Locale.getDefault())->{
                    var tabItemBinding = TabItem1Binding.inflate(LayoutInflater.from(requireContext()),null,false)
                    val tabAt = binding.tabLayout.getTabAt(i)
                    tabAt!!.customView = tabItemBinding.root
                    tabItemBinding.text.text = listMinut[i]
                    if (i==0){
                        tabItemBinding.cons.setBackgroundResource(R.drawable.tab_item)
                        tabItemBinding.text.setTextColor(Color.parseColor("#01B4FF"))
                    }else{
                        tabItemBinding.cons.setBackgroundResource(R.drawable.tab_item1)
                        tabItemBinding.text.setTextColor(Color.WHITE)
                    }
                }
                "Internet".lowercase(Locale.getDefault())->{
                    var tabItemBinding = TabItem2Binding.inflate(LayoutInflater.from(requireContext()),null,false)
                    val tabAt = binding.tabLayout.getTabAt(i)
                    tabAt!!.customView = tabItemBinding.root
                    tabItemBinding.text.text = listInternet[i]
                    if (i==0){
                        tabItemBinding.cons.setBackgroundResource(R.drawable.tab_item)
                        tabItemBinding.text.setTextColor(Color.parseColor("#01B4FF"))
                    }else{
                        tabItemBinding.cons.setBackgroundResource(R.drawable.tab_item1)
                        tabItemBinding.text.setTextColor(Color.WHITE)
                    }
                }
                "SMS".lowercase(Locale.getDefault())->{
                    var tabItemBinding = TabItem2Binding.inflate(LayoutInflater.from(requireContext()),null,false)
                    val tabAt = binding.tabLayout.getTabAt(i)
                    tabAt!!.customView = tabItemBinding.root
                    tabItemBinding.text.text = listSMS[i]
                    if (i==0){
                        tabItemBinding.cons.setBackgroundResource(R.drawable.tab_item)
                        tabItemBinding.text.setTextColor(Color.parseColor("#01B4FF"))
                    }else{
                        tabItemBinding.cons.setBackgroundResource(R.drawable.tab_item1)
                        tabItemBinding.text.setTextColor(Color.WHITE)
                    }
                }
            }

        }
    }

    private fun loadCategory() {
        listCategory = ArrayList()
        val language = LocaleHelper.getLanguage(requireContext())
        when(language.lowercase(Locale.getDefault())){
            "uz".lowercase(Locale.getDefault())->{
                listCategory.add("Xizmatlar")
                listCategory.add("Pullik SMSlar")
                listCategory.add("Rouming")
            }
            "ru".lowercase(Locale.getDefault())->{
                listCategory.add("????????????")
                listCategory.add("?????????????? ??????")
                listCategory.add("??????????????")
            }
            "en".lowercase(Locale.getDefault())->{
                listCategory.add("??????????????????")
                listCategory.add("???????????? ????????????")
                listCategory.add("??????????????")
            }
        }
    }
}