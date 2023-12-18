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
import com.google.firebase.storage.FirebaseStorage


class RecyclerNew(
    private val newList: List<modelNews>,
    private val onItemClick: (modelNews) -> Unit
) :
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
            onItemClick(new)
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

            // Ambil URL gambar sesuai dengan kunci yang ada di Firebase Realtime Database
            val storageRef = FirebaseStorage.getInstance().reference.child("berita/${new.id}/image.jpg")

            storageRef.downloadUrl.addOnSuccessListener { uri ->
                // Simpan URL gambar ke properti imgURL pada objek spot yang sesuai
                new.gambar = uri.toString()

                Glide.with(itemView)
                    .load(new.gambar) // post.profil berisi URL gambar
                    .into(gambar) // profil adalah ImageView
            }.addOnFailureListener {
                // Handle error jika gagal mengambil URL gambar dari Cloud Storage
            }
//            Glide.with(itemView)
//                .load(new.gambar) // post.profil berisi URL gambar
//                .into(gambar) // profil adalah ImageView

        }
    }
}