package com.delivery.quickie.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.delivery.quickie.R
import com.delivery.quickie.databinding.ActivityProfileBinding
import com.delivery.quickie.network.ImageResponse
import com.delivery.quickie.network.RetrofitClient
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileBinding
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var selectedImageUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityProfileBinding.inflate(layoutInflater).also { binding = it }.root)

        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE)

        binding.btnUpload.setOnClickListener {
            openGallery()
        }

        binding.btnContinue.setOnClickListener {
            val username = binding.etUsername.text
            if (username != null) {
                val imagePart: MultipartBody.Part = prepareFilePart("image", selectedImageUri)
                val email = sharedPreferences.getString("email", "")

                // Specifying the media type for the email content
                // RequestBody.create(...) is used to create a RequestBody instance for the email content.
                val emailRequestBody: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), email.toString())
                val nameRequestBody: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), username.toString())

                val call: Call<ImageResponse> = RetrofitClient.dbApi.uploadProfileImage(imagePart, emailRequestBody, nameRequestBody)
                call.enqueue(object : Callback<ImageResponse> {
                    override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@ProfileActivity, "Uploaded", Toast.LENGTH_SHORT).show()

                            val editor = sharedPreferences.edit()
                            editor.putBoolean("upload", true)
                            editor.putString("profile", response.body()?.message!!)
                            editor.apply()

                            Log.d("res", response.body()?.status!!)

                            Intent(this@ProfileActivity, home::class.java).also {
                                startActivity(it)
                                finish()
                            }

                        } else {
                            Log.d("res", response.body().toString())
                            Toast.makeText(this@ProfileActivity, "Image Upload failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                        Log.d("res", t.message!!)
                        Toast.makeText(this@ProfileActivity, "Error: " + t.message, Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null && data.data != null) {
                // Handle the selected image URI here
                selectedImageUri = data.data!!
                val selectedImage: String = selectedImageUri.toString()
                Log.d("SelectedImage", "Uri: $selectedImage")
                binding.ivProfile.setImageURI(selectedImageUri)
                binding.btnContinue.visibility = View.VISIBLE
                binding.etUsername.visibility = View.VISIBLE
            }
        }
    }

    private fun prepareFilePart(partName: String, fileUri: Uri): MultipartBody.Part {
        val filePath: String? = getFilePathFromContentUri(this, fileUri)
        val file = File(filePath)
        val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    private fun getFilePathFromContentUri(context: Context, uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA)
        val cursor: Cursor = context.contentResolver.query(uri, projection, null, null, null)!!

        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA)
            val filePath: String = cursor.getString(columnIndex)
            cursor.close()
            return filePath
        }

        return null
    }

}