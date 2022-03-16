package ru.kudesnik.fastforex.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.kudesnik.fastforex.databinding.ItemMainBinding
import ru.kudesnik.fastforex.model.entities.FetchAll

class MainFragmentAdapter(
    var sum: Int,
    private val context: Context,
    private val setFavourite: MainFragment.SetFavourites,
    private val delFavourite: MainFragment.DelFavourites,
) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var currencyDataList: List<Pair<String, Double>> = listOf()
    private lateinit var currencyData: FetchAll
    private lateinit var favorData: String
    private var fabSorted = 0
    private lateinit var binding: ItemMainBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setCurrency(data: FetchAll, dataFavor: String, fabSort: Int) {
        currencyDataList = data.results.toSortedMap().toList()
        currencyData = data
        favorData = dataFavor
        fabSorted = fabSort

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        when (fabSorted) {
            0 -> holder.bind(currencyDataList.sortedBy { it.first }[position])
            1 -> holder.bind(currencyDataList.sortedByDescending { it.first }[position])
            2 -> holder.bind(currencyDataList.sortedBy { it.second }[position])
            3 -> holder.bind(currencyDataList.sortedByDescending { it.second }[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount() = currencyDataList.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(currency: Pair<String, Double>) = with(binding) {
            currencyItem.text = currency.first
            exchangeItem.text = ((currency.second) * sum).toString()
            if (favorData.contains(currency.first)) {
                favourites.setColorFilter(
                    ContextCompat.getColor(context, android.R.color.holo_orange_light),
                    android.graphics.PorterDuff.Mode.MULTIPLY
                )

            } else favourites.setColorFilter(
                ContextCompat.getColor(context, android.R.color.darker_gray),
                android.graphics.PorterDuff.Mode.MULTIPLY
            )

            favourites.setOnClickListener {
                if (favorData.contains(currency.first)) {
                    favourites.setColorFilter(
                        ContextCompat.getColor(context, android.R.color.darker_gray),
                        android.graphics.PorterDuff.Mode.MULTIPLY
                    )
                    delFavourite.delFav(currency.first)
                } else {
                    favourites.setColorFilter(
                        ContextCompat.getColor(context, android.R.color.holo_orange_light),
                        android.graphics.PorterDuff.Mode.MULTIPLY
                    )
                    setFavourite.setFav(currency.first)
                }
            }
        }
    }
}



