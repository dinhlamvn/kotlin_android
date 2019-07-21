package com.example.android.trackmysleepquality.sleepdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepDetailsBinding

class SleepDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout
        val binding: FragmentSleepDetailsBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_details, container, false
        )

        val application = requireNotNull(this.activity).application
        val arguments = SleepDetailFragmentArgs.fromBundle(arguments!!)

        // The database DAO
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        // View model factory to create view model
        val viewModelFactory = SleepDetailViewModelFactory(arguments.sleepNightId, dataSource)

        // Get view model from factory
        val sleepDetailViewModel = ViewModelProviders.of(this,
                viewModelFactory).get(SleepDetailViewModel::class.java)
        binding.sleepDetailViewModel = sleepDetailViewModel
        binding.setLifecycleOwner(this)

        sleepDetailViewModel.navigateToSleepTracker.observe(this, Observer {
            navigate ->
            if (navigate == true) {
                this.findNavController().navigate(
                        SleepDetailFragmentDirections.actionSleepDetailFragmentToSleepTrackerFragment()
                )
                sleepDetailViewModel.onNavigated()
            }
        })


        return binding.root
    }
}