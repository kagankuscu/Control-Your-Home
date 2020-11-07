package com.kagan.control_your_home.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import com.kagan.control_your_home.R
import com.kagan.control_your_home.databinding.FragmentDeviceBinding

class DeviceFragment : Fragment(R.layout.fragment_device) {

    val TAG = "DeviceFragment"
    lateinit var db: FirebaseDatabase
    lateinit var temp: DatabaseReference
    lateinit var hum: DatabaseReference
    lateinit var lum: DatabaseReference
    lateinit var binding: FragmentDeviceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = FirebaseDatabase.getInstance()

        val ref = db.reference
        temp = ref.child("info").child("temp")
        hum = ref.child("info").child("hum")
        lum = ref.child("info").child("lum")
    }

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