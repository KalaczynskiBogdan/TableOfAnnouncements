package com.example.tableofannouncements.ui.newads

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.tableofannouncements.R
import com.example.tableofannouncements.databinding.FragmentImagePickerBinding
import io.ak1.pix.helpers.PixEventCallback
import io.ak1.pix.helpers.pixFragment
import io.ak1.pix.models.Flash
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio

class ImagePickerFragment : Fragment() {
    private var _binding: FragmentImagePickerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImagePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (childFragmentManager.backStackEntryCount > 0) {
                childFragmentManager.popBackStack()
            } else {
                parentFragmentManager.popBackStack()
            }
        }

        launchPix()
    }

    private fun launchPix() {
        val options = Options().apply {
            ratio = Ratio.RATIO_AUTO
            count = 3
            path = "Pix/Camera"
            isFrontFacing = false
            mode = Mode.Picture
            flash = Flash.Auto
        }

        val pixFragment = pixFragment(options) {
            when (it.status) {
                PixEventCallback.Status.SUCCESS -> {
                    val selectedMedia = it.data
                    selectedMedia.forEach { Log.d("Pix", "Выбранные URI: $selectedMedia") }
                }
                PixEventCallback.Status.BACK_PRESSED -> {
                    Log.d("Pix", "Пользователь отменил выбор")
                }
            }
        }

        childFragmentManager.beginTransaction()
            .replace(R.id.container2, pixFragment)
            .addToBackStack(null)
            .commit()
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}