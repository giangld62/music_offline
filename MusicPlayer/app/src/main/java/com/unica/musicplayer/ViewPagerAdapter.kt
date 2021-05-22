package com.unica.musicplayer

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter : FragmentPagerAdapter {
    constructor(fm: FragmentManager) : super(fm)

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> SongFragment()
            1 -> AlbumFragment()
            else -> SongFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title = ""
        when(position){
            0 -> title = "Song"
            1 -> title = "Album"
        }
        return title
    }
}