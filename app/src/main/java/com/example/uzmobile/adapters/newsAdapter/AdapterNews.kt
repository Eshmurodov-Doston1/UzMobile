package com.example.uzmobile.adapters.newsAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uzmobile.R
import com.example.uzmobile.databinding.ItemNewsBinding
import com.example.uzmobile.models.News
import com.squareup.picasso.Picasso

class AdapterNews(var onItemClickListener: OnItemClickListener):ListAdapter<News,AdapterNews.Vh>(MyDiffUtill()) {
    inner class Vh(var itemNewsBinding: ItemNewsBinding):RecyclerView.ViewHolder(itemNewsBinding.root){
        fun onBind(news: News,position: Int){
            itemNewsBinding.date.text = news.date
            Picasso.get().load(news.image1).into(itemNewsBinding.image)
            itemNewsBinding.name.text= news.name
            itemNewsBinding.newsItem.setOnClickListener {
                onItemClickListener.onItemClickListener(news,position)
            }
        }
    }

    class MyDiffUtill:DiffUtil.ItemCallback<News>(){
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.equals(newItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position),position)
    }
    interface OnItemClickListener{
        fun onItemClickListener(news: News,position: Int)
    }
}