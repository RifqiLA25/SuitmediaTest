package com.capstone.project.testsuitmedia.view.third

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.project.testsuitmedia.R
import com.capstone.project.testsuitmedia.adapter.UserAdapter
import com.capstone.project.testsuitmedia.data.remote.response.DataItem
import com.capstone.project.testsuitmedia.databinding.ActivityThirdScreenBinding

class ThirdScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdScreenBinding
    private val viewModel: ThirdScreenViewModel by viewModels()

    private lateinit var userAdapter: UserAdapter
    private var page = 1
    private val perPage = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupSwipeRefreshLayout()

        viewModel.users.observe(this) { users ->
            userAdapter.submitList(users)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }

        viewModel.isEmpty.observe(this) { isEmpty ->
            // Handle empty state (e.g., show/hide empty state view)
        }

        loadUsers()

        setupScrollListener()
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter(object : UserAdapter.OnItemClickListener {
            override fun onItemClick(user: DataItem) {
                val resultIntent = Intent().apply {
                    putExtra("SELECTED_USER_NAME", "${user.firstName} ${user.lastName}")
                }
                setResult(RESULT_OK, resultIntent)
                finish() // Return to SecondScreenActivity
            }
        })

        binding.userListView.apply {
            layoutManager = LinearLayoutManager(this@ThirdScreenActivity)
            adapter = userAdapter
        }
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            page = 1
            loadUsers()
        }
    }

    private fun loadUsers() {
        viewModel.loadUsers(page, perPage)
    }

    private fun setupScrollListener() {
        binding.userListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    page++
                    loadUsers()
                }
            }
        })
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.materialBarDetail)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = getString(R.string.str_third_screen)
        }
        binding.materialBarDetail.setNavigationOnClickListener { onBackPressed() }
    }
}
