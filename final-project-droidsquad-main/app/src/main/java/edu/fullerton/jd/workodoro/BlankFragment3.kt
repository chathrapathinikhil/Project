package edu.fullerton.jd.workodoro


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
//import edu.fullerton.jd.workodoro.databinding.FragmentBlank3Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var quoteAdapter: QuoteAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment3.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment3 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
//    private var _binding: FragmentBlank3Binding? = null
//    private val binding get() = _binding!!

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
    ): View {
        val view = inflater.inflate(R.layout.fragment_blank3, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("stanley", "I entered the code")
        var recyclerView: RecyclerView = view.findViewById(R.id.recycleview)
        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        val call = serviceGenerator.getQuotes()
        val dataList = mutableListOf<PostModel>()
        val myAdapter = QuoteAdapter(dataList)
        recyclerView.adapter = myAdapter

//        serviceGenerator.getData().enqueue(object : Callback<List<PostModel>> {
//            override fun onResponse(call: Call<List<PostModel>>, response: Response<List<PostModel>>) {
////                val dataList = response.body() ?: emptyList()
//                dataList.addAll(response.body()!!)
//                myAdapter.notifyDataSetChanged()
//                // Render the data somewhere in the local app
//            }
//
//            override fun onFailure(call: Call<List<PostModel>>, t: Throwable) {
//                t.printStackTrace()
//            }
//        })
//
//        serviceGenerator.sendData(PostModel()).enqueue(object : Callback<Void> {
//            override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                // Data successfully sent to the remote API
//
//            }
//
//            override fun onFailure(call: Call<Void>, t: Throwable) {
//                t.printStackTrace()
//            }
//        })



//
//        val call4 = .getMyData()
//        call.enqueue(object : Callback<List<MyData>> {
//            override fun onResponse(call: Call<List<MyData>>, response: Response
//
//
        call.enqueue(object : Callback<MutableList<PostModel>> {
            override fun onResponse(call: Call<MutableList<PostModel>>, response: Response<MutableList<PostModel>>) {
                Log.i("stanley", "I started here")
                if (response.isSuccessful) {

                    dataList.addAll(response.body()!!)
                    myAdapter.notifyDataSetChanged()

                    Log.i("stanley", "Response size: ${dataList.size}")
                } else {
                    Log.e("error", "Response is null")
                }
            }

            override fun onFailure(call: Call<MutableList<PostModel>>, t: Throwable) {
                t.printStackTrace()
                Log.e("error", t.message.toString() + "hi there")
            }

        })


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

        recyclerView.layoutManager = LinearLayoutManager(context)
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment3.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BlankFragment3().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

//    override fun onStart() {
//        super.onStart()
//        var recyclerView: RecyclerView = requireView().findViewById(R.id.recycleview)
//        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
//        val call = serviceGenerator.getQuotes()
//        val dataList = mutableListOf<PostModel>()
//        val myAdapter = QuoteAdapter(dataList)
//        recyclerView.adapter = myAdapter
//
//        call.enqueue(object : Callback<MutableList<PostModel>> {
//            override fun onResponse(call: Call<MutableList<PostModel>>, response: Response<MutableList<PostModel>>) {
//                if (response.isSuccessful) {
//                    val dataList = response.body() ?: emptyList()
//                    myAdapter.notifyDataSetChanged()
//                    Log.i("stanley", "Response size: ${dataList.size}")
//                } else {
//                    Log.e("error", "Response is null")
//                }
//            }
//
//            override fun onFailure(call: Call<MutableList<PostModel>>, t: Throwable) {
//                t.printStackTrace()
//                Log.e("error", t.message.toString() + "hi there")
//            }
//        })
//    }

}


//fun updatePost(postModel: PostModel) {
//    postModel.id?.let {
//        serviceGenerator.updatePost(it, postModel).enqueue(object : Callback<PostModel> {
//            override fun onResponse(call: Call<PostModel>, response: Response<PostModel>) {
//                if (response.isSuccessful) {
//                    val updatedPost = response.body()
//                    quoteAdapter.updateQuote(updatedPost)
//                } else {
//                    Log.e("error", "Response is null")
//                }
//            }
//
//            override fun onFailure(call: Call<PostModel>, t: Throwable) {
//                t.printStackTrace()
//                Log.e("error", t.message.toString())
//            }
//        })
//    }
//}
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding=null
//    }

//    companion object {
//        private const val TAG = "BlankFragment3"
//        fun newInstance() = BlankFragment3()
//    }
//                    val dataList = mutableListOf<PostModel>()
//                    quoteAdapter = QuoteAdapter(postList)
//                    recyclerView.layoutManager = LinearLayoutManager(context)
//                    recyclerView.adapter = quoteAdapter
//                    recyclerView.apply {
//                        Log.i("stanley", "I was here")
////                    layoutManager=LinearLayoutManager{this@apply}
//                        layoutManager = LinearLayoutManager(context)
////                    adapter=QuoteAdapter(response.body()!!)
//                        quoteAdapter = QuoteAdapter(response.body()!!)
////                        adapter = quoteAdapter
//                    }