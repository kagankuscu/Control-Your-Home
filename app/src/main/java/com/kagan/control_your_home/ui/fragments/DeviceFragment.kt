package com.kagan.control_your_home.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kagan.control_your_home.R
import com.kagan.control_your_home.databinding.FragmentDeviceBinding
import com.kagan.control_your_home.viewmodel.DBViewModel
import kotlin.collections.ArrayList

class DeviceFragment : Fragment(R.layout.fragment_device) {

    val TAG = "DeviceFragment"
    lateinit var binding: FragmentDeviceBinding
    private val args: DeviceFragmentArgs by navArgs()
    private val dbViewModel: DBViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDeviceBinding.bind(view)
        val fromDialog = createTimeDialog()
        val toDialog = createTimeDialog()
        val repeat = createDayDialog()

        fromDialog?.setMessage("Start Time")
        toDialog?.setMessage("Finish Time")

        Log.d(TAG, TAG)

        setInfo()

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigateUp()
        }

        binding.tvName.text = args.deviceName

        binding.ivBack.setOnClickListener {
            callback.handleOnBackPressed()
        }

        binding.cvFrom.setOnClickListener {
            fromDialog?.show()
        }

        binding.cvTo.setOnClickListener {
            toDialog?.show()
        }

        binding.flRepeat.setOnClickListener {
            Log.d(TAG, "onViewCreated: ")
            repeat?.show()
        }
    }


    private fun createTimeDialog(): AlertDialog.Builder? {
        return activity?.let {
            AlertDialog.Builder(it)
                .setTitle("Time")
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                    Log.d(TAG, "createDialog: $dialog $id")
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                    Log.d(TAG, "createDialog: $dialog $id")
                })
        }
    }

    private fun setInfo() {
        dbViewModel.getInfo()
        dbViewModel.info.observe(viewLifecycleOwner, Observer {
            binding.tvHumidity.text = getString(R.string.info_hum, it.hum)
            binding.tvLum.text = getString(R.string.info_lum, it.lum)
            binding.tvTemp.text = getString(R.string.info_temp, it.temp)
        })
    }

    private fun createDayDialog(): AlertDialog.Builder? {
        val selectedItems = ArrayList<Int>()

        return activity?.let {
            AlertDialog.Builder(it)
                .setTitle("Days")
                .setMultiChoiceItems(
                    R.array.days,
                    null,
                    DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
                        if (isChecked) {
                            selectedItems.add(which)
                        } else {
                            selectedItems.remove(Integer.valueOf(which))
                        }
                    })
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                    Log.d(TAG, "createDayDialog: day,$which")
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                    Log.d(TAG, "createDayDialog: ")
                })
        }
    }
}