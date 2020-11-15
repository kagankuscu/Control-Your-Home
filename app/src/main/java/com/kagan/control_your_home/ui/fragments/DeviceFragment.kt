package com.kagan.control_your_home.ui.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kagan.control_your_home.R
import com.kagan.control_your_home.databinding.FragmentDeviceBinding
import com.kagan.control_your_home.others.Constant.FROM
import com.kagan.control_your_home.others.Constant.TO
import com.kagan.control_your_home.others.DialogHelper
import com.kagan.control_your_home.viewmodel.DBViewModel
import com.kagan.control_your_home.viewmodel.TimeViewModel

class DeviceFragment : Fragment(R.layout.fragment_device) {

    val TAG = "DeviceFragment"
    lateinit var binding: FragmentDeviceBinding
    private val args: DeviceFragmentArgs by navArgs()
    private val dbViewModel: DBViewModel by viewModels()
    private lateinit var timeViewModel: TimeViewModel
    lateinit var dialogHelper: DialogHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDeviceBinding.bind(view)
        dialogHelper = DialogHelper(requireContext())
        timeViewModel = ViewModelProvider(requireActivity()).get(TimeViewModel::class.java)

        Log.d(TAG, TAG)

        setInfo()
        setTime()

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigateUp()
        }

        binding.ivBack.setOnClickListener {
            callback.handleOnBackPressed()
        }

        binding.tvName.text = args.deviceName

        binding.sOnOff.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                open()
            } else {
                close()
            }
        }
    }

    private fun open() {
        setAlpha()
        val repeat = dialogHelper.createDayDialog()

        binding.cvFrom.setOnClickListener {
            navigateToTimePicker(FROM)
        }

        binding.cvTo.setOnClickListener {
            navigateToTimePicker(TO)
        }

        binding.flRepeat.setOnClickListener {
            Log.d(TAG, "onViewCreated: ")
            repeat?.setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
                Log.d(TAG, "onViewCreated: ${dialogHelper.getDays()}")
                binding.tvDays.text = dialogHelper.getDays()
            })?.show()
        }
    }

    private fun close() {
        binding.cvFrom.alpha = 0.75F
        binding.cvTo.alpha = 0.75F
        binding.flRepeat.alpha = 0.75F

        binding.cvFrom.setOnClickListener(null)
        binding.cvTo.setOnClickListener(null)
        binding.flRepeat.setOnClickListener(null)
    }

    private fun setAlpha() {
        binding.cvFrom.alpha = 1F
        binding.cvTo.alpha = 1F
        binding.flRepeat.alpha = 1F
    }

    private fun setTime() {
        timeViewModel.startTime.observe(viewLifecycleOwner, {
            if (it[0] >= 12) {
                when (it[0]) {
                    13 -> binding.tvFrom.text = getString(R.string.test, 1, it[1])
                    14 -> binding.tvFrom.text = getString(R.string.test, 2, it[1])
                    15 -> binding.tvFrom.text = getString(R.string.test, 3, it[1])
                    16 -> binding.tvFrom.text = getString(R.string.test, 4, it[1])
                    17 -> binding.tvFrom.text = getString(R.string.test, 5, it[1])
                    18 -> binding.tvFrom.text = getString(R.string.test, 6, it[1])
                    19 -> binding.tvFrom.text = getString(R.string.test, 7, it[1])
                    20 -> binding.tvFrom.text = getString(R.string.test, 8, it[1])
                    21 -> binding.tvFrom.text = getString(R.string.test, 9, it[1])
                    22 -> binding.tvFrom.text = getString(R.string.test, 10, it[1])
                    23 -> binding.tvFrom.text = getString(R.string.test, 11, it[1])
                    24 -> binding.tvFrom.text = getString(R.string.test, 12, it[1])
                }
            } else {
                binding.tvFrom.text = getString(R.string.test, it[0], it[1])
            }
        })

        timeViewModel.endTime.observe(viewLifecycleOwner, {
            if (it[0] >= 12) {
                when (it[0]) {
                    13 -> binding.tvTo.text = getString(R.string.test, 1, it[1])
                    14 -> binding.tvTo.text = getString(R.string.test, 2, it[1])
                    15 -> binding.tvTo.text = getString(R.string.test, 3, it[1])
                    16 -> binding.tvTo.text = getString(R.string.test, 4, it[1])
                    17 -> binding.tvTo.text = getString(R.string.test, 5, it[1])
                    18 -> binding.tvTo.text = getString(R.string.test, 6, it[1])
                    19 -> binding.tvTo.text = getString(R.string.test, 7, it[1])
                    20 -> binding.tvTo.text = getString(R.string.test, 8, it[1])
                    21 -> binding.tvTo.text = getString(R.string.test, 9, it[1])
                    22 -> binding.tvTo.text = getString(R.string.test, 10, it[1])
                    23 -> binding.tvTo.text = getString(R.string.test, 11, it[1])
                    24 -> binding.tvTo.text = getString(R.string.test, 12, it[1])
                }
            } else {
                binding.tvTo.text = getString(R.string.test, it[0], it[1])
            }
        })
    }

    private fun setInfo() {
        dbViewModel.getInfo()
        dbViewModel.info.observe(viewLifecycleOwner, Observer {
            binding.tvHumidity.text = getString(R.string.info_hum, it.hum)
            binding.tvLum.text = getString(R.string.info_lum, it.lum)
            binding.tvTemp.text = getString(R.string.info_temp, it.temp)
        })
    }

    private fun navigateToTimePicker(time: String) {
        val action = DeviceFragmentDirections.actionDeviceFragmentToTimePickerFragment(time)
        findNavController().navigate(action)
    }
}