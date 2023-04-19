package com.example.servicesandbrexample.ui.main

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.servicesandbrexample.R
import com.example.servicesandbrexample.cp.ContentProviderFragment
import com.example.servicesandbrexample.utils.AppState
import com.example.servicesandbrexample.databinding.FragmentMainBinding
import com.example.servicesandbrexample.model.entities.Description
import com.example.servicesandbrexample.services.BoundService
import com.example.servicesandbrexample.services.ForegroundService
import com.example.servicesandbrexample.ui.vm.MainViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var isBound = false
    private var boundService: BoundService.ServiceBinder? = null

    private val viewModel: MainViewModel by viewModel()

    private val testReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("", "receiverResult: ${intent?.getBooleanExtra(ForegroundService.INTENT_SERVICE_DATA, false)}")
        }

    }

    private val boundServiceConnection: ServiceConnection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            boundService = service as BoundService.ServiceBinder
            isBound = true
            Log.i("", "BOUND SERVICE")
            Log.i("", "next F: ${service.nextF}")

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
            boundService = null
        }

    }

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

/*        Thread{
            ForegroundService.start(requireContext())
        }.start()*/
        binding.cpButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, ContentProviderFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        if (!isBound){
            val bindServiceIntent = Intent(context, BoundService::class.java)
            activity?.bindService(bindServiceIntent, boundServiceConnection, Context.BIND_AUTO_CREATE)
        }
        activity?.registerReceiver(testReceiver, IntentFilter(ForegroundService.INTENT_ACTION_KEY))
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

    override fun onStop() {
        activity?.unregisterReceiver(testReceiver)
        activity?.unbindService(boundServiceConnection)
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
