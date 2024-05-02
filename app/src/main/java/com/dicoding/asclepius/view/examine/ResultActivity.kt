package com.dicoding.asclepius.view.examine

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    private lateinit var currentImageUri: Uri
    private lateinit var diagnosisResult: String

    private val resultViewModel by viewModels<ResultViewModel>{
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIntentExtra()
        showImage()
        showDiagnosis()
        resultViewModel.insertScan(
            img = currentImageUri.toString(),
            diagnosis = diagnosisResult,
            date = resultViewModel.getCurrentTime()
        )

        binding.apply {
            topBar.setNavigationOnClickListener {
                setResult(RESULT_OK)
                finish()
            }
            btnExamineAgain.setOnClickListener {
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    private fun showDiagnosis() {
        binding.tvResultDesc.text = diagnosisResult
    }

    private fun getIntentExtra() {
        val imgPath = intent.getStringExtra("imagePath")
        currentImageUri = Uri.parse(imgPath)
        diagnosisResult = intent.getStringExtra("result") ?: getString(R.string.image_classifier_failed)
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.resultImage.setImageURI(it)
        }
    }

    companion object {
        const val RESULT_OK = 15350
    }


}