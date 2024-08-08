package com.capstone.project.testsuitmedia.view.second

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SecondScreenViewModel : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _selectedUserName = MutableLiveData<String>()
    val selectedUserName: LiveData<String> = _selectedUserName

    fun setUserName(name: String) {
        _userName.value = name
    }

    fun setSelectedUserName(selectedName: String) {
        _selectedUserName.value = selectedName
    }
}
