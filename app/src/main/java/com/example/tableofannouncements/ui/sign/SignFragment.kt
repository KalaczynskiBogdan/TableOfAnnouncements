package com.example.tableofannouncements.ui.sign

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tableofannouncements.R
import com.example.tableofannouncements.databinding.FragmentSignBinding
import com.example.tableofannouncements.utils.SignConst

class SignFragment : Fragment() {
    private var _binding: FragmentSignBinding? = null
    private val binding get() = _binding!!

    private var index: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receivedIndex()
        index?.let { setFragmentState(it) }

    }

    private fun receivedIndex(){
        arguments?.let {
            index = it.getInt(INDEX)
        }
    }

    private fun setFragmentState(index: Int) {
        if (index == SignConst.SIGN_UP_STATE) {
            binding.tvSignTitle.text = requireContext().resources.getString(R.string.an_sign_up)
            binding.btnSign.text = requireContext().resources.getString(R.string.sign_up_action)
        } else {
            binding.tvSignTitle.text = requireContext().resources.getString(R.string.an_sign_in)
            binding.btnSign.text = requireContext().resources.getString(R.string.sign_in_action)
            binding.btnForgetPassword.visibility = View.VISIBLE
        }
    }

    //Todo: refactor clicks

//    private fun setOnClickSign(index: Int, binding: SignDialogBinding, dialog: AlertDialog) {
//        if (index == SignConst.SIGN_UP_STATE) {
//            accHelper.signUpWithEmail(
//                binding.etSignEmail.text.toString(),
//                binding.etSignPassword.text.toString()
//            )
//        } else {
//            dialog.dismiss()
//            accHelper.signInWithEmail(
//                binding.etSignEmail.text.toString(),
//                binding.etSignPassword.text.toString()
//            )
//        }
//    }
//
//    private fun setOnClickForgetPassword(binding: SignDialogBinding, dialog: AlertDialog) {
//        if (binding.etSignEmail.text.toString().isNotEmpty()) {
//            activity.myAuth.sendPasswordResetEmail(binding.etSignEmail.text.toString())
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Toast.makeText(
//                            activity,
//                            activity.resources.getString(R.string.password_reset_message),
//                            Toast.LENGTH_LONG
//                        ).show()
//                    } else {
//                        Toast.makeText(
//                            activity,
//                            activity.resources.getString(R.string.password_reset_message),
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                }
//            dialog.dismiss()
//        } else {
//            binding.apply {
//                tvEnterEmail.visibility = View.VISIBLE
//            }
//        }
//    }
//
//    private fun setOnClickSignInGoogle(dialog: AlertDialog) {
//        CoroutineScope(Dispatchers.IO).launch {
//            accHelper.signInWithGoogle()
//        }
//        dialog.dismiss()
//    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object{
        private const val INDEX = "index_int"

        fun newInstance(index: Int):SignFragment{
            return SignFragment().apply {
                arguments = Bundle().apply { putInt(INDEX, index) }
            }
        }
    }

}