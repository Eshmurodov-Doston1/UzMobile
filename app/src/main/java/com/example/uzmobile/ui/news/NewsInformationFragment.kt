package com.example.uzmobile.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainer
import com.example.uzmobile.R
import com.example.uzmobile.databinding.FragmentNewsInformationBinding
import com.example.uzmobile.language.LocaleHelper
import com.example.uzmobile.models.News
import com.squareup.picasso.Picasso
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class NewsInformationFragment : Fragment(R.layout.fragment_news_information) {

    private val binding by viewBinding(FragmentNewsInformationBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val news = arguments?.getSerializable("news") as News
            name.text = news.name
            (activity as AppCompatActivity).supportActionBar?.title = news.name
            if (news.info2!=""){
                Picasso.get().load(news.image2).into(imageNews2)
                info2.text = news.info2
                card2.visibility = View.VISIBLE
                info2.visibility = View.VISIBLE
            }else{
                card2.visibility = View.GONE
                info2.visibility = View.GONE
            }
            Picasso.get().load(news.image1).into(imageNews1)
            info1.text = news.info1
        }
    }
}