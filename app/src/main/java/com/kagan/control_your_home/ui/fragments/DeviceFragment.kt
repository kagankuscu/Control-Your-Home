package com.kagan.control_your_home.ui.fragments

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
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

    private val selectedDays = ArrayList<String>()
    private var checkDays = BooleanArray(7)

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
        restoreUIState()

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

        timeViewModel.selectedDays.observe(viewLifecycleOwner, {
            binding.tvDays.text = when (it.size) {
                0 -> "Nothing"
                7 -> "Everyday"
                else -> FunctionConstant.formatList(it)
            }
        })

        binding.ivApply.setOnClickListener {
            setAlarm()
        }
    }

    private fun open() {
        setAlpha()

        binding.cvFrom.setOnClickListener {
            navigateToTimePicker(FROM)
        }

        binding.cvFrom.setOnLongClickListener {
            Log.d(TAG, "open: long listener from")
            timeViewModel.startTime.value = null

            schedule.cancelAlarmOpen()
            return@setOnLongClickListener true
        }

        binding.cvTo.setOnClickListener {
            navigateToTimePicker(TO)
        }

        binding.cvTo.setOnLongClickListener {
            Log.d(TAG, "open: long listener to")
            timeViewModel.endTime.value = null

            schedule.cancelAlarmClose()
            return@setOnLongClickListener true
        }

        binding.flRepeat.setOnClickListener {
            Log.d(TAG, "onViewCreated: ")
            dialogShow()
        }
    }

    private fun dialogShow() {
        val repeat = dialogHelper.createDayDialog()

        repeat?.setMultiChoiceItems(
            R.array.days,
            checkDays,
            DialogInterface.OnMultiChoiceClickListener { _, which, isChecked ->
                if (isChecked) {
                    selectedDays.add(resources.getStringArray(R.array.days_short)[which])
                    timeViewModel.selectedDays.value = selectedDays
                    checkDays[which] = true
                    timeViewModel.checkDays.value = checkDays
                } else {
                    selectedDays.remove(resources.getStringArray(R.array.days_short)[which])
                    timeViewModel.selectedDays.value = selectedDays
                    checkDays[which] = false
                    timeViewModel.checkDays.value = checkDays
                }
            })
            ?.setNeutralButton("Reset", DialogInterface.OnClickListener { _, _ ->
                selectedDays.clear()
                timeViewModel.selectedDays.value = selectedDays

                for (i in checkDays.indices) {
                    checkDays[i] = false
                }
            })?.setPositiveButton("Apply", DialogInterface.OnClickListener { _, _ ->
                Log.d(TAG, "dialogShow: ${timeViewModel.selectedDays.value}")
            })?.show()
    }

    private fun close() {
        binding.cvFrom.alpha = 0.75F
        binding.cvTo.alpha = 0.75F
        binding.flRepeat.alpha = 0.75F

        binding.cvFrom.setOnClickListener(null)
        binding.cvTo.setOnClickListener(null)
        binding.flRepeat.setOnClickListener(null)

        schedule.cancelAlarmAll()
    }

    private fun setAlpha() {
        binding.cvFrom.alpha = 1F
        binding.cvTo.alpha = 1F
        binding.flRepeat.alpha = 1F
    }

    private fun setTime() {
        Log.d(TAG, "setTime: ")
        timeViewModel.startTime.observe(viewLifecycleOwner, {
            if (it == null) {
                binding.tvFrom.text = getString(R.string.set_time)
            } else {
                binding.tvFrom.text = FunctionConstant.simpleDateFormat(it)
                Log.d(TAG, "open: ${FunctionConstant.simpleDateFormat(it)}")
            }
        })

        timeViewModel.endTime.observe(viewLifecycleOwner, {
            if (it == null) {
                binding.tvTo.text = getString(R.string.set_time)
            } else {
                binding.tvTo.text = FunctionConstant.simpleDateFormat(it)
                Log.d(TAG, "close: ${FunctionConstant.simpleDateFormat(it)}")
            }
        })
    }

    private fun setAlarm() {
        timeViewModel.startTime.value?.let {
            schedule.setAlarm(it, true)
            Log.d(TAG, "setAlarm: inside Start time")
        }

        timeViewModel.endTime.value?.let {
            schedule.setAlarm(it, false)
            Log.d(TAG, "setAlarm: inside end time")
        }
        Log.d(TAG, "setAlarm: ")
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

    private fun saveUIState() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        with(sharedPref.edit()) {
            putBoolean(getString(R.string.btn_on_off_status_key), binding.sOnOff.isChecked)

            timeViewModel.startTime.value?.let {
                putLong(
                    getString(R.string.tv_start_time_key),
                    it
                )
            }

            timeViewModel.endTime.value?.let {
                putLong(
                    getString(R.string.tv_end_time_key),
                    it
                )
            }

            putString(
                getString(R.string.tv_days_days_key),
                binding.tvDays.text.toString()
            )

            putInt(getString(R.string.array_checkDays_size), checkDays.size)

            for (i in checkDays.indices) {
                putBoolean(getString(R.string.array_checkDays_value, i), checkDays[i])
            }

            putInt(getString(R.string.array_selected_day_size), selectedDays.size)

            for (i in selectedDays.indices) {
                putString(getString(R.string.array_selected_day_value, i), selectedDays[i])
            }

            commit()
        }
    }

    private fun restoreUIState() {
        Log.d(TAG, "restoreUIState: ")
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        binding.sOnOff.isChecked =
            sharedPref.getBoolean(getString(R.string.btn_on_off_status_key), false)

        if (binding.sOnOff.isChecked) {
            open()
        }

        binding.tvFrom.text =
            FunctionConstant.simpleDateFormat(
                sharedPref.getLong(
                    getString(R.string.tv_start_time_key),
                    0
                )
            )
        binding.tvTo.text =
            FunctionConstant.simpleDateFormat(
                sharedPref.getLong(
                    getString(R.string.tv_end_time_key),
                    0
                )
            )

        binding.tvDays.text = sharedPref.getString(getString(R.string.tv_days_days_key), "")

        val arraySize = sharedPref.getInt(getString(R.string.array_checkDays_size), 0)

        for (i in 0 until arraySize) {
            checkDays[i] =
                sharedPref.getBoolean(getString(R.string.array_checkDays_value, i), false)
        }

        val selectedDaysSize = sharedPref.getInt(getString(R.string.array_selected_day_size), 0)

        for (i in 0 until selectedDaysSize) {
            selectedDays.add(
                sharedPref.getString(getString(R.string.array_selected_day_value, i), "").toString()
            )
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: saving UI States")
        saveUIState()
    }
}