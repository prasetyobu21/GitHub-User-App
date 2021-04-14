package com.dicoding.githubuser.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.databinding.ItemCardviewUserSearchBinding
import com.dicoding.githubuser.entity.FavoriteUser

class FavUserAdapter() :
    RecyclerView.Adapter<FavUserAdapter.FavUserViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: FavoriteUser)
    }

    var listFavUser = ArrayList<FavoriteUser>()
        set(listFavUser) {
            if (listFavUser.size > 0) {
                this.listFavUser.clear()
            }
            this.listFavUser.addAll(listFavUser)

            notifyDataSetChanged()
        }

    fun setList(favUser: ArrayList<FavoriteUser>){
        listFavUser.clear()
        listFavUser.addAll(favUser)
        notifyDataSetChanged()
    }

    fun addItem(fav: FavoriteUser) {
        this.listFavUser.add(fav)
        notifyItemInserted(this.listFavUser.size - 1)
    }

    fun removeItem(position: Int) {
        this.listFavUser.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listFavUser.size)
    }

    inner class FavUserViewHolder(private val binding: ItemCardviewUserSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favUser: FavoriteUser) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(favUser)
            }
            binding.apply {

                Glide.with(itemView)
                    .load(favUser.avatar_url)
                    .centerCrop()
                    .into(imgUserPhoto)

                tvUsername.text = favUser.login
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavUserViewHolder {
        val view = ItemCardviewUserSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavUserViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavUserViewHolder, position: Int) {
        holder.bind(listFavUser[position])
    }

    override fun getItemCount(): Int = listFavUser.size
}