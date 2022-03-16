package ru.kudesnik.fastforex.ui.favourites

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kudesnik.fastforex.R
import ru.kudesnik.fastforex.databinding.FavouriteFragmentBinding
import ru.kudesnik.fastforex.databinding.MainFragmentBinding
import ru.kudesnik.fastforex.model.AppState
import ru.kudesnik.fastforex.ui.main.MainFragment
import ru.kudesnik.fastforex.ui.main.MainFragmentAdapter
import ru.kudesnik.fastforex.ui.main.MainViewModel

class FavouriteFragment : Fragment() {
    private val viewModel: FavouriteViewModel by viewModel()
    private var _binding: FavouriteFragmentBinding? = null
    private val binding get() = _binding!!
    private var adapter: FavouriteFragmentAdapter? = null
    private var listAdapter: List<String> = listOf()
    private var selectedItemSpinner = ""
    private var selectedFavouriteCurrency = ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavouriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
//            adapter = FavouriteFragmentAdapter(editTextSum.text.toString().toInt(), requireContext(), object :
//                MainFragment.SetFavourites {
//                override fun setFav(item: String) {
//                    viewModel.setFavourites(name = item)
//                }
//
//            })

            recyclerViewFavourite.adapter = adapter


            viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
            viewModel.getCurrenciesName()
            viewModel.getFavourites()
            viewModel.getCurrencyList(selectedItemSpinner, viewModel.favouriteLiveDataString.toString())
            Log.d("myTag", "OnViewCreated. vm - ${viewModel.getFavourites()}")


//Spinner

            button.setOnClickListener {
                viewModel.getCurrencyList(selectedItemSpinner, viewModel.favouriteLiveDataString.toString())

            }
        }
    }

    private fun getSpinner() =with(binding){
        Log.d("testingMy", "OnViewCreated. listAdapter - $listAdapter")
        val adp1: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1, listAdapter
            //simple_list_item_1
        )
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adp1
        val index = if (selectedItemSpinner != "") {
            listAdapter.indexOf(selectedItemSpinner)
        } else if (listAdapter.contains("USD")) {
            listAdapter.indexOf("USD")
        } else {
            listAdapter.indexOfFirst { true }
        }


        spinner.setSelection(index)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                selectedItemSpinner = spinner.selectedItem.toString()

                Log.d(
                    "testingMy", "OnSelected. listAdapter - " +
                            "${
                                selectedItemSpinner
                            }, $selectedItemPosition"
                )
//                viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
                adapter?.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
//        viewModel.getCurrencyList(selectedItemSpinner)

    }


    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {

            is AppState.SuccessFavourites -> {
                listAdapter = appState.currenciesName.currencies.keys.toList()
                getSpinner()
                adapter = FavouriteFragmentAdapter(
                    editTextSum.text.toString().toInt(),
                    requireContext(),
                    object : MainFragment.SetFavourites {
                        override fun setFav(item: String) {
                            viewModel.setFavourites(name = item)
                        }

                    }
                ).apply {
                    Log.d("myTag", "Favour render setCurrency = ${appState.currencyData}")

                    setCurrency(appState.currencyData)
                }
                recyclerViewFavourite.adapter = adapter
            }
            else -> {}
        }
    }

    companion object {
        fun newInstance() = FavouriteFragment()
    }
}