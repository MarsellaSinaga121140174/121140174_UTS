package com.example.uts_pam.ui.register

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.uts_pam.R
import com.example.uts_pam.database.User
import com.example.uts_pam.databinding.FragmentRegisterBinding
import com.example.uts_pam.network.LoginPreferences
import com.example.uts_pam.ui.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var lh: LoginPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val registerViewModel: RegisterViewModel by viewModels {
            factory
        }

        auth = FirebaseAuth.getInstance()
        lh = LoginPreferences(requireContext())

        (activity as AppCompatActivity).supportActionBar?.hide()

        val btnRegister = binding.btnRegister

        btnRegister.setOnClickListener{
            val nama = binding.rgName.text.toString()
            val github = binding.rgGithub.text.toString()
            val nik = binding.rgNik.text.toString()
            val email = binding.rgEmail.text.toString()
            val password = binding.rgPassword.text.toString()

            if(nama.isEmpty()){
                binding.rgName.error = "Nama Harus Diisi"
                binding.rgName.requestFocus()
                return@setOnClickListener
            }
            if (github.isEmpty()){
                binding.rgGithub.error = "Username Github Harus Diisi"
                binding.rgGithub.requestFocus()
                return@setOnClickListener
            }
            if (nik.isEmpty()){
                binding.rgNik.error = "NIK harus diisi"
                binding.rgNik.requestFocus()
                return@setOnClickListener
            }
            if (email.isEmpty()){
                binding.rgEmail.error = "Email Harus Diisi"
                binding.rgEmail.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.rgEmail.error = "Email Tidak Valid"
                binding.rgEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.length < 6){
                binding.rgPassword.error = "Password minimal 6 karakter"
                binding.rgPassword.requestFocus()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()){
                    if (it.isSuccessful){
                        val user = User(
                            nama = nama,
                            github = github,
                            nik = nik,
                            email = email,
                            password = password
                        )
                        registerViewModel.run {
                            registerUser(user)
                        }
                        lh.setStatus(true)
                        Toast.makeText(requireContext(), "Register barhasil", Toast.LENGTH_SHORT).show()

                        val navController = findNavController()
                        navController.navigate(R.id.action_navigation_register_to_home)
                    } else {
                        Toast.makeText(requireContext(), "Error: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}