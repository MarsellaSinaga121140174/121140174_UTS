package com.example.uts_pam.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uts_pam.databinding.ItemUserBinding
import com.example.uts_pam.model.DataItem

class HomeAdapter(
    private var userList: ArrayList<DataItem>
): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var filterUser: ArrayList<DataItem>

    init {
        filterUser = userList
    }

    inner class HomeViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: DataItem){
            binding.tvName.text = "${user.firstName} ${user.lastName}"
            binding.tvEmail.text = user.email
            Glide.with(binding.root)
                .load(user.avatar)
                .into(binding.ivImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(view)
    }

    fun setData(newList: DataItem){
        filterUser.add(newList)
        notifyDataSetChanged()
    }

    fun clear(){
        filterUser.clear()
        notifyDataSetChanged()
    }

    fun setFilterUser(data: ArrayList<DataItem>){
        filterUser = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }
}