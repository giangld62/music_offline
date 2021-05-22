package com.unica.musicplayer

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.unica.musicplayer.databinding.FragmentSongBinding


class SongFragment : Fragment(), MusicAdapter.IMusic {
    private var binding : FragmentSongBinding? = null
    private var listOfSong = mutableListOf<SongData>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listOfSong = (activity as MainActivity).getAllSong()
        binding?.rcvSong?.adapter = MusicAdapter(this)
        binding?.rcvSong?.layoutManager = LinearLayoutManager(context)
        binding?.rcvSong?.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun getCount(): Int {
        return listOfSong.size
    }

    override fun getData(position: Int): SongData {
        return listOfSong[position]
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(context,PlayerActivity::class.java)
        intent.putExtra("song",listOfSong[position])
        intent.putExtra("position",position)
        startActivity(intent)
    }


}