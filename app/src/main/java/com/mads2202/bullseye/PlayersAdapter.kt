package com.mads2202.bullseye

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlayersAdapter(val players:Map<String, Int>): RecyclerView.Adapter<PlayersAdapter.PlayersVH>() {
    class PlayersVH(val itemView: View):RecyclerView.ViewHolder(itemView) {
        val scoreTv=itemView.findViewById<TextView>(R.id.score_text_view)
        val nameTv=itemView.findViewById<TextView>(R.id.name_text_view)
        fun bind(name:String, score:Int){
            nameTv.text=name
            scoreTv.text=score.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersVH {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.players_layout_item,parent,false)
        return PlayersVH(view)
    }

    override fun onBindViewHolder(holder: PlayersVH, position: Int) {
       holder.bind(players.keys.elementAt(position),players.values.elementAt(position))
    }

    override fun getItemCount(): Int {
        return players.size
    }
}