package edu.temple.audiobb
import com.squareup.moshi.JsonClass
import java.io.Serializable
@JsonClass(generateAdapter = true)

data class Book(val title: String, val author: String, val id: Int, val coverURL: String) : Serializable
