package com.capstone.project.testsuitmedia.view.first

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.capstone.project.testsuitmedia.databinding.ActivityFirstScreenBinding
import com.capstone.project.testsuitmedia.view.second.SecondScreenActivity


class FirstScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstScreenBinding
    private val viewModel: FirstScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkButton.setOnClickListener {
            val text = binding.palindromeInput.text.toString()
            viewModel.checkPalindrome(text)
        }

        binding.nextButton.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val intent = Intent(this, SecondScreenActivity::class.java)
            intent.putExtra("USER_NAME", name)
            startActivity(intent)
        }

        viewModel.palindromeResult.observe(this) { result ->
            showDialog(result)
        }
    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}