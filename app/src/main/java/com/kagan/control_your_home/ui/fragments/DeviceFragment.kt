package com.kagan.control_your_home.ui.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kagan.control_your_home.R
import com.kagan.control_your_home.databinding.FragmentDeviceBinding
import com.kagan.control_your_home.others.Constant.FROM
import com.kagan.control_your_home.others.Constant.TO
import com.kagan.control_your_home.others.DialogHelper
import com.kagan.control_your_home.others.FunctionConstant
import com.kagan.control_your_home.others.ScheduleTaskHelper
import com.kagan.control_your_home.viewmodel.DBViewModel
import com.kagan.control_your_home.viewmodel.TimeViewModel

class DeviceFragment : Fragment(R.layout.fragment_device) {

    val TAG = "DeviceFragment"
    lateinit var binding: FragmentDeviceBinding
    private val args: DeviceFragmentArgs by navArgs()
    private lateinit var dbViewModel: DBViewModel
    private lateinit var timeViewModel: TimeViewModel
    lateinit var dialogHelper: DialogHelper
    lateinit var schedule: ScheduleTaskHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbViewModel = ViewModelProvider(requireActivity()).get(DBViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDeviceBinding.bind(view)
        dialogHelper = DialogHelper(requireContext())
        timeViewModel = ViewModelProvider(requireActivity()).get(TimeViewModel::class.java)
        schedule = ScheduleTaskHelper(context)

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
            binding.tvFrom.text = FunctionConstant.simpleDateFormat(it)
            schedule.setAlarm(it)
            Log.d(TAG, "open: ${FunctionConstant.simpleDateFormat(it)}")
        })

        timeViewModel.endTime.observe(viewLifecycleOwner, {
            binding.tvFrom.text = FunctionConstant.simpleDateFormat(it)
        })
    }

    private fun setInfo() {
        dbViewModel.info.observe(viewLifecycleOwner, {
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