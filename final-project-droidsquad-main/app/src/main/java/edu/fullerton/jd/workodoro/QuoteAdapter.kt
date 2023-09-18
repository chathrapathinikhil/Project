package edu.fullerton.jd.workodoro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuoteAdapter(private val postModel: MutableList<PostModel>):RecyclerView.Adapter<QuoteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_quote,parent,false)
        return QuoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postModel.size
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        return holder.bindView(postModel[position])
    }
}

class QuoteViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    private val textView:TextView = itemView.findViewById(R.id.textView)
    private val textView1:TextView = itemView.findViewById(R.id.textView1)
    private val textView2:TextView = itemView.findViewById(R.id.textView2)
    private val textView3:TextView = itemView.findViewById(R.id.textView3)
    fun bindView(postModel: PostModel){
        textView.text=postModel.title
        textView1.text=postModel.body
        textView2.text=postModel.id.toString()
        textView3.text=postModel.userId.toString()
    }
}