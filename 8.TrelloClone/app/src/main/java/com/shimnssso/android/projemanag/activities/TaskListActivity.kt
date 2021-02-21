package com.shimnssso.android.projemanag.activities

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.shimnssso.android.projemanag.R
import com.shimnssso.android.projemanag.adapters.TaskListItemsAdapter
import com.shimnssso.android.projemanag.databinding.ActivityTaskListBinding
import com.shimnssso.android.projemanag.firebase.FirestoreClass
import com.shimnssso.android.projemanag.models.Board
import com.shimnssso.android.projemanag.models.Task
import com.shimnssso.android.projemanag.utils.Constants

class TaskListActivity : BaseActivity() {
    private lateinit var binding: ActivityTaskListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var boardDocumentId = ""
        if (intent.hasExtra(Constants.DOCUMENT_ID)) {
            boardDocumentId = intent.getStringExtra(Constants.DOCUMENT_ID)!!
        }

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getBoardDetails(this@TaskListActivity, boardDocumentId)
    }

    /**
     * A function to setup action bar
     */
    private fun setupActionBar(title: String) {

        setSupportActionBar(binding.toolbarTaskListActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = title
        }

        binding.toolbarTaskListActivity.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * A function to get the result of Board Detail.
     */
    fun boardDetails(board: Board) {

        hideProgressDialog()

        // Call the function to setup action bar.
        setupActionBar(board.name)

        // Here we are appending an item view for adding a list task list for the board.
        val addTaskList = Task(resources.getString(R.string.add_list))
        board.taskList.add(addTaskList)

        binding.rvTaskList.layoutManager =
            LinearLayoutManager(this@TaskListActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvTaskList.setHasFixedSize(true)

        // Create an instance of TaskListItemsAdapter and pass the task list to it.
        val adapter = TaskListItemsAdapter(this@TaskListActivity, board.taskList)
        binding.rvTaskList.adapter = adapter // Attach the adapter to the recyclerView.
    }
}