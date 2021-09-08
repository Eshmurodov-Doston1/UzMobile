package com.example.uzmobile.ui.home


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.uzmobile.R
import com.example.uzmobile.adapters.CardViewPagerAdapter
import com.example.uzmobile.databinding.FragmentHomeBinding
import com.example.uzmobile.language.LocaleHelper
import com.example.uzmobile.models.CardAnim
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs


class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    var handler = Handler(Looper.getMainLooper())
    lateinit var cardViewPagerAdapter: CardViewPagerAdapter
    lateinit var listViewPager:ArrayList<CardAnim>
    lateinit var binding:FragmentHomeBinding
    lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        root = binding.root
        binding.apply {
            var language = LocaleHelper.getLanguage(requireContext())
            val onAttach = LocaleHelper.onAttach(requireContext())
            ussdText.text = onAttach.getText(R.string.ussd)
            defTv.text = onAttach.getText(R.string.tarif)
            jobtv.text = onAttach.getText(R.string.xizmat)
            minutTv.text = onAttach.getText(R.string.minut)
            smsTv.text = onAttach.getText(R.string.sms)
            text.text = onAttach.getText(R.string.milliy_operator)
            loadList()
            internetTv.text = onAttach.getText(R.string.internet)
            cardViewPagerAdapter = CardViewPagerAdapter(object:CardViewPagerAdapter.OnItemClickListener{
                override fun onItemClick(cardAnim: CardAnim, position: Int) {
                    var bundle = Bundle()
                    bundle.putSerializable("cardAnim",cardAnim)
                    bundle.putInt("position",1)
                    findNavController().navigate(R.id.definitionFragment,bundle)
                }
            },requireContext(),listViewPager)
            viewPager2.adapter = cardViewPagerAdapter
            springDotsIndicator.setViewPager2(viewPager2)
            viewPager2.setPageTransformer { page, position ->
                if (position < -1) {    // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.alpha = 0F
                } else if (position <= 0) {    // [-1,0]
                    page.alpha = 1F
                    page.pivotX = page.width.toFloat()
                    page.rotationY = -90 * abs(position)
                } else if (position <= 1) {    // (0,1]
                    page.alpha = 1F
                    page.pivotX = 0F
                    page.rotationY = 90 * abs(position)
                } else {    // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.alpha = 0F
                }
            }
            viewPager2.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    handler.removeCallbacks(slideRunnable)
                    handler.postDelayed(slideRunnable,3000)
                    if (position== listViewPager.size-1){
                        handler.postDelayed({
                            binding.viewPager2.setCurrentItem(0,false)
                        },2000)
                    }
                }
            })

            ussdFr.setOnClickListener {
                var bundle = Bundle()
                bundle.putString("name","USSD")
                bundle.putString("name1",ussdText.text.toString())
                findNavController().navigate(R.id.ussdFragment,bundle)
            }
            buttonCard1.setOnClickListener {
                var bundle = Bundle()
                bundle.putString("name","Tariflar")
                bundle.putString("name1",defTv.text.toString())
                findNavController().navigate(R.id.ussdFragment,bundle)
            }
            buttonCard2.setOnClickListener {
                var bundle = Bundle()
                bundle.putString("name","Xizmatlar")
                bundle.putString("name1",jobtv.text.toString())
                findNavController().navigate(R.id.serviceFragment,bundle)
            }

            buttonCard3.setOnClickListener {
                var bundle = Bundle()
                bundle.putString("name","Daqiqa")
                bundle.putString("name1",minutTv.text.toString())
                findNavController().navigate(R.id.serviceFragment,bundle)
            }
            buttonCard4.setOnClickListener {
                var bundle = Bundle()
                bundle.putString("name","Internet")
                bundle.putString("name1",internetTv.text.toString())
                findNavController().navigate(R.id.serviceFragment,bundle)
            }
            buttonCard5.setOnClickListener {
                var bundle = Bundle()
                bundle.putString("name","SMS")
                bundle.putString("name1",smsTv.text.toString())
                findNavController().navigate(R.id.serviceFragment,bundle)
            }


        }
        var calback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, calback)
        return root
    }

    private val slideRunnable:Runnable = Runnable {
        binding.viewPager2.setCurrentItem(binding.viewPager2.currentItem+1)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(slideRunnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(slideRunnable,3000)
    }

    private fun loadList() {
        listViewPager = ArrayList()
        var language = LocaleHelper.getLanguage(requireContext())
        when(language.lowercase(Locale.getDefault())){
            "uz".lowercase(Locale.getDefault())->{
                listViewPager.add(CardAnim("Oddiy 10","10","10","10","10000","*111*1*11*12#"))
                listViewPager.add(CardAnim("Street","750","6500","750","39900","*111*1*11*1*1#"))
                listViewPager.add(CardAnim("Onlime","1000","10000","1000","49900","*111*1*11*6#"))
                listViewPager.add(CardAnim("Flash","1500","16000","1500","69900","*111*1*11*2*1#"))
                listViewPager.add(CardAnim("Ishbilarmon","Cheksiz","25000","3000","99000","*111*1*11*10#"))
            }
            "ru".lowercase(Locale.getDefault())->{
                listViewPager.add(CardAnim("Простой 10","10","10","10","10000","*111*1*11*12#"))
                listViewPager.add(CardAnim("улица","750","6500","750","39900","*111*1*11*1*1#"))
                listViewPager.add(CardAnim("Онлайм","1000","10000","1000","49900","*111*1*11*6#"))
                listViewPager.add(CardAnim("Вспышка","1500","16000","1500","69900","*111*1*11*2*1#"))
                listViewPager.add(CardAnim("Бизнесмен","Безлимитный","25000","3000","99000","*111*1*11*10#"))
            }
            "en".lowercase(Locale.getDefault())->{
                listViewPager.add(CardAnim("Оддий 10","10","10","10","10000","*111*1*11*12#"))
                listViewPager.add(CardAnim("Стреет","750","6500","750","39900","*111*1*11*1*1#"))
                listViewPager.add(CardAnim("Онлиме","1000","10000","1000","49900","*111*1*11*6#"))
                listViewPager.add(CardAnim("Флаш","1500","16000","1500","69900","*111*1*11*2*1#"))
                listViewPager.add(CardAnim("Ишбилармон","Чексиз","25000","3000","99000","*111*1*11*10#"))
            }
        }

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.my_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.telegramMy->{
                val uri =
                    Uri.parse("https://t.me/ussdchat")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                try {
                    startActivity(intent)
                } catch (e: Exception) {
                    Snackbar.make(binding.root, "Qayta urining !", Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.share1->{
                var shareIntent = Intent().apply {
                    this.action = Intent.ACTION_SEND
                    this.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=uz.pdp.uzmobile")
                    this.type="text/plain"
                }
                startActivity(shareIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}