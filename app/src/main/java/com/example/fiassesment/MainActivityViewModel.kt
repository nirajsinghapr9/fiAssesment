package com.example.fiassesment

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.fiassesment.MainModel

class MainActivityViewModel : ViewModel() {
    var PAN = MutableLiveData<String>()
    var DateS = MutableLiveData<String>()
    private var userMutableLiveData: MutableLiveData<MainModel>? = null
    val user: MutableLiveData<MainModel>
        get() {
            if (userMutableLiveData == null) {
                userMutableLiveData = MutableLiveData()
            }
            return userMutableLiveData!!
        }

    fun onClick(view: View?) {
        val loginUser = PAN.value?.let { DateS.value?.let { it1 -> MainModel(it, it1) } }
        userMutableLiveData!!.value = loginUser
    }
}