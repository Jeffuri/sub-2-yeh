package com.jepprot.submission2.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jepprot.submission2.databinding.ItemCardoBinding
import com.jepprot.submission2.repository.DataUser


class CardoUserAdapter: RecyclerView.Adapter<CardoUserAdapter.CardViewHolder>() {

    private val cardUser = ArrayList<DataUser>()
    private var chooseClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        position: Int
    ): CardoUserAdapter.CardViewHolder {
        val binding =
            ItemCardoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardoUserAdapter.CardViewHolder, position: Int) {
        holder.wrap(cardUser[position])
    }

    override fun getItemCount(): Int = cardUser.size

    fun rvListCard(user: ArrayList<DataUser>) {
        cardUser.clear()
        cardUser.addAll(user)
        notifyDataSetChanged()
    }

    inner class CardViewHolder(private val binding: ItemCardoBinding): RecyclerView.ViewHolder(binding.root) {
        fun wrap(dataUser: DataUser) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(dataUser.avatar_url)
                    .centerCrop()
                    .into(imgAvatar)
                tvItemName.text = dataUser.login

                binding.root.setOnClickListener {
                    chooseClickCallback?.onItemCallback(dataUser)

                    itemView.setOnClickListener { chooseClickCallback?.onItemCallback(dataUser) }

                }
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.chooseClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemCallback(data: DataUser)
    }
}