package com.myexample.wm.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myexample.wm.R
import com.myexample.wm.databinding.MainFragmentBinding
import com.myexample.wm.domain.uc.GetCountries

class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, MyViewModelFactory(GetCountries()))[MainViewModel::class.java]
    }

    private lateinit var recyclerView: RecyclerView
    private var lastKnownPosition = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<MainFragmentBinding>(
            inflater,
            R.layout.main_fragment,
            container,
            false
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.rvCountries.layoutManager = LinearLayoutManager(context)
        binding.rvCountries.adapter = CountriesAdapter()
        recyclerView = binding.rvCountries


        viewModel.countries.observe(viewLifecycleOwner) { value ->
            (binding.rvCountries.adapter as? CountriesAdapter)?.update(value.itemsList)
            if (!value.isLoading && value.error.isEmpty()) {
                if (lastKnownPosition > 0) {
                    binding.rvCountries.scrollToPosition(lastKnownPosition)
                    lastKnownPosition = -1
                }
            } else if (value.error.isNotEmpty())
                showAlert(value.error)
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lastKnownPosition = savedInstanceState?.getInt("lastKnownPosition") ?: 0

    }

    override fun onSaveInstanceState(outState: Bundle) {
        lastKnownPosition = (recyclerView.layoutManager as? LinearLayoutManager)?.findFirstCompletelyVisibleItemPosition() ?: 0
        outState.putInt("lastKnownPosition", lastKnownPosition)
        super.onSaveInstanceState(outState)
    }

    private fun showAlert(alertText: String) {
        val alertDialog = AlertDialog.Builder(requireActivity())
            .setTitle("Something went wrong")
            .setMessage(alertText)
            .setNegativeButton("OK") { _, _ -> }
            .create()
        alertDialog.show()
    }
}