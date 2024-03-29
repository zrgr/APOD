package com.example.apod.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        binding.iconShare.setOnClickListener { shareLink() }

        viewModel.photo.observe(requireActivity()){ data ->
            formatExplanation(data.explanation)
        }

        return binding.root
    }

    private fun formatExplanation(explanation: String) {
        var counter = 0
        var padding = 3
        var result = "\t" + explanation

        for (i in 0 until explanation.length - 1 ) {
            if ("${explanation[i]}${explanation[i+1]}" == ". ") {
                counter++
                if (counter == 3) {
                    result = createParagraph(result, i, padding)
                    counter = 0
                    padding++
                }
            }
        }

        binding.description.text = result
    }

    private fun createParagraph(result: String, i: Int, padding: Int): String {
        return result.substring(0, i+ padding) + "\n\n" + result.substring(i + padding)
    }

    private fun shareLink() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, viewModel.photo.value?.url)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)

    }

    private fun showSetBackground() {
        findNavController()
            .navigate(R.id.action_apodFragment_to_setBackgroundFragment)
    }
}