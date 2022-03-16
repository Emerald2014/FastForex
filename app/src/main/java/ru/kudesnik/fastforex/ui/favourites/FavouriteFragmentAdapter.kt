package ru.kudesnik.fastforex.ui.favourites

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ru.kudesnik.fastforex.FAVOURITE
import ru.kudesnik.fastforex.MAIN_SETTINGS
import ru.kudesnik.fastforex.R
import ru.kudesnik.fastforex.databinding.ItemMainBinding
import ru.kudesnik.fastforex.model.entities.FetchAll
import ru.kudesnik.fastforex.ui.main.MainFragment
import java.lang.StringBuilder

class FavouriteFragmentAdapter(
    val sum: Int,
    private val context: Context,
    private val setFavourite: MainFragment.SetFavourites,
) :
    RecyclerView.Adapter<FavouriteFragmentAdapter.MainViewHolder>() {
    private var currencyDataList: List<Pair<String, Double>> = listOf()
    private lateinit var currencyData: FetchAll
    private lateinit var testData: List<String>

    private lateinit var binding: ItemMainBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setCurrency(data: FetchAll) {
//        testData = data.results.toList()
        currencyDataList = data.results.toSortedMap().toList()
        currencyData = data
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
            favourites.setOnClickListener {
                if (currencyData.isFavourites) {
                    favourites.setBackgroundColor(context.resources.getColor(android.R.color.holo_red_dark))
//                    favourites.background.setTint(context.resources.getColor(android.R.color.holo_red_dark))
                    delFavourites(currency.first)
                    setFavourite.setFav(currency.first)

                    currencyData.isFavourites = false
                } else {
                    currencyData.isFavourites = true
                    favourites.setBackgroundColor(context.resources.getColor(android.R.color.holo_blue_bright))
                    setFavourite.setFav(currency.first)

                    setFavourite(currency.first)
                }
            }
        }
    }

    private fun delFavourites(first: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            MAIN_SETTINGS, AppCompatActivity.MODE_PRIVATE
        )
        val stringBuilder = StringBuilder(sharedPreferences.getString(FAVOURITE, ""))

        while (stringBuilder.contains(first)) {
            stringBuilder.delete(
                stringBuilder.indexOf(",$first"),
                stringBuilder.indexOf(",$first") + 4
            )
            Log.d("listOperations", "Удаление $first- $stringBuilder")
        }
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(FAVOURITE, stringBuilder.toString())
        editor.apply()

    }

    private fun setFavourite(first: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            MAIN_SETTINGS, AppCompatActivity.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val stringBuilder: StringBuilder = StringBuilder()
        stringBuilder.append(sharedPreferences.getString(FAVOURITE, "")).append(',').append(first)

        Log.d("listOperations", "Действие 3 - $stringBuilder")
        editor.putString(FAVOURITE, stringBuilder.toString())
        editor.apply()
    }

}



