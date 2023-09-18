package edu.fullerton.jd.workodoro

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.fullerton.jd.workodoro.repository.Repository

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var viewModel:MainViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment5.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment5 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_blank5, container, false)

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("stanley", "I entered the code")
//        var recyclerView: RecyclerView = view.findViewById(R.id.recycleview1)
        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        val repository = Repository()
        val dataList = mutableListOf<QuoteModel>()
//        val myAdapter = PostAdapter(dataList)
        val btn: Button = view.findViewById(R.id.button)
//        recyclerView.adapter = myAdapter
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel= ViewModelProvider(this,viewModelFactory)[MainViewModel::class.java]


        val handler = Handler()
        val delay = 5000L // 5 seconds in milliseconds

// Define a runnable to generate a random number and execute some action
        val runnable = object : Runnable {
            override fun run() {
                val random = (0..100).random() // generate a random number between 0 and 10
                Log.d("RandomNumber", "Generated: $random")
                val text: TextView = view.findViewById(R.id.editTextTextPersonName4)
                val text1: TextView = view.findViewById(R.id.recycleview1)
//                val num: Int = Integer.parseInt(text.text.toString())
                viewModel.getQuote(random)
                viewModel.myResponse2.observe(viewLifecycleOwner, Observer { response ->
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
//        val random = Random()
//        val upperBound = 100
//        val randomNumber = random.nextInt(upperBound)


        btn.setOnClickListener {

//            val myquote = QuoteModel(1,"I am the King","James")
//            viewModel.getQuotes2(num)

        }

//        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment5.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BlankFragment5().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}