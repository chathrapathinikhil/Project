package edu.fullerton.jd.workodoro

import com.google.gson.annotations.SerializedName

data class PostModel(
     val userId: Int? = null,
     val id: Int?=null,
     val title: String? = null,
     val body:String?=null
)
