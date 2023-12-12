package com.android.currencyx

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.model.modelNews
import com.bumptech.glide.Glide


class RecyclerNew(private val newList: List<modelNews>) :
    RecyclerView.Adapter<RecyclerNew.NewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_berita, parent, false)
        return NewViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {
        val new = newList[position]
        holder.bind(new)
        holder.itemView.setOnClickListener {
            // Mengakses NavController dan melakukan navigasi ke DetailFragment dengan argumen yang diperlukan
            val action = NewFragmentDirections.actionNewFragmentToDetailBeritaFragment(
                new.judul,
                new.isi,
                new.tanggal,
                new.gambar
            )
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return newList.size
    }

    inner class NewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvDate: TextView = itemView.findViewById(R.id.Date)
        private val gambar: ImageView=itemView.findViewById(R.id.image)

        fun bind(new :modelNews) {
            tvTitle.text = new.judul
            tvDate.text = new.tanggal
            Glide.with(itemView)
                .load(new.gambar) // post.profil berisi URL gambar
                .into(gambar) // profil adalah ImageView

        }
    }
}