package ru.kudesnik.fastforex.ui.favourites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kudesnik.fastforex.databinding.ItemMainBinding
import ru.kudesnik.fastforex.model.entities.FetchAll
import ru.kudesnik.fastforex.ui.main.MainFragment

class FavouriteFragmentAdapter(
    val sum: Int,
    private val delFavourite: MainFragment.DelFavourites,
) :
    RecyclerView.Adapter<FavouriteFragmentAdapter.MainViewHolder>() {
    private var currencyDataList: List<Pair<String, Double>> = listOf()
    private lateinit var currencyData: FetchAll
    private lateinit var binding: ItemMainBinding
    private var fabSorted = 0

    @SuppressLint("NotifyDataSetChanged")
    fun setCurrency(data: FetchAll, fabSort: Int) {
        currencyDataList = data.results.toSortedMap().toList()
        currencyData = data
        notifyDataSetChanged()
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
            favourites.setOnClickListener {
                delFavourite.delFav(currency.first)
            }
        }
    }
}



