package com.kagan.control_your_home.repositries

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kagan.control_your_home.others.Constant.INFO


class FirebaseDatabaseRepository {

    private val infoCollectionRef = Firebase.firestore.collection(INFO)
}