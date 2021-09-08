package com.example.uzmobile.ui.news

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.uzmobile.R
import com.example.uzmobile.adapters.newsAdapter.AdapterNews
import com.example.uzmobile.databinding.FragmentNewsBinding
import com.example.uzmobile.language.LocaleHelper
import com.example.uzmobile.models.News
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.util.*
import kotlin.collections.ArrayList

class NewsFragment : Fragment(R.layout.fragment_news) {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var firebaseFirestore: FirebaseFirestore
    private val binding by viewBinding(FragmentNewsBinding::bind)
    lateinit var listNews:ArrayList<News>
    lateinit var adapterNews: AdapterNews
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listNews = ArrayList()
        binding.apply {
            firebaseFirestore = FirebaseFirestore.getInstance()
            val onAttach = LocaleHelper.onAttach(requireContext())
            (activity as AppCompatActivity).supportActionBar?.title = onAttach.getText(R.string.news)
            sharedPreferences = requireActivity().getSharedPreferences("Language",0)
            val language = sharedPreferences.getString("lan", "")
            adapterNews = AdapterNews(object:AdapterNews.OnItemClickListener{
                override fun onItemClickListener(news: News, position: Int) {
                    var bundle = Bundle()
                    bundle.putSerializable("news",news)
                    findNavController().navigate(R.id.newsInformationFragment,bundle)
                }
            })
            when(language?.lowercase(Locale.getDefault())){
                "uz".lowercase(Locale.getDefault())->{
                    firebaseFirestore.collection("News").addSnapshotListener { value, error ->
                        value?.documentChanges?.forEach { documentChange->
                            when(documentChange.type){
                                DocumentChange.Type.ADDED->{
                                    val news = documentChange.document.toObject(News::class.java)
                                    listNews.add(news)
                                }
                            }
                        }
                        adapterNews.submitList(listNews)
                        rvNews.adapter = adapterNews
                    }
                }
                "ru".lowercase(Locale.getDefault())->{
                    firebaseFirestore.collection("NewsRu").addSnapshotListener { value, error ->
                        value?.documentChanges?.forEach { documentChange->
                            when(documentChange.type){
                                DocumentChange.Type.ADDED->{
                                    val news = documentChange.document.toObject(News::class.java)
                                    listNews.add(news)
                                }
                            }
                        }
                        adapterNews.submitList(listNews)
                        rvNews.adapter = adapterNews
                    }
                }
                "kril".lowercase(Locale.getDefault())->{
                    firebaseFirestore.collection("NewsCril").addSnapshotListener { value, error ->
                        value?.documentChanges?.forEach { documentChange->
                            when(documentChange.type){
                                DocumentChange.Type.ADDED->{
                                    val news = documentChange.document.toObject(News::class.java)
                                    listNews.add(news)
                                }
                            }
                        }
                        adapterNews.submitList(listNews)
                        rvNews.adapter = adapterNews
                    }
                }
            }
        }
        var calback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
                findNavController().navigate(R.id.nav_home)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, calback)
    }
}