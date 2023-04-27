package com.example.apod.ui.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apod.ApodViewModel
import com.example.apod.R
import com.example.apod.databinding.FragmentGalleryBinding
import com.example.apod.ui.ApodListener
import com.example.apod.ui.GalleryAdapter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.util.*

class GalleryFragment : Fragment(), AdapterView.OnItemSelectedListener  {

    private val viewModel: ApodViewModel by activityViewModels()
    private lateinit var binding: FragmentGalleryBinding
    private var isReversed = true
    private lateinit var layoutManager: LinearLayoutManager

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
        })


        binding.changeApodOrder.setOnClickListener { changeApodOrder() }

        binding.fabSelectDate.setOnClickListener{ pickDate() }

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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        updateGallery(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun updateGallery(position: Int) {

    }

    private fun pickDate() {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.ApodDatePickerStyle,
            { view, year, monthOfYear, dayOfMonth ->
                getApodByDate(dayOfMonth, monthOfYear, year )
            },
            year,
            month,
            day
        )

        //First APOD released date
        calendar.set(1995, 6, 17)
        val currentTime = Date().time
        datePickerDialog.datePicker.minDate = currentTime - (currentTime - calendar.timeInMillis)
        datePickerDialog.datePicker.maxDate = Date().time
        datePickerDialog.show()
    }

    private fun getApodByDate(day: Int, month: Int, year: Int) {
        viewModel.getApodByDate(day, month, year)
        findNavController()
            .navigate(R.id.action_galleryFragment_to_apodFragment)
    }

}