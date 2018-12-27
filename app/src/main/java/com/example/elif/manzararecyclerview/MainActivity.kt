package com.example.elif.manzararecyclerview


import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_layout.view.*


class MainActivity : AppCompatActivity(){

    //bu sınfın her yerinden ulaşmak için burada tanımladık.
    var tumManzaralar = ArrayList<Manzara>()
    var myAdapter = ManzaraAdapter(tumManzaralar)
    lateinit var view: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        floatingActionButton.setOnClickListener {
            alertdialog()
        }

        recyclerViewManzara.layoutManager = LinearLayoutManager(this)
        recyclerViewManzara.adapter = myAdapter

        //nesne üretimi yapabilmek için object
        var swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                myAdapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerViewManzara)
    }
    fun alertdialog() {
        var alert = AlertDialog.Builder(this)
        view = layoutInflater.inflate(R.layout.dialog_layout, null)
        alert.setView(view)
        alert.setPositiveButton("Ekle", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, secim: Int) {
                var bas=view.et_baslik
                var acik=view.et_aciklama
                myAdapter.addItem(bas.text.toString(),acik.text.toString())
            }
        }).setNegativeButton("Vazgeç", null).create()
        alert.show()
    }



}


