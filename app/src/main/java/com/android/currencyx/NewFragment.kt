package com.android.currencyx

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.currencyx.databinding.FragmentNewBinding
import com.android.model.modelNews
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

import java.util.UUID

class NewFragment : Fragment() {

    private var _binding : FragmentNewBinding? = null

    private lateinit var imgUri : Uri
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.berita)
//        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
//        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
//
//        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
//        actionBar?.apply {
//            setDisplayHomeAsUpEnabled(true) // Menampilkan tombol back
//            title = "" // Mengatur judul toolbar
//        }

        val databaseReference = FirebaseDatabase.getInstance().getReference("berita")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val newList = mutableListOf<modelNews>()

                for (snapshot in dataSnapshot.children) {
//                    val key = snapshot.ref.key
                    val judul = snapshot.child("judul").getValue(String::class.java) ?: ""
                    val isi = snapshot.child("isi").getValue(String::class.java) ?: ""
                    val tanggal = snapshot.child("tanggal").getValue(String::class.java) ?: ""



                    val new = modelNews(snapshot.key!!, judul, isi, tanggal,"", )
                    newList.add(new)

//                    // Ambil URL gambar sesuai dengan kunci yang ada di Firebase Realtime Database
//                    val storageRef = FirebaseStorage.getInstance().reference.child("berita/${snapshot.key}/image.jpg")
//
//                    storageRef.downloadUrl.addOnSuccessListener { uri ->
//                        // Simpan URL gambar ke properti imgURL pada objek spot yang sesuai
//                        new.gambar = uri.toString()
//                    }.addOnFailureListener {
//                        // Handle error jika gagal mengambil URL gambar dari Cloud Storage
//                    }
                }

                // Gunakan spotList dalam RecyclerViewAdapter
                val adapter = RecyclerNew(newList) {

                    // Mengakses NavController dan melakukan navigasi ke DetailFragment dengan argumen yang diperlukan
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailBeritaFragment(
                        it.judul,
                        it.isi,
                        it.tanggal,
                        it.gambar
                    )
                    findNavController().navigate(action)
                }
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error jika terjadi pembatalan pengambilan data dari Realtime Database
            }
        })

    }
}