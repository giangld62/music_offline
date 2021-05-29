package com.unica.musicplayer

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaMetadataRetriever
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.unica.musicplayer.databinding.ActivityMainBinding

const val RESQUEST_CODE = 1

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object{
        var listOfSong = mutableListOf<SongData>()
    }
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        permission()
        getAllSong()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun getAllSong():MutableList<SongData>{
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ARTIST,
        )
        val cursor = contentResolver.query(uri,projection,null,null,null)
        if(cursor!=null){
            while(cursor.moveToNext()){
                val album = cursor.getString(0)
                val title = cursor.getString(1)
                val duration = cursor.getString(2)
                val path = cursor.getString(3)
                val artist = cursor.getString(4)
                listOfSong.add(SongData(path,title,artist,album,duration))
            }
            cursor.close()
        }
        return listOfSong
    }

    private fun permission() {
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), RESQUEST_CODE)
        }
        else{
            Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == RESQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
            else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), RESQUEST_CODE)
            }
        }
    }

//    fun openFistFragment(clazz:Class<out Fragment>){
//        supportFragmentManager.beginTransaction().add(clazz.newInstance(),clazz.name).addToBackStack(null).commit()
//    }
}