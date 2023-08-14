package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth

class msg_adapter(val context : Context, val msgList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){



    val ITEM_RECEIVE =1;
    val ITEM_SENT=2;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1){
            val view: View= LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return RecieveViewHolder(view)
        }else{
            val view: View= LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return msgList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentMessage = msgList[position]
      if(holder.javaClass == SentViewHolder::class.java){


          val viewHolder = holder as SentViewHolder

          holder.sentMessage.text= currentMessage.message

      }else{
          val viewHolder = holder as RecieveViewHolder
          holder.receiveMessage.text=currentMessage.message
      }
    }

    override fun getItemViewType(position:Int):Int{
        val currentMessage = msgList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }

    class SentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent_msg)
    }

    class RecieveViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val receiveMessage = itemView.findViewById<TextView>(R.id.receive_msg)
    }
}