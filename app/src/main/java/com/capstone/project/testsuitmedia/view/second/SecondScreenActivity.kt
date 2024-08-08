package com.capstone.project.testsuitmedia.view.second

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.project.testsuitmedia.R
import com.capstone.project.testsuitmedia.databinding.ActivitySecondScreenBinding
import com.capstone.project.testsuitmedia.view.third.ThirdScreenActivity

class SecondScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondScreenBinding
    private val viewModel: SecondScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        val userName = intent.getStringExtra("USER_NAME")
        viewModel.setUserName(userName ?: "")

        viewModel.userName.observe(this) { name ->
            binding.userNameText.text = name
        }

        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val setUserName = result.data?.getStringExtra("SELECTED_USER_NAME")
                    setUserName?.let {
                        viewModel.setUserName(it)
                    }
                }
            }

        binding.chooseUserButton.setOnClickListener {
            val intent = Intent(this, ThirdScreenActivity::class.java)
            startForResult.launch(intent)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.materialBarDetail)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = getString(R.string.str_second_screen)
        }
        binding.materialBarDetail.setNavigationOnClickListener { onBackPressed() }
    }
}
