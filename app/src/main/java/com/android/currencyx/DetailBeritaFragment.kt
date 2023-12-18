package com.android.currencyx

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.currencyx.databinding.FragmentDetailBeritaBinding
import com.bumptech.glide.Glide

class DetailBeritaFragment : Fragment() {
    private var _binding : FragmentDetailBeritaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBeritaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Mengambil argumen yang dikirim dari navigasi
        val judul = arguments?.getString("judul")
        val isi = arguments?.getString("isi")
        val tanggal = arguments?.getString("tanggal")
        val gambar = arguments?.getString("gambar")

        // Menemukan TextView berdasarkan ID dari layout
        val tvNamaArtikel = binding.tvNamaJudul
        val tvTanggal = binding.tanggal
        val tvDeskripsi = binding.tvDeskripsi
        val img = binding.imgDetail

        // Set nilai dari argumen ke TextView yang sesuai
        tvNamaArtikel.text = judul
        tvTanggal.text = tanggal
        tvDeskripsi.text = isi
        gambar?.let {
            Glide.with(requireContext())
                .load(it) // it adalah URL gambar
                .into(img)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}