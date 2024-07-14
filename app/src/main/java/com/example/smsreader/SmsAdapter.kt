package com.example.smsreader

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class SmsAdapter(private val smsList: List<MainActivity.SmsMessage>) : RecyclerView.Adapter<SmsAdapter.SmsViewHolder>() {

    class SmsViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView) {
        val smsAddress: TextView = cardView.findViewById(R.id.smsAddress)
        val smsBody: TextView = cardView.findViewById(R.id.smsBody)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sms, parent, false) as CardView
        return SmsViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: SmsViewHolder, position: Int) {
        holder.smsAddress.text = smsList[position].address
        holder.smsBody.text = smsList[position].body
    }

    override fun getItemCount() = smsList.size
}

