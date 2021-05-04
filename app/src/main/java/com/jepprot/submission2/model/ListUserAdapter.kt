package com.jepprot.submission2.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jepprot.submission2.repository.DataUser
import com.jepprot.submission2.databinding.ItemRowUserBinding

class ListUserAdapter: RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private val listUser = ArrayList<DataUser>()
    private var chooseUserClickCallback: OnItemClickCallback? = null


    inner class ListViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun wrap(dataUser: DataUser) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(dataUser.avatar_url)
                    .centerCrop()
                    .into(imgAvatar)
                tvUsername.text = dataUser.login

                binding.root.setOnClickListener {
                    chooseUserClickCallback?.onItemCallback(dataUser)

                    itemView.setOnClickListener { chooseUserClickCallback?.onItemCallback(dataUser) }

                }
            }
        }
    }

    fun rvList(user: ArrayList<DataUser>) {
        listUser.clear()
        listUser.addAll(user)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.wrap(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.chooseUserClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemCallback(data: DataUser)
    }
}




