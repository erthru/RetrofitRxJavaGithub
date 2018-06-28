package erthru.retrofitrxjavagithub.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import erthru.retrofitrxjavagithub.R
import erthru.retrofitrxjavagithub.network.model.UserResponse
import kotlinx.android.synthetic.main.repo_list.view.*

class RecyclerViewAdapterRepo(private val context: Context, private val list: List<UserResponse>?) : RecyclerView.Adapter<RecyclerViewAdapterRepo.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.repo_list,parent,false))
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val user = list?.get(position)
        holder.v.lbNameRL.text = user?.name
        holder.v.lbDescRL.text = user?.description
        holder.v.lbStarRL.text = user?.stargazers_count


    }

    class Holder(val v:View) : RecyclerView.ViewHolder(v)
}