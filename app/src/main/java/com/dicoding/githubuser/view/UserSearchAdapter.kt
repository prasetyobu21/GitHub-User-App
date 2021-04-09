package com.dicoding.githubuser.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubuser.databinding.ItemCardviewUserSearchBinding
import com.dicoding.githubuser.model.UserSearch
import de.hdodenhof.circleimageview.CircleImageView

class UserSearchAdapter : RecyclerView.Adapter<UserSearchAdapter.UserSearchViewHolder>() {

    private val list = ArrayList<UserSearch>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: UserSearch)
    }

    fun setList(users: ArrayList<UserSearch>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserSearchViewHolder(val binding: ItemCardviewUserSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserSearch) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .centerCrop()
                    .into(imgUserPhoto)
                tvUsername.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserSearchViewHolder {
        val view = ItemCardviewUserSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserSearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserSearchViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}