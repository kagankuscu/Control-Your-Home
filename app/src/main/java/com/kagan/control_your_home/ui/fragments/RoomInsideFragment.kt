package com.kagan.control_your_home.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.*
import com.kagan.control_your_home.R
import com.kagan.control_your_home.databinding.FragmentRoomInsideBinding
import com.kagan.control_your_home.others.Constant.BED_ROOM
import com.kagan.control_your_home.others.Constant.FAN
import com.kagan.control_your_home.others.Constant.GUEST_ROOM
import com.kagan.control_your_home.others.Constant.HUM
import com.kagan.control_your_home.others.Constant.INFO
import com.kagan.control_your_home.others.Constant.KITCHEN
import com.kagan.control_your_home.others.Constant.LAMP
import com.kagan.control_your_home.others.Constant.LIVING_ROOM
import com.kagan.control_your_home.others.Constant.LUM
import com.kagan.control_your_home.others.Constant.MOTION_SENSOR
import com.kagan.control_your_home.others.Constant.ROOMS
import com.kagan.control_your_home.others.Constant.TEMP
import com.kagan.control_your_home.others.Constant.TV

class RoomInsideFragment : Fragment(R.layout.fragment_room_inside) {

    val TAG = "RoomInsideFragment"
    lateinit var db: FirebaseDatabase
    lateinit var temp: DatabaseReference
    lateinit var hum: DatabaseReference
    lateinit var lum: DatabaseReference
    lateinit var light: DatabaseReference
    lateinit var fan: DatabaseReference
    lateinit var tv: DatabaseReference
    lateinit var motionSensor: DatabaseReference
    lateinit var binding: FragmentRoomInsideBinding
    private val args: RoomInsideFragmentArgs by navArgs()

    private var lightStatus: Any? = null
    private var fanStatus: Any? = null
    private var tvStatus: Any? = null
    private var motionSensorStatus: Any? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = FirebaseDatabase.getInstance()

        val ref = db.reference
        temp = ref.child(INFO).child(TEMP)
        hum = ref.child(INFO).child(HUM)
        lum = ref.child(INFO).child(LUM)

        setRoomDevice(ref)
    }

    private fun setRoomDevice(ref: DatabaseReference) {
        when (args.nameRoom) {
            getString(R.string.living_room) -> {
                light = ref.child(ROOMS).child(LIVING_ROOM).child(LAMP)
                fan = ref.child(ROOMS).child(LIVING_ROOM).child(FAN)
                tv = ref.child(ROOMS).child(LIVING_ROOM).child(TV)
                motionSensor = ref.child(ROOMS).child(LIVING_ROOM).child(MOTION_SENSOR)
            }
            getString(R.string.kitchen) -> {
                light = ref.child(ROOMS).child(KITCHEN).child(LAMP)
                fan = ref.child(ROOMS).child(KITCHEN).child(FAN)
                tv = ref.child(ROOMS).child(KITCHEN).child(TV)
                motionSensor = ref.child(ROOMS).child(KITCHEN).child(MOTION_SENSOR)
            }
            getString(R.string.bed_room) -> {
                light = ref.child(ROOMS).child(BED_ROOM).child(LAMP)
                fan = ref.child(ROOMS).child(BED_ROOM).child(FAN)
                tv = ref.child(ROOMS).child(BED_ROOM).child(TV)
                motionSensor = ref.child(ROOMS).child(BED_ROOM).child(MOTION_SENSOR)
            }
            getString(R.string.guest_room) -> {
                light = ref.child(ROOMS).child(GUEST_ROOM).child(LAMP)
                fan = ref.child(ROOMS).child(GUEST_ROOM).child(FAN)
                tv = ref.child(ROOMS).child(GUEST_ROOM).child(TV)
                motionSensor = ref.child(ROOMS).child(GUEST_ROOM).child(MOTION_SENSOR)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRoomInsideBinding.bind(view)
        Log.d(TAG, TAG)

        setInfo()
        setDevice()

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigateUp()
        }

        binding.ivBack.setOnClickListener {
            callback.handleOnBackPressed()
        }

        binding.tvName.text = args.nameRoom

        binding.cvLamp.setOnClickListener {
            Log.d(TAG, "onViewCreated: ghd")
            lightStatus.let {
                if (it as Boolean) {
                    light.setValue(false)
                } else {
                    light.setValue(true)
                }
            }
        }

        binding.cvLamp.setOnLongClickListener {
            navigate(getString(R.string.lamp))
            return@setOnLongClickListener true
        }

        binding.cvFan.setOnClickListener {
            Log.d(TAG, "onViewCreated: cvfan")
            fanStatus.let {
                if (it as Boolean) {
                    fan.setValue(false)
                } else {
                    fan.setValue(true)
                }
            }
        }

        binding.cvFan.setOnLongClickListener {
            navigate(getString(R.string.fan))
            return@setOnLongClickListener true
        }

        binding.cvTV.setOnClickListener {
            Log.d(TAG, "onViewCreated: cvTV")

            tvStatus.let {
                if (it as Boolean) {
                    tv.setValue(false)
                } else {
                    tv.setValue(true)
                }
            }
        }

        binding.cvTV.setOnLongClickListener {
            navigate(getString(R.string.TV))
            return@setOnLongClickListener true
        }

        binding.cvMotionSensor.setOnClickListener {
            Log.d(TAG, "onViewCreated: cvMotion")

            motionSensorStatus.let {
                if (it as Boolean) {
                    motionSensor.setValue(false)
                } else {
                    motionSensor.setValue(true)
                }
            }
        }

        binding.cvMotionSensor.setOnLongClickListener {
            navigate(getString(R.string.motion_sensor))
            return@setOnLongClickListener true
        }
    }

    private fun navigate(value: String) {
        val action = RoomInsideFragmentDirections.actionRoomInsideFragmentToDeviceFragment2(value)
        findNavController().navigate(action)
    }

    private fun setInfo() {
        temp.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.tvTemp.text = getString(R.string.info_temp, snapshot.value)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: ", error.toException())
            }
        })

        lum.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.tvLum.text = getString(R.string.info_lum, snapshot.value)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: ", error.toException())
            }
        })

        hum.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.tvHumidity.text = getString(R.string.info_hum, snapshot.value)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: ", error.toException())
            }
        })
    }

    private fun setDevice() {
        light.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                lightStatus = snapshot.value

                if (lightStatus as Boolean)
                    binding.tvLampCircle.setBackgroundResource(R.drawable.info_open_device)
                else
                    binding.tvLampCircle.setBackgroundResource(R.drawable.info_close_device)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: ", error.toException())
            }
        })

        fan.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                fanStatus = snapshot.value

                if (fanStatus as Boolean)
                    binding.tvFanCircle.setBackgroundResource(R.drawable.info_open_device)
                else
                    binding.tvFanCircle.setBackgroundResource(R.drawable.info_close_device)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: ", error.toException())
            }
        })

        tv.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tvStatus = snapshot.value

                if (tvStatus as Boolean)
                    binding.tvTVCircle.setBackgroundResource(R.drawable.info_open_device)
                else
                    binding.tvTVCircle.setBackgroundResource(R.drawable.info_close_device)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: ", error.toException())
            }
        })

        motionSensor.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                motionSensorStatus = snapshot.value

                if (motionSensorStatus as Boolean)
                    binding.tvMotionSensorCircle.setBackgroundResource(R.drawable.info_open_device)
                else
                    binding.tvMotionSensorCircle.setBackgroundResource(R.drawable.info_close_device)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: ", error.toException())
            }
        })
    }
}