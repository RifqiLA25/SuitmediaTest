package com.capstone.project.testsuitmedia.view.first

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirstScreenViewModel : ViewModel() {

    private val _palindromeResult = MutableLiveData<String>()
    val palindromeResult: LiveData<String> = _palindromeResult

    fun checkPalindrome(text: String) {
        if (isPalindrome(text)) {
            _palindromeResult.value = "isPalindrome"
        } else {
            _palindromeResult.value = "not palindrome"
        }
    }

    private fun isPalindrome(str: String): Boolean {
        val cleaned = str.replace(Regex("[^A-Za-z0-9]"), "").toLowerCase()
        return cleaned == cleaned.reversed()
    }
}