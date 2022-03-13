package ru.kudesnik.fastforex.ui.main

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kudesnik.fastforex.databinding.ItemMainBinding
import ru.kudesnik.fastforex.model.entities.FetchAll
import java.util.*

class MainFragmentAdapter : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var currencyData: List<Pair<String, Double>> = listOf()
    private lateinit var testData : List<String>

    private lateinit var binding: ItemMainBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setCurrency(data: FetchAll) {
//        testData = data.results.toList()
        currencyData = data.results.toSortedMap().toList()
        notifyDataSetChanged()
//        Log.d("listOperations", "Действие 1 - ${testData}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

//  holder.bind(testData[position])
        holder.bind(currencyData[position])
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
//    override fun getItemCount() = testData.size
    override fun getItemCount() = currencyData.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        fun bind(currency: String) = with(binding) {
//            Log.d("listOperations", "Действие 3 - ${currency}")

        fun bind(currency: Pair<String, Double>) = with(binding) {
            currencyItem.text = currency.first
            exchangeItem.text = currency.second.toString()

        }
    }
}


