package com.example.apod.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.apod.ApodViewModel
import com.example.apod.R
import com.example.apod.databinding.FragmentGalleryBinding
import com.example.apod.ui.ApodListener
import com.example.apod.ui.GalleryAdapter

class GalleryFragment : Fragment() {

    private val viewModel: ApodViewModel by activityViewModels()
    lateinit var binding: FragmentGalleryBinding

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        binding.photosGrid.adapter = GalleryAdapter(ApodListener { apod ->
            viewModel.onApodClicked(apod)
            findNavController()
                .navigate(R.id.action_galleryFragment_to_apodFragment)
        })


        populateSpinner()


        return binding.root
    }

    private fun populateSpinner() {
        val spinner: Spinner = binding.timePeriodSpinner

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.time_period_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }
}