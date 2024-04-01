package com.example.uts_pam.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.uts_pam.R
import com.example.uts_pam.databinding.FragmentLoginBinding
import com.example.uts_pam.network.LoginPreferences
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    private lateinit var lh: LoginPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity).supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        lh = LoginPreferences(requireContext())

        binding.tvRegister.setOnClickListener{
            val navController = findNavController()
            navController.navigate(R.id.action_navigation_login_to_register)
        }

        binding.btnSignin.setOnClickListener {
            val email = binding.lgEmail.text.toString()
            val password = binding.lgPassword.text.toString()

            if (email.isEmpty()){
                binding.lgEmail.error = "Email Tidak Boleh Kosong"
                binding.lgEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()){
                binding.lgPassword.error = "Password Tidak Valid"
                binding.lgPassword.requestFocus()
                return@setOnClickListener
            }
            if (password.length < 6){
                binding.lgPassword.error = "Password Minimal 6 Karakter"
                binding.lgPassword.requestFocus()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(requireActivity()){
                    if(it.isSuccessful){
                        val navController = findNavController()
                        lh.setStatus(true)
                        navController.navigate(R.id.action_navigation_login_to_home)
                    } else {
                        Toast.makeText(requireContext(), "Email/Password Tidak Valid", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}