package com.eazyalgo.chatapplication

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.eazyalgo.chatapplication.UserAdapter.UserViewHolder

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        val ITEM RECEIVE = 1
    val ITEM SENT = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType ==1){
            val view: View = LayoutInflater.from(context).inflate(R.layout.receive, parent, false)
            return ReceiveViewHolder(view)
        }else{
            val view: View = LayoutInflater.from(context).inflate(R.layout.sent, parent, false)
            return SentViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]


        if(holder.javaClass == SentViewHolder::class.java){

            val viewHolder = holder as SentViewHolder
holder.sentMessage.text=currentMessage.message


        }else{
            val viewHolder =holder as ReceiveViewHolder
            holder.receiveMessage.text=currentMessage.message
        }

    }



    override fun getItemCount(): Int {

        Override fun getItemViewType(position: Int):Int{
val currentmessage = messageList[position]
            if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderID)){
                return ITEM_SENT
            }else{
                return ITEM_RECEIVE
            }

        }

        override fun getItemCount(): Int{
            return messageList.size
        }

    }

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent_message)
    }

    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receiveMessage = itemView.findViewById<TextView>(R.id.txt_receive_message)

    }

    }