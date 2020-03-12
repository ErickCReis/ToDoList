package com.example.todolist.view.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.model.ToDo
import kotlinx.android.synthetic.main.frame_to_do.view.*

class ListToDoAdapter (private var listToDo: MutableList<ToDo>,
                       private val context: Context,
                       private val listener: OnClickListener
): RecyclerView.Adapter<ListToDoAdapter.ViewHolder>(), Filterable {

    var filterList = listToDo

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.checkBox
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.frame_to_do, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listToDo.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val toDo = listToDo[position]
        holder.checkBox.text = toDo.title
        if (toDo.done!!) {
            holder.checkBox.isChecked = true
        }

        holder.checkBox.setOnClickListener {
            listener.checkToDo(holder.checkBox.isChecked, position)
        }
    }

    interface OnClickListener {
        fun checkToDo(state: Boolean, position: Int)
    }

    private fun delete(position: Int) {
        listToDo.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clear() {
        for (i in 0 until this.itemCount) {
            this.delete(i)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                listToDo = filterResults.values as MutableList<ToDo>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = FilterResults()
                filterResults.values = if (queryString==null || queryString.isEmpty())
                    filterList
                else
                    filterList.filter { it.title!!.toLowerCase().contains(queryString)}
                return filterResults
            }
        }
    }

}