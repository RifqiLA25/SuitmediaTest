package com.capstone.project.testsuitmedia.view.third

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.project.testsuitmedia.data.remote.response.DataItem
import com.capstone.project.testsuitmedia.data.remote.response.UserResponse
import com.capstone.project.testsuitmedia.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdScreenViewModel : ViewModel() {

    private val _users = MutableLiveData<List<DataItem>>()
    val users: LiveData<List<DataItem>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val _selectedUserName = MutableLiveData<String>()
    val selectedUserName: LiveData<String> = _selectedUserName

    fun loadUsers(page: Int, perPage: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(page, perPage)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val userList = response.body()?.data
                    if (userList.isNullOrEmpty()) {
                        _isEmpty.value = true
                    } else {
                        _isEmpty.value = false
                        _users.value = userList ?: emptyList()
                    }
                } else {
                    _isEmpty.value = true
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                _isEmpty.value = true
            }
        })
    }

    fun selectUser(userName: String) {
        _selectedUserName.value = userName
    }
}
