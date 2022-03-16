package ru.kudesnik.fastforex.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Precision
import coil.size.Scale
import ru.kudesnik.fastforex.R
import ru.kudesnik.fastforex.databinding.ItemMainBinding
import ru.kudesnik.fastforex.model.entities.FetchAll

class MainFragmentAdapter(
    val sum: Int,
    private val context: Context,
    private val setFavourite: MainFragment.SetFavourites,
    private val delFavourite: MainFragment.DelFavourites,
) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var currencyDataList: List<Pair<String, Double>> = listOf()
    private lateinit var currencyData: FetchAll
    private lateinit var favorData: String
    private lateinit var testData: List<String>

    private lateinit var binding: ItemMainBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setCurrency(data: FetchAll, dataFavor: String) {
//        testData = data.results.toList()
        currencyDataList = data.results.toSortedMap().toList()
        currencyData = data
        favorData = dataFavor
        notifyDataSetChanged()
//        Log.d("listOperations", "Действие 1 - ${testData}")
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

//  holder.bind(testData[position])
        holder.bind(currencyDataList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //    override fun getItemCount() = testData.size
    override fun getItemCount() = currencyDataList.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        fun bind(currency: String) = with(binding) {
//            Log.d("listOperations", "Действие 3 - ${currency}")

        fun bind(currency: Pair<String, Double>) = with(binding) {
            currencyItem.text = currency.first
            exchangeItem.text = ((currency.second) * sum).toString()
            if (favorData.contains(currency.first)) {
//                favourites.setColorFilter(android.R.color.holo_orange_light);
                favourites.setColorFilter(
                    ContextCompat.getColor(context, android.R.color.holo_orange_light),
                    android.graphics.PorterDuff.Mode.MULTIPLY
                );

            } else favourites.setColorFilter(
                ContextCompat.getColor(context, android.R.color.darker_gray),
                android.graphics.PorterDuff.Mode.MULTIPLY
            );

            favourites.setOnClickListener {
                if (favorData.contains(currency.first)) {
                    favourites.setColorFilter(
                        ContextCompat.getColor(context, android.R.color.darker_gray),
                        android.graphics.PorterDuff.Mode.MULTIPLY
                    );
                    delFavourite.delFav(currency.first)

                    Log.d("myTag", "SetFav isFav = ${currency.first}")

                } else {
                    favourites.setColorFilter(
                        ContextCompat.getColor(context, android.R.color.holo_orange_light),
                        android.graphics.PorterDuff.Mode.MULTIPLY
                    );
                    setFavourite.setFav(currency.first)
                    Log.d("myTag", "SetFav isNOTFav = ${currency.first}")
                }
            }
        }
    }


}



