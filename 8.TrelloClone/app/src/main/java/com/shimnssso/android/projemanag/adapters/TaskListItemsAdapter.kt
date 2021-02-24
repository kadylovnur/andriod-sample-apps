package com.shimnssso.android.projemanag.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
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
            val isLastItem = position == list.size - 1
            if (isLastItem) {
                holder.itemBinding.tvAddTaskList.visibility = View.VISIBLE
                holder.itemBinding.llTaskItem.visibility = View.GONE
            } else {
                holder.itemBinding.tvAddTaskList.visibility = View.GONE
                holder.itemBinding.llTaskItem.visibility = View.VISIBLE
            }

            holder.itemBinding.tvTaskListTitle.text = model.title

            holder.itemBinding.tvAddTaskList.setOnClickListener {

                holder.itemBinding.tvAddTaskList.visibility = View.GONE
                holder.itemBinding.cvAddTaskListName.visibility = View.VISIBLE
            }

            holder.itemBinding.ibCloseListName.setOnClickListener {
                holder.itemBinding.tvAddTaskList.visibility = View.VISIBLE
                holder.itemBinding.cvAddTaskListName.visibility = View.GONE
            }

            holder.itemBinding.ibDoneListName.setOnClickListener {
                val listName = holder.itemBinding.etTaskListName.text.toString()

                if (listName.isNotEmpty()) {
                    // Here we check the context is an instance of the TaskListActivity.
                    if (context is TaskListActivity) {
                        context.createTaskList(listName)
                    }
                } else {
                    Toast.makeText(context, "Please Enter List Name.", Toast.LENGTH_SHORT).show()
                }
            }

            holder.itemBinding.ibEditListName.setOnClickListener {
                holder.itemBinding.etEditTaskListName.setText(model.title) // Set the existing title
                holder.itemBinding.llTitleView.visibility = View.GONE
                holder.itemBinding.cvEditTaskListName.visibility = View.VISIBLE
            }

            holder.itemBinding.ibCloseEditableView.setOnClickListener {
                holder.itemBinding.llTitleView.visibility = View.VISIBLE
                holder.itemBinding.cvEditTaskListName.visibility = View.GONE
            }

            holder.itemBinding.ibDoneEditListName.setOnClickListener {
                val listName = holder.itemBinding.etEditTaskListName.text.toString()

                if (listName.isNotEmpty()) {
                    if (context is TaskListActivity) {
                        context.updateTaskList(position, listName, model)
                    }
                } else {
                    Toast.makeText(context, "Please Enter List Name.", Toast.LENGTH_SHORT).show()
                }
            }

            holder.itemBinding.ibDeleteList.setOnClickListener {
                alertDialogForDeleteList(position, model.title)
            }

            holder.itemBinding.tvAddCard.setOnClickListener {

                holder.itemBinding.tvAddCard.visibility = View.GONE
                holder.itemBinding.cvAddCard.visibility = View.VISIBLE

                holder.itemBinding.ibCloseCardName.setOnClickListener {
                    holder.itemBinding.tvAddCard.visibility = View.VISIBLE
                    holder.itemBinding.cvAddCard.visibility = View.GONE
                }

                holder.itemBinding.ibDoneCardName.setOnClickListener {

                    val cardName = holder.itemBinding.etCardName.text.toString()

                    if (cardName.isNotEmpty()) {
                        if (context is TaskListActivity) {
                            context.addCardToTaskList(position, cardName)
                        }
                    } else {
                        Toast.makeText(context, "Please Enter Card Detail.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

            holder.itemBinding.rvCardList.layoutManager = LinearLayoutManager(context)
            holder.itemBinding.rvCardList.setHasFixedSize(true)

            val adapter = CardListItemsAdapter(context, model.cards)
            holder.itemBinding.rvCardList.adapter = adapter

            adapter.setOnClickListener(object :
                CardListItemsAdapter.OnClickListener {
                override fun onClick(cardPosition: Int) {

                    if (context is TaskListActivity) {
                        context.cardDetails(position, cardPosition)
                    }
                }
            })
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
     * Method is used to show the Alert Dialog for deleting the task list.
     */
    fun alertDialogForDeleteList(position: Int, title: String) {
        val builder = AlertDialog.Builder(context)
        //set title for alert dialog
        builder.setTitle("Alert")
        //set message for alert dialog
        builder.setMessage("Are you sure you want to delete $title.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            dialogInterface.dismiss() // Dialog will be dismissed

            if (context is TaskListActivity) {
                context.deleteTaskList(position)
            }
        }

        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false) // Will not allow user to cancel after clicking on remaining screen area.
        alertDialog.show()  // show the dialog to UI
    }


    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    private class MyViewHolder(val itemBinding: ItemTaskBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
    }
}