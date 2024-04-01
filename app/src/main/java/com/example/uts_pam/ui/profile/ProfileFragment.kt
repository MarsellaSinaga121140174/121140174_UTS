package com.example.uts_pam.ui.profile


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.uts_pam.databinding.FragmentProfileBinding
import com.example.uts_pam.ui.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: ProfileViewModel by viewModels {
            factory
        }

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        (activity as AppCompatActivity).supportActionBar?.show()

        viewModel.getProfileUser(user?.email.toString()).observe(viewLifecycleOwner) { it ->
            it?.let {
                binding.tvProfile.text = it.nama.firstOrNull()?.toString()?.uppercase() ?: ""
                binding.tvNama.text = it.nama
                binding.tvGithub.text = it.github
                binding.tvEmail.text = it.email
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}