package edu.fullerton.jd.workodoro

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.fullerton.jd.workodoro.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment4.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment4 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var taskList: MutableList<Task> = ArrayList()
    var taskAdapter: TaskAdapter = TaskAdapter(taskList)
    lateinit var viewModel1:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val taskSet = sharedPref.getStringSet("taskList", emptySet()) ?: emptySet()
        val tasks = taskSet.map { Task(it, false) }
//        taskAdapter.taskList = tasks.toMutableList()

        taskAdapter.taskList.addAll(tasks)
    }

    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_blank4, container, false)
        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        val repository = Repository()
        val dataList = mutableListOf<QuoteModel>()
//        val myAdapter = PostAdapter(dataList)
//        val btn: Button = view.findViewById(R.id.button)
//        recyclerView.adapter = myAdapter
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel1= ViewModelProvider(this,viewModelFactory)[MainViewModel::class.java]
        val handler = Handler()
        val delay = 15000L // 15 seconds in milliseconds

// Define a runnable to generate a random number and execute some action
        val runnable = object : Runnable {
            override fun run() {
                val random = (0..100).random() // generate a random number between 0 and 10
                Log.d("RandomNumber", "Generated: $random")
//                val text: TextView = view.findViewById(R.id.editTextTextPersonName4)
                val text1: TextView = view.findViewById(R.id.textView4)
//                val num: Int = Integer.parseInt(text.text.toString())
                viewModel1.getQuote(random)
                viewModel1.myResponse2.observe(viewLifecycleOwner, Observer { response ->
                    if(response.isSuccessful){
                        Log.d("Response",response.body()?.id.toString())
                        Log.d("Response",response.code().toString())
                        dataList.addAll(listOf(response.body()!!))
                        text1.text=response.body()?.quote!!
//                    myAdapter.notifyDataSetChanged()
                        response.body()?.author?.let { Log.d("Response", it) }
                        response.body()?.quote?.let { Log.d("Response", it) }
                    }
                    else{
                        Log.d("Response",response.errorBody().toString())
                    }
                })
                // execute some action with the random number here
                handler.postDelayed(this, delay) // post the same runnable again after the delay
            }
        }

// Start the runnable
        handler.postDelayed(runnable, delay)

        val newResource = PostModel(
            1,
            1,
            "I am a stranger",
            "https://jsonplaceholder.typicode.com/")
        val call1 = serviceGenerator.createPost(1,1,"I am the King","https://jsonplaceholder.typicode.com/photos/placeholderchanged")
        call1.enqueue(object : Callback<PostModel> {
            override fun onResponse(call: Call<PostModel>, response: Response<PostModel>) {
                if (response.isSuccessful) {
                    val createdResource = response.body()
                    Log.d("debug", "Created resource: $createdResource,${response.code()}")
                    Log.i("stanley", "Response size: ${dataList.size}")

                } else {
                    Log.e("error", "Error creating resource: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PostModel>, t: Throwable) {
                Log.e("error", "Error creating resource: ${t.message}")
            }
        })

        val updatedPost = PostModel(1,1," Updated Post","Hi how r uu")
        val service = ServiceGenerator.buildService(ApiService::class.java)
        val call2 = service.updatePost(1,updatedPost)
        call2.enqueue(object : Callback<PostModel> {
            override fun onResponse(call: Call<PostModel>, response: Response<PostModel>) {
                if (response.isSuccessful) {
                    val updatedPost = response.body()
                    Log.d("debug", "Updated post: $updatedPost, ${response.code()}")
                    Log.i("stanley", "Response size: ${dataList.size}")
                } else {
                    Log.e("error", "Error updating post: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<PostModel>, t: Throwable) {
                Log.e("error", "Error updating post: ${t.message}")
            }
        })

//        recyclerView.layoutManager = LinearLayoutManager(context)
//        val taskList = ArrayList<String>()
        val taskChecked = ArrayList<Boolean>()
        var recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        var fab: FloatingActionButton = view.findViewById(R.id.floating_button)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = taskAdapter

        fab.setOnClickListener {
        // Show dialog to add a new task
            showAddTaskDialog()
        }
        return view
    }
    private fun showAddTaskDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Add a new task")

        val input = EditText(context)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("Save") { dialog, which ->
            val taskName = input.text.toString().trim()
            val task = Task(taskName, false)
            taskList.add(task)
            taskAdapter.notifyItemInserted(taskList.size - 1)
        }

//        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.setPositiveButton("Save") { dialog, which ->
            val taskName = input.text.toString().trim()
            val task = Task(taskName, false)
            taskAdapter.taskList.add(task)
            taskAdapter.notifyDataSetChanged()
        }

        builder.show()
    }



    override fun onPause() {
        super.onPause()
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putStringSet("taskList", taskList.map { it.taskName }.toSet())
            apply()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment4.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BlankFragment4().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

