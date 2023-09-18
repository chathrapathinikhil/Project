package edu.fullerton.jd.workodoro


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView


class TaskAdapter(var taskList: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        val taskName: TextView = itemView.findViewById(R.id.taskName)
    }

    fun addTask(task: Task) {
        taskList.add(task)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ll1, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskAdapter.TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.taskName.text = task.taskName
        holder.checkBox.isChecked = task.isCompleted
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            task.isCompleted = isChecked
            if (isChecked) {
                val notificationManager = holder.itemView.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                // Check if the app has permission to show notifications
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = notificationManager.getNotificationChannel("Workodoro")
                    if (channel == null || channel.importance == NotificationManager.IMPORTANCE_NONE) {
                        // Prompt the user to grant notification permission
                        val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
                            .putExtra(Settings.EXTRA_CHANNEL_ID, "Workodoro")
                            .putExtra(Settings.EXTRA_APP_PACKAGE, holder.itemView.context.packageName)
                        holder.itemView.context.startActivity(intent)
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val name = "Workodoro Channel"
                    val importance = NotificationManager.IMPORTANCE_HIGH
                    val channel = NotificationChannel("Workodoro", name, importance)
                    val notificationManager = holder.itemView.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.createNotificationChannel(channel)
                }
                // Create and send the notification
                val notification = NotificationCompat.Builder(holder.itemView.context, "Workodoro")
                    .setContentTitle("Task Completed")
                    .setContentText(task.taskName)
                    .setSmallIcon(R.drawable.baseline_notifications_24)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build()

                val notificationId = System.currentTimeMillis().toInt()
                notificationManager.notify(notificationId, notification)
                taskList.remove(task)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}

