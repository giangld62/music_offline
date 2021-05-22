package com.unica.musicplayer


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unica.musicplayer.databinding.MusicItemBinding

class MusicAdapter:RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {
    private val inter:IMusic

    constructor(inter:IMusic){
        this.inter = inter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        return MusicViewHolder(MusicItemBinding.inflate(LayoutInflater.from(parent.context),parent,false),inter)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.binding.data = inter.getData(position)
    }

    override fun getItemCount(): Int {
        return inter.getCount()
    }

    interface IMusic{
        fun getCount():Int
        fun getData(position:Int):SongData
        fun onItemClick(position: Int)
    }

    class MusicViewHolder(val binding:MusicItemBinding,inter:IMusic):RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener{
                inter.onItemClick(adapterPosition)
            }
        }
    }


//    private fun getAlbumArt(uri:String):ByteArray{
//        val retriever = MediaMetadataRetriever()
//        retriever.setDataSource(uri)
//        val art = retriever.embeddedPicture
//        retriever.release()
//        return art!!
//    }
}