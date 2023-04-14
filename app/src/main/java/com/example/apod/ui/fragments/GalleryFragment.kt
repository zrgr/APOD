package com.example.apod.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.apod.ApodViewModel
import com.example.apod.R
import com.example.apod.databinding.FragmentGalleryBinding
import com.example.apod.ui.ApodListener
import com.example.apod.ui.GalleryAdapter

class GalleryFragment : Fragment(), AdapterView.OnItemSelectedListener  {

    private val viewModel: ApodViewModel by activityViewModels()
    private lateinit var binding: FragmentGalleryBinding

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGalleryBinding.inflate(inflater)
        binding.lifecycleOwner = this
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

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.time_period_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.setSelection(0, false)
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        updateGallery(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun updateGallery(position: Int) {
        var gallery = binding.photosGrid

        val spanCount = when (position) {
            0 -> 1
            1,2,3 -> 2
            else -> 1
        }

        gallery.layoutManager = GridLayoutManager(requireContext(), spanCount)
    }

}