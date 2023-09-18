package edu.fullerton.jd.workodoro

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment2 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
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
        val view=inflater.inflate(R.layout.fragment_blank2, container, false)
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
//        webView.loadUrl("https://media.tenor.com/kYaQKOvNf9oAAAAC/kit-kat-break.gif")

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BlankFragment2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}