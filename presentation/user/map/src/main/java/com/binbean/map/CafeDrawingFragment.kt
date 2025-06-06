package com.binbean.map

import android.R
import android.app.AlertDialog
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.binbean.domain.cafe.Cafe
import com.binbean.map.databinding.FragmentCafeDrawingBinding
import kotlinx.coroutines.launch
import java.io.File

class CafeDrawingFragment : Fragment() {
    private lateinit var binding: FragmentCafeDrawingBinding
    private val viewModel: CafeDrawingViewModel by viewModels()

    private var cafe: Cafe? = null

    private lateinit var cameraUri: Uri
    private lateinit var imageFile: File

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
//                uploadImage(cameraUri)
            }
        }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//            uri?.let { uploadImage(it) }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cafe = arguments?.getSerializable("cafe") as? Cafe
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCafeDrawingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cafe?.let {
            binding.tvCafeName.text = it.name
        }

        val items = listOf("1층", "2층", "3층")
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.floorSpinner.adapter = adapter
    }

    private fun showImagePickerDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("사진 업로드 방법 선택")
            .setItems(arrayOf("카메라로 촬영", "갤러리에서 선택")) { _, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }
            .show()
    }

    private fun openCamera() {
        val photoFile = File.createTempFile("camera_", ".jpg", requireContext().cacheDir)
        imageFile = photoFile
        cameraUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            photoFile
        )
        cameraLauncher.launch(cameraUri)
    }

    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

//    private fun uploadImage(uri: Uri) {
//        val file = uriToFile(uri) ?: return
//        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//        val multipartBody = MultipartBody.Part.createFormData("image", file.name, requestFile)
//
//        lifecycleScope.launch {
//            try {
//                val response = RetrofitClient.api.uploadImage(multipartBody)
//                if (response.isSuccessful) {
//                    Log.d("Upload", "업로드 성공: ${response.body()?.string()}")
//                } else {
//                    Log.e("Upload", "업로드 실패: ${response.errorBody()?.string()}")
//                }
//            } catch (e: Exception) {
//                Log.e("Upload", "에러 발생: ${e.message}")
//            }
//        }
//    }

    private fun uriToFile(uri: Uri): File? {
        return requireContext().contentResolver.openInputStream(uri)?.use { input ->
            val file = File.createTempFile("upload_", ".jpg", requireContext().cacheDir)
            file.outputStream().use { output -> input.copyTo(output) }
            file
        }
    }
}