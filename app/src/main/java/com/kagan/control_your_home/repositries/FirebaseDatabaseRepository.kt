package com.kagan.control_your_home.repositries

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.kagan.control_your_home.models.Info
import com.kagan.control_your_home.models.Room
import com.kagan.control_your_home.others.Constant.HUM
import com.kagan.control_your_home.others.Constant.INFO
import com.kagan.control_your_home.others.Constant.LUM
import com.kagan.control_your_home.others.Constant.ROOMS
import com.kagan.control_your_home.others.Constant.TEMP
import kotlinx.coroutines.tasks.await


class FirebaseDatabaseRepository {
    val TAG = "FirebaseDatabaseRepo"
    private val infoCollectionRef = Firebase.firestore.collection(INFO)
    private val roomsCollectionRef = Firebase.firestore.collection(ROOMS)

    fun getInfo(): MutableLiveData<Info> {
        val info = MutableLiveData<Info>()

        try {
            infoCollectionRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val hum = document.get(HUM) as Long
                        val lum = document.get(LUM) as Long
                        val temp = document.get(TEMP) as Long
                        info.value = Info(temp, hum, lum)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("DBRepo", "getInfo: ${e.message}")
        }

        return info
    }

    fun getRealTimeInfo(): MutableLiveData<Info> {
        val info = MutableLiveData<Info>()

        infoCollectionRef.addSnapshotListener { value, error ->
            error?.let {
                Log.d("TAG", "getRealTimeInfo: ${error.message}")
                return@addSnapshotListener
            }

            value?.let {
                for (document in it) {
                    info.value = document.toObject(Info::class.java)
                }
            }
        }

        return info
    }

    fun getRoom(room: String): MutableLiveData<Room> {
        val roomDevices = MutableLiveData<Room>()

        roomsCollectionRef.document(room).addSnapshotListener { value, error ->
            error?.let {
                Log.d(TAG, "getRealTimeInfo: ${error.message}")
                return@addSnapshotListener
            }

            value?.let {
                roomDevices.value = it.toObject<Room>()
            }
        }

        return roomDevices
    }

    fun updateValue(room: String, map: Map<String, Boolean>) {
        roomsCollectionRef.document(room).set(
            map, SetOptions.merge()
        )
    }
}