package ru.kudesnik.fastforex.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kudesnik.fastforex.databinding.MainFragmentBinding
import ru.kudesnik.fastforex.model.AppState

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModel()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private var adapter: MainFragmentAdapter? = null
    private var recyclerViewVer2: RecyclerView? = null
    private val testData = listOf("1", "2", "3")



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
//            recyclerViewVer2 = recyclerViewMain
//            recyclerViewMain.adapter = adapter
//            adapter = MainFragmentAdapter().apply { setCurrency(testData) }
//            recyclerViewMain.adapter = adapter

            adapter = MainFragmentAdapter()
            recyclerViewMain.adapter = adapter

            viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
            viewModel.getCurrencyList()
        }
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                adapter = MainFragmentAdapter().apply {
                    setCurrency(appState.currencyData)
                }
                recyclerViewMain.adapter = adapter
            }
        }

    }

    companion object {
        fun newInstance() = MainFragment()
    }
}