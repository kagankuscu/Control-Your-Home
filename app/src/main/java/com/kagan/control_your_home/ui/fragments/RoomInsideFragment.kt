package com.kagan.control_your_home.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kagan.control_your_home.R
import com.kagan.control_your_home.databinding.FragmentRoomInsideBinding
import com.kagan.control_your_home.others.Constant.BED_ROOM
import com.kagan.control_your_home.others.Constant.FAN
import com.kagan.control_your_home.others.Constant.GUEST_ROOM
import com.kagan.control_your_home.others.Constant.KITCHEN
import com.kagan.control_your_home.others.Constant.LAMP
import com.kagan.control_your_home.others.Constant.LIVING_ROOM
import com.kagan.control_your_home.others.Constant.MOTION_SENSOR
import com.kagan.control_your_home.others.Constant.TV
import com.kagan.control_your_home.viewmodel.DBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomInsideFragment : Fragment(R.layout.fragment_room_inside) {

    val TAG = "RoomInsideFragment"

    lateinit var binding: FragmentRoomInsideBinding
    private val args: RoomInsideFragmentArgs by navArgs()
    private val dbViewModel: DBViewModel by viewModels()

    private var lampStatus: Any? = null
    private var fanStatus: Any? = null
    private var tvStatus: Any? = null
    private var motionSensorStatus: Any? = null
    private var roomName: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRoomInsideBinding.bind(view)
        Log.d(TAG, TAG)

        setInfo()
        setRoomDevice()

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigateUp()
        }

        binding.ivBack.setOnClickListener {
            callback.handleOnBackPressed()
        }

        binding.tvName.text = args.nameRoom

        binding.cvLamp.setOnClickListener {
            Log.d(TAG, "onViewCreated: ghd")
            updateValue(lampStatus, LAMP)
        }

        binding.cvLamp.setOnLongClickListener {
            navigate(getString(R.string.lamp))
            return@setOnLongClickListener true
        }

        binding.cvFan.setOnClickListener {
            Log.d(TAG, "onViewCreated: cvfan")
            updateValue(fanStatus, FAN)
        }

        binding.cvFan.setOnLongClickListener {
            navigate(getString(R.string.fan))
            return@setOnLongClickListener true
        }

        binding.cvTV.setOnClickListener {
            Log.d(TAG, "onViewCreated: cvTV")
            updateValue(tvStatus, TV)
        }

        binding.cvTV.setOnLongClickListener {
            navigate(getString(R.string.TV))
            return@setOnLongClickListener true
        }

        binding.cvMotionSensor.setOnClickListener {
            Log.d(TAG, "onViewCreated: cvMotion")
            updateValue(motionSensorStatus, MOTION_SENSOR)
        }

        binding.cvMotionSensor.setOnLongClickListener {
            navigate(getString(R.string.motion_sensor))
            return@setOnLongClickListener true
        }
    }

    private fun updateValue(it: Any?, deviceRef: String) {
        val map = mutableMapOf<String, Boolean>()
        map[deviceRef] = (it as Boolean).not()
        dbViewModel.updateValue(roomName, map)
    }

    private fun setRoomDevice() {
        when (args.nameRoom) {
            getString(R.string.living_room) -> roomName = LIVING_ROOM
            getString(R.string.kitchen) -> roomName = KITCHEN
            getString(R.string.bed_room) -> roomName = BED_ROOM
            getString(R.string.guest_room) -> roomName = GUEST_ROOM
        }

        dbViewModel.getRoom(roomName)
        dbViewModel.roomDevice.observe(viewLifecycleOwner, Observer {
            lampStatus = it.lamp
            fanStatus = it.fan
            motionSensorStatus = it.motionSensor
            tvStatus = it.tv

            isDeviceOpen()
        })
    }

    private fun isDeviceOpen() {
        if (lampStatus as Boolean) {
            binding.tvLampCircle.setBackgroundResource(R.drawable.info_open_device)
        } else {
            binding.tvLampCircle.setBackgroundResource(R.drawable.info_close_device)
        }

        if (fanStatus as Boolean) {
            binding.tvFanCircle.setBackgroundResource(R.drawable.info_open_device)
        } else {
            binding.tvFanCircle.setBackgroundResource(R.drawable.info_close_device)
        }

        if (motionSensorStatus as Boolean) {
            binding.tvMotionSensorCircle.setBackgroundResource(R.drawable.info_open_device)
        } else {
            binding.tvMotionSensorCircle.setBackgroundResource(R.drawable.info_close_device)
        }

        if (tvStatus as Boolean) {
            binding.tvTVCircle.setBackgroundResource(R.drawable.info_open_device)
        } else {
            binding.tvTVCircle.setBackgroundResource(R.drawable.info_close_device)
        }
    }

    private fun navigate(value: String) {
        val action = RoomInsideFragmentDirections.actionRoomInsideFragmentToDeviceFragment2(value)
        findNavController().navigate(action)
    }

    private fun setInfo() {
        dbViewModel.getInfo()
        dbViewModel.info.observe(viewLifecycleOwner, Observer {
            binding.tvHumidity.text = getString(R.string.info_hum, it.hum)
            binding.tvLum.text = getString(R.string.info_lum, it.lum)
            binding.tvTemp.text = getString(R.string.info_temp, it.temp)
        })
    }
}