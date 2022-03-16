package ru.kudesnik.fastforex.ui.main

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kudesnik.fastforex.R
import ru.kudesnik.fastforex.databinding.MainFragmentBinding
import ru.kudesnik.fastforex.model.AppState

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModel()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private var adapter: MainFragmentAdapter? = null
    private var listAdapter: List<String> = listOf()
    private var selectedItemSpinner = ""
    private var fabSortDefault = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            recyclerViewMain.adapter = adapter
            recyclerViewMain.layoutManager =
                GridLayoutManager(requireContext(), getScreenOrientation())
            viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
            viewModel.getCurrenciesName() //Отображение спиннера при первом пуске
            viewModel.getCurrencyList(selectedItemSpinner)

//Spinner
            button.setOnClickListener {
                viewModel.getCurrencyList(selectedItemSpinner)
            }
//FAB
            fabSort.setOnClickListener {
                when (fabSortDefault) {
                    0 -> {
                        fabSortDefault++
                        fabSort.setImageResource(R.drawable.ic_sort_number_down)
                        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
                    }
                    1 -> {
                        fabSortDefault++
                        fabSort.setImageResource(R.drawable.ic_sort_number_up)
                        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
                    }
                    2 -> {
                        fabSortDefault++
                        fabSort.setImageResource(R.drawable.ic_sort_litter_down)
                        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
                    }
                    3 -> {
                        fabSortDefault = 0
                        fabSort.setImageResource(R.drawable.ic_sort_litter_up)
                        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
                    }
                }
            }
        }
    }

    private fun getScreenOrientation(): Int {
        return when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> 1
            Configuration.ORIENTATION_LANDSCAPE -> 2
            else -> 1
        }
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                listAdapter = appState.currenciesName.currencies.keys.toList()
                getSpinner()
                adapter = MainFragmentAdapter(
                    if (editTextSum.text.toString().trim().isNotEmpty()) {
                        editTextSum.text.toString().toInt()
                    } else {
                        editTextSum.setText("1").toString()
                        1
                    },
                    requireContext(),
                    object : SetFavourites {
                        override fun setFav(item: String) {
                            Log.d("myTag", "SetFav VM Render = ${item}")
                            viewModel.setFavourites(name = item)
                        }
                    }, object : DelFavourites {
                        override fun delFav(item: String) {
                            Log.d("myTag", "SetFav VM Render = ${item}")
                            viewModel.deleteFavourites(name = item)
                        }
                    }
                )
                    .apply {
                        setCurrency(appState.currencyData, appState.dataFavor, fabSortDefault)
                    }
                recyclerViewMain.adapter = adapter
            }
            else -> {}
        }
    }

    private fun getSpinner() = with(binding) {
        Log.d("testingMy", "OnViewCreated. listAdapter - $listAdapter")
        val adp1: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1, listAdapter
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
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    interface SetFavourites {
        fun setFav(item: String)
    }

    interface DelFavourites {
        fun delFav(item: String)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}