package com.kagan.control_your_home.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import com.kagan.control_your_home.R
import com.kagan.control_your_home.databinding.FragmentRoomInsideBinding

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

    private var lightStatus: Any? = null
    private var fanStatus: Any? = null
    private var tvStatus: Any? = null
    private var motionSensorStatus: Any? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = FirebaseDatabase.getInstance()

        val ref = db.reference
        temp = ref.child("info").child("temp")
        hum = ref.child("info").child("hum")
        lum = ref.child("info").child("lum")

        light = ref.child("device").child("light")
        fan = ref.child("device").child("fan")
        tv = ref.child("device").child("tv")
        motionSensor = ref.child("device").child("motion_sensor")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRoomInsideBinding.bind(view)
        Log.d(TAG, TAG)

        setInfo()
        setDevice()
//        isOpenDevice()

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigateUp()
        }

        binding.ivBack.setOnClickListener {
            callback.handleOnBackPressed()
        }

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
            view.findNavController().navigate(R.id.action_roomInsideFragment_to_deviceFragment2)
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
            view.findNavController().navigate(R.id.action_roomInsideFragment_to_deviceFragment2)
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
            view.findNavController().navigate(R.id.action_roomInsideFragment_to_deviceFragment2)
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
            view.findNavController().navigate(R.id.action_roomInsideFragment_to_deviceFragment2)
            return@setOnLongClickListener true
        }
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