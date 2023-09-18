package edu.fullerton.jd.workodoro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class Fragment2 : Fragment()
{
    private lateinit var text: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // view for fragment2
        val view = inflater.inflate(R.layout.fragment2, container, false)

        // bind views to variables here
        text = view.findViewById(R.id.textView)

        return view
    }
}