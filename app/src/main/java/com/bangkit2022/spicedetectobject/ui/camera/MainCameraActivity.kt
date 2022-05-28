package com.bangkit2022.spicedetectobject.ui.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bangkit2022.spicedetectobject.databinding.ActivityMainCameraBinding
import kotlinx.coroutines.runBlocking
import java.io.File

class MainCameraActivity : AppCompatActivity() {
    private var getFile: File? = null


    private lateinit var binding: ActivityMainCameraBinding
    private lateinit var currentPhotoPath: String

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.cameraButton.setOnClickListener { startTakePhoto() }
        binding.galleryButton.setOnClickListener { startGallery() }
//        binding.uploadButton.setOnClickListener { uploadImage() }
    }


    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@MainCameraActivity,
                "com.bangkit2022.spicedetectobject",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }


//    private fun uploadImage() {
//        if (getFile != null) {
//            val file = reduceFileImage(getFile as File)
//
//            showLoading(true)
//            val description = binding.addStoryDescription.text.toString()
//                .toRequestBody("text/plain".toMediaType())
//            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
//            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
//                "photo",
//                file.name,
//                requestImageFile
//            )
//            showLoading(true)
//            val token = runBlocking { tesPref.getToken() }
//            val bearerToken = "Bearer $token"
//            val service =
//                ApiConfig.getApiService().postNewStory(bearerToken, imageMultipart, description)
//            service.enqueue(object : Callback<NewStoryResponse> {
//                override fun onResponse(
//                    call: Call<NewStoryResponse>,
//                    response: Response<NewStoryResponse>
//                ) {
//                    showLoading(false)
//                    if (response.isSuccessful) {
//                        val responseBody = response.body()
//                        if (responseBody != null && !responseBody.error) {
//                            Toast.makeText(
//                                this@MainCameraActivity,
//                                responseBody.message,
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            startActivity(Intent(this@MainCameraActivity, MainActivity::class.java))
//                            finish()
//
//                        }
//                    } else {
//                        Toast.makeText(
//                            this@MainCameraActivity,
//                            response.message(),
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<NewStoryResponse>, t: Throwable) {
//                    showLoading(false)
//                    Toast.makeText(
//                        this@MainCameraActivity,
//                        "Gagal instance Retrofit",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            })
//
//        } else {
//            Toast.makeText(
//                this@MainCameraActivity,
//                "Silakan masukkan berkas gambar terlebih dahulu.",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }

//    private fun showLoading(isLoading: Boolean) {
//        if (isLoading) {
//            binding.pbUploadStory.visibility = View.VISIBLE
//        } else {
//            binding.pbUploadStory.visibility = View.GONE
//        }
//    }


    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile
            val result = BitmapFactory.decodeFile(myFile.path)

            binding.previewImage.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@MainCameraActivity)
            getFile = myFile
            binding.previewImage.setImageURI(selectedImg)
        }
    }


}