package com.example.localization.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import java.io.*
import com.example.localization.R

class LocalizationList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_localization_list)
        val mainList = listarArquivos()
        val listView = this.findViewById<ListView>(R.id.listView)
        listView.adapter = CustomAdapter(this, mainList)

        val btnVoltar = this.findViewById<Button>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun listarArquivos(): MutableList<String> {

        var lista = mutableListOf<String>()
        val directory = this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        File("$directory").walk().forEach {
            if(it.name == "Documents") {

            } else if (it.name.endsWith("crd")){
                lista.add(it.name)
            }
        }
        return lista
    }

    private class CustomAdapter(context: Context, list: MutableList<String>): BaseAdapter() {

        private val mContext: Context
        private val mList: MutableList<String>
        init {
            mContext = context
            mList = list
        }
        // Responsável por passar a quantidade de itens na Lista
        override fun getCount(): Int {
            return mList.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return  mList[position]
        }

        // Responsável por renderizar cada linha
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(R.layout.row_layout, viewGroup, false)
            rowMain.findViewById<TextView>(R.id.txt_nome).setText(mList[position])
            return rowMain
            /*val textView = TextView(mContext)
            textView.text = mList[position]
            return textView*/
        }
    }
}