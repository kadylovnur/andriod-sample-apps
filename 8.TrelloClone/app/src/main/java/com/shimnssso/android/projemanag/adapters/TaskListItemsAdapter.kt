package com.shimnssso.android.projemanag.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.shimnssso.android.projemanag.activities.TaskListActivity
import com.shimnssso.android.projemanag.databinding.ItemTaskBinding
import com.shimnssso.android.projemanag.models.Task

open class TaskListItemsAdapter(
    private val context: Context,
    private var list: ArrayList<Task>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val itemBinding =
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val view = itemBinding.root
        // Here the layout params are converted dynamically according to the screen size as width is 70% and height is wrap_content.
        val layoutParams = LinearLayout.LayoutParams(
            (parent.width * 0.7).toInt(),
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        // Here the dynamic margins are applied to the view.
        layoutParams.setMargins((15.toDp()).toPx(), 0, (40.toDp()).toPx(), 0)
        view.layoutParams = layoutParams

        return MyViewHolder(itemBinding)
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            holder.bind(position == list.size - 1, model, context)
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * A function to get density pixel from pixel
     */
    private fun Int.toDp(): Int =
        (this / Resources.getSystem().displayMetrics.density).toInt()

    /**
     * A function to get pixel from density pixel
     */
    private fun Int.toPx(): Int =
        (this * Resources.getSystem().displayMetrics.density).toInt()

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    private class MyViewHolder(private val itemBinding: ItemTaskBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(isLastItem: Boolean, model: Task, context: Context) {
            if (isLastItem) {
                itemBinding.tvAddTaskList.visibility = View.VISIBLE
                itemBinding.llTaskItem.visibility = View.GONE
            } else {
                itemBinding.tvAddTaskList.visibility = View.GONE
                itemBinding.llTaskItem.visibility = View.VISIBLE
            }

            itemBinding.tvTaskListTitle.text = model.title

            itemBinding.tvAddTaskList.setOnClickListener {

                itemBinding.tvAddTaskList.visibility = View.GONE
                itemBinding.cvAddTaskListName.visibility = View.VISIBLE
            }

            itemBinding.ibCloseListName.setOnClickListener {
                itemBinding.tvAddTaskList.visibility = View.VISIBLE
                itemBinding.cvAddTaskListName.visibility = View.GONE
            }

            itemBinding.ibDoneListName.setOnClickListener {
                val listName = itemBinding.etTaskListName.text.toString()

                if (listName.isNotEmpty()) {
                    // Here we check the context is an instance of the TaskListActivity.
                    if (context is TaskListActivity) {
                        context.createTaskList(listName)
                    }
                } else {
                    Toast.makeText(context, "Please Enter List Name.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}