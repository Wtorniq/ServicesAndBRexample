package com.example.servicesandbrexample.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.servicesandbrexample.AppState
import com.example.servicesandbrexample.databinding.FragmentMainBinding
import com.example.servicesandbrexample.model.entities.Description
import com.example.servicesandbrexample.vm.MainViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val observer = Observer<AppState>{renderData(it)}
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        viewModel.getTranslationData()
    }
    private fun renderData(appState: AppState) = with(binding){
        when(appState){
            is AppState.Error -> {
                inputView.visibility = View.INVISIBLE
                outputView.visibility = View.INVISIBLE
                progressBar.visibility = View.GONE
                Snackbar.make(root, "Error: ${appState.error}", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload"){viewModel.getTranslationData()}
                    .show()
            }
            is AppState.Loading -> {
                inputView.visibility = View.INVISIBLE
                outputView.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                inputView.visibility = View.VISIBLE
                outputView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                setData(appState.response)
            }
        }
    }
    private fun setData(response: ArrayList<Description>) = with(binding) {
        outputTv.text = response.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        fun newInstance() = MainFragment()
    }
}
