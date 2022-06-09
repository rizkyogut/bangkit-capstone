package com.bangkit2022.boemboe.ui.camera

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bangkit2022.boemboe.databinding.ActivityMainCameraBinding
import com.bangkit2022.boemboe.ml.SpicesV1
import org.tensorflow.lite.support.image.TensorImage

class MainCameraActivity : AppCompatActivity(), View.OnClickListener {
    private var _binding: ActivityMainCameraBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private lateinit var tvOutput: TextView
    private val GALLERY_REQUEST_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        button = binding.btnCaptureImage
        tvOutput = binding.tvOutput
        imageView = binding.imageView

        with(binding) {
            btnCaptureImage.setOnClickListener(this@MainCameraActivity)
            btnLoadImage.setOnClickListener(this@MainCameraActivity)
            tvOutput.setOnClickListener(this@MainCameraActivity)
        }
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnCaptureImage -> startCamera()
            binding.btnLoadImage -> startGallery()
            binding.tvOutput -> startResultML()
        }
    }

    private fun startCamera() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            takePicturePreview.launch(null)
        } else {
            requestPermission.launch(android.Manifest.permission.CAMERA)
        }
    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                takePicturePreview.launch(null)
            } else {
                Toast.makeText(this, "Permission Denied !! Try again", Toast.LENGTH_SHORT).show()
            }
        }

    // launch camera and take picture
    private val takePicturePreview =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
                outputGenerator(bitmap)
            }
        }


    private fun startGallery() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            onresult.launch(intent)
        } else {
            requestPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    //to get image from gallery
    private val onresult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.i("TAG", "This is the result: ${result.data} ${result.resultCode}")
            onResultReceived(GALLERY_REQUEST_CODE, result)
        }

    private fun onResultReceived(requestCode: Int, result: ActivityResult?) {
        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                if (result?.resultCode == Activity.RESULT_OK) {
                    result.data?.data?.let { uri ->
                        Log.i("TAG", "onResultReceived: $uri")
                        val bitmap =
                            BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                        imageView.setImageBitmap(bitmap)
                        outputGenerator(bitmap)
                    }
                } else {
                    Log.e("TAG", "onActivityResult: error in selecting image")
                }
            }
        }
    }

    private fun outputGenerator(bitmap: Bitmap) {
        //declearing tensor flow lite model variable
        val spiceModel = SpicesV1.newInstance(this)

        // converting bitmap into tensor flow image
        val newBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val tfimage = TensorImage.fromBitmap(newBitmap)

        //process the image using trained model and sort it in descending order
        val outputs = spiceModel.process(tfimage)
            .probabilityAsCategoryList.apply {
                sortByDescending { it.score }
            }

        //getting result having high probability
        val highProbabilityOutput = outputs[0]

        //setting ouput text
        binding.tvOutput.text = highProbabilityOutput.label
        Log.i("TAG", "outputGenerator: $highProbabilityOutput")
    }

    private fun startResultML() {
        val intent = Intent(Intent.ACTION_VIEW,
            Uri.parse("https://www.google.com/search?q=Rekomendasi+Makanan+Menggunakan+Bumbu+${binding.tvOutput.text}"))
        startActivity(intent)
    }
}
