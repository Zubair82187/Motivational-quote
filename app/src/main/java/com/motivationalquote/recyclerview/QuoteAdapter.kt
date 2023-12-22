package com.motivationalquote.recyclerview

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.motivationalquote.R

class QuoteAdapter(val list:ArrayList<String>, val context: Context): RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {
    inner class QuoteViewHolder(itemView: View): ViewHolder(itemView) {
        val quote = itemView.findViewById<TextView>(R.id.quote)
        val shareQuote = itemView.findViewById<Button>(R.id.shareQuote)
        val copyQuote = itemView.findViewById<Button>(R.id.copyQuote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.quote_layout,parent,false)
        return QuoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.quote.text = list[position]
        holder.shareQuote.setOnClickListener{
            val sendQuote = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, list[position])
                type = "text/plain"
            }
            context.startActivity(sendQuote)
        }
        holder.copyQuote.setOnClickListener {
            copyToClipboard(list[position])
        }
    }

    private fun copyToClipboard(text: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", text)
        clipboardManager.setPrimaryClip(clipData)

        // Optionally, you can show a toast or perform any other action after copying
        Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
    }
}