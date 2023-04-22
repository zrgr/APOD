package com.example.apod.ui.fragments

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.apod.ApodViewModel
import com.example.apod.R
import com.example.apod.databinding.FragmentGalleryBinding
import com.example.apod.ui.ApodListener
import com.example.apod.ui.GalleryAdapter
import kotlinx.coroutines.launch

class GalleryFragment : Fragment(), AdapterView.OnItemSelectedListener  {

    private val viewModel: ApodViewModel by activityViewModels()
    private lateinit var binding: FragmentGalleryBinding
    private var isReversed = true
    private lateinit var layoutManager: LinearLayoutManager

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
        layoutManager = LinearLayoutManager(requireContext())
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true

        binding.photosGrid.adapter = GalleryAdapter(ApodListener { apod ->
            viewModel.onApodClicked(apod)
            findNavController()
                .navigate(R.id.action_galleryFragment_to_apodFragment)
        }, this@GalleryFragment)


        binding.changeApodOrder.setOnClickListener { changeApodOrder() }

        binding.photosGrid.layoutManager = layoutManager

        populateSpinner()
        return binding.root
    }

    private fun changeApodOrder() {


        layoutManager.reverseLayout = !isReversed
        layoutManager.stackFromEnd = !isReversed
        isReversed = !isReversed
        binding.photosGrid.layoutManager = layoutManager
        binding.arrowOrder.background  = flipArrow()
    }

    private fun flipArrow(): Drawable? {
        return if (!isReversed)
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_arrow_upward)
        else
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_arrow_downward)
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

    fun getImage(url: String): Bitmap? {

        var test: Bitmap? = null

        lifecycleScope.launch {
            test = getBitmap(url)
        }

        return test
    }

    suspend fun getBitmap(url: String): Bitmap {
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data(url)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        updateGallery(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun updateGallery(position: Int) {

    }

}