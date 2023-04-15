package com.example.apod.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.apod.ApodViewModel
import com.example.apod.R
import com.example.apod.databinding.FragmentApodBinding

class ApodFragment: Fragment() {

    private val viewModel: ApodViewModel by activityViewModels()
    private lateinit var binding : FragmentApodBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentApodBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        binding.iconWallpaper.setOnClickListener { showSetBackground() }

        viewModel.photo.observe(requireActivity()){ data ->
            formatExplanation(data.explanation)
        }

        return binding.root
    }

    private fun formatExplanation(explanation: String) {
        var counter = 0
        var result = explanation

        for (i in explanation.indices) {
            if (explanation[i] == '.') {
                counter++
                if (counter == 3) {
                    result = result.substring(0, i+1) + "\n\n" + result.substring(i + 1)
                    counter = 0
                }
            }
        }
        binding.description.text = result
    }


    private fun showSetBackground() {
        findNavController()
            .navigate(R.id.action_apodFragment_to_setBackgroundFragment)
    }
}