package com.dicoding.asclepius.view.examine

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.FragmentExamineBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.helper.getImageUri
import com.dicoding.asclepius.helper.gone
import com.dicoding.asclepius.helper.visible
import com.yalantis.ucrop.UCrop
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.io.File
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ExamineFragment : Fragment() {
    private var _binding: FragmentExamineBinding? = null
    private val binding get() = _binding!!

    private var currentImageUri: Uri? = null

    private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())


    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showToast("Permission request granted")
            } else {
                showToast("Permission request denied")
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        currentImageUri = uri
        currentImageUri?.let {
            startCropActivity(it)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            currentImageUri?.let {
                startCropActivity(it)
            }
        } else {
            currentImageUri = null
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == ResultActivity.RESULT_OK) {
            binding.progressIndicator.gone()
            currentImageUri = null
            binding.previewImageView.setImageResource(R.drawable.img_placeholder)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExamineBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.apply {
            galleryButton.setOnClickListener {
                startGallery()
            }

            cameraButton.setOnClickListener {
                startCamera()
            }

            analyzeButton.setOnClickListener {
                lateinit var diagnosisResult: String
                if(currentImageUri != null) {
                    binding.progressIndicator.visible()
                    diagnosisResult = analyzeImage()
                    moveToResult(diagnosisResult, currentImageUri!!)
                } else {
                    showToast("Picture is missing. Please take a picture first.")
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        binding.progressIndicator.gone()
        if (resultCode == Activity.RESULT_OK) {
            val resultUri = UCrop.getOutput(data!!)
            if (resultUri != null) {
                currentImageUri = resultUri
                showImage()
            } else {
                currentImageUri = null
                Log.e("ExamineFragment", "Error: Cropped image URI is null")
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val error = UCrop.getError(data!!)
            currentImageUri = null
            Log.e("ExamineFragment", "Crop activity encountered an error: $error")
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.d("ExamineFragment", "Crop activity was cancelled")
            currentImageUri = null
        }
    }

    private fun startCropActivity(uri: Uri) {
        val destinationUri = Uri.fromFile(File(requireContext().cacheDir, "cropped${timeStamp}"))
        val options = UCrop.Options()
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG)

        UCrop.of(uri, destinationUri)
            .withOptions(options)
            .start(requireContext(), this)

    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri)
    }


    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage(): String {
        lateinit var displayResult: String
        val imageClassifierHelper = ImageClassifierHelper(
            context = requireContext(),
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    requireActivity().runOnUiThread {
                        showToast(getString(R.string.image_classifier_failed))
                    }
                }

                override fun onResults(results: List<Classifications>?) {
                    requireActivity().runOnUiThread {
                        results?.let { it ->

                            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                displayResult =
                                    it[0].categories.joinToString("\n") {
                                        "Diagnosis: ${it.label}\nConfidence Score: " + NumberFormat.getPercentInstance()
                                            .format(it.score).trim()
                                    }
                            } else {
                                displayResult = "Not Found"
                            }

                        }
                    }
                }
            }
        )
        imageClassifierHelper.classifyStaticImage(currentImageUri!!)
        return displayResult
    }

    private fun moveToResult(diagnosisResult: String, imgUri: Uri) {
        val intent = Intent(requireContext(), ResultActivity::class.java)
        intent.putExtra("result", diagnosisResult)
        intent.putExtra("imagePath", imgUri.toString())
        resultLauncher.launch(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
    }
}