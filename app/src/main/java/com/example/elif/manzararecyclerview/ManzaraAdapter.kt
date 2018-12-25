package com.example.elif.manzararecyclerview

import android.media.Image
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.tek_satir_manzara.view.*
import org.w3c.dom.Text

//primary constractır ilk tetiklenen yerdir. O yüzden buraya manzaralar tipinde bir arraylist beklediğimizi ifade ettik.
class ManzaraAdapter(tumManzaralar: ArrayList<Manzara>) : RecyclerView.Adapter<ManzaraAdapter.ManzaraViewHolder>() {
    var manzaralar = tumManzaralar
    override fun getItemCount(): Int {
        Log.e("RecyclerView", "GetItemCount Tetiklendi.")
        return manzaralar.size
    }

    fun removeItem(position: Int) {
        manzaralar.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,manzaralar.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManzaraViewHolder {

        var inflater = LayoutInflater.from(parent.context)
        var tekSatirmanzara = inflater.inflate(R.layout.tek_satir_manzara, parent, false)
        Log.e("RecyclerView", "OnCreateViewHolder Tetiklendi.")
        return ManzaraViewHolder(tekSatirmanzara)
    }

    //ayrıştırılan elemanları burada atama işlemi yaptık.
    override fun onBindViewHolder(holder: ManzaraViewHolder, position: Int) {

        holder.manzaraBaslik.text = manzaralar.get(position).baslik
        holder.manzaraAciklama.text = manzaralar.get(position).aciklama

        Log.e("RecyclerView", "OnBindViewHolder Tetiklendi.")
    }

    //elemanları bulma işlemi erişme kısmı burasıdır.
    //inflate edilen alan buraya geldi Dnüştüülen xmlde hangi alanlar var ise burada değişkene çıkardık.
    inner class ManzaraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //itemView:View= View genel bir sınıf o yüzden okuyamadık o yüzden typecasting yapıldı.
        var teksatirManzara = itemView as CardView
        var manzaraBaslik = teksatirManzara.tvManzaraBaslik
        var manzaraAciklama = teksatirManzara.tvManzaraAciklama

        //log kaydını yazamabilmem için bir methodun içinde olması gerekir. O yüzden inite koyduk
        init {
            manzaraBaslik.tvManzaraBaslik as TextView
            manzaraAciklama.tvManzaraAciklama as TextView

        }

    }
    fun removeAt(position: Int) {
        manzaralar.removeAt(position)
        notifyItemRemoved(position)
    }



}
