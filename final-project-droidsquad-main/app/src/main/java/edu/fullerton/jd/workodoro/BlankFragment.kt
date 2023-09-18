package edu.fullerton.jd.workodoro

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG1 = "BlankFrag"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var imageView: ImageView
//    private lateinit var viewModel: SharedViewModel
    private lateinit var webView: WebView

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
        val view = inflater.inflate(R.layout.fragment_blank, container, false)
//        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
////        imageView = view.findViewById(R.id.imageView)
//
//        var webView: WebView = view.findViewById(R.id.webvidew)
////        webView.setScaleType(ImageView.ScaleType.FIT_XY);
//        // Adding the gif here using glide library
//        //        webView.settings.javaScriptEnabled = true
//        WebViewClient().also { webView.webViewClient = it }
//        val webSettings = webView.settings
//        webSettings.useWideViewPort = true
//        webSettings.loadWithOverviewMode = true
////        Log.v(TAG1,"Status: $viewModel.buttonClicked")
////        if (viewModel.buttonClicked) {
////            webView.loadUrl("https://media.tenor.com/a3DOY_-r3dcAAAAC/yeah-you-can-do-it.gif")
////        }
//
//        // add the url of gif
//
//        webView.loadUrl("https://media.tenor.com/a3DOY_-r3dcAAAAC/yeah-you-can-do-it.gif")
//        try {
//            // Set the URL of the GIF you want to display.
//            val url = "https://media.tenor.com/a3DOY_-r3dcAAAAC/yeah-you-can-do-it.gif"
//            val imageUrl = URL(url)
//
//            // Open a connection to the URL.
//            val connection = imageUrl.openConnection() as HttpURLConnection
//            connection.requestMethod = "GET"
//            connection.connect()
//
//            // Read the response into a byte array.
//            val inputStream = connection.inputStream
//            val outputStream = ByteArrayOutputStream()
//            val buffer = ByteArray(1024)
//            var len: Int
//            while (inputStream.read(buffer).also { len = it } != -1) {
//                outputStream.write(buffer, 0, len)
//            }
//            val response = outputStream.toByteArray()
//
//            // Encode the byte array as a base64 string.
//            val base64String: String = Base64.encodeToString(response, Base64.DEFAULT)
//
//            // Load the base64-encoded string into a WebView.
//            val html =
//                "<html><body><img src=\"data:image/gif;base64,$base64String\" /></body></html>"
//            webView.loadData(html, "text/html", "base64")
//        } catch (e: IOException) {
//            Log.e("HttpURLConnection", "Error retrieving GIF: " + e.message)
//        }
        // Inflate the layout for this fragment
        return view
    }
}
