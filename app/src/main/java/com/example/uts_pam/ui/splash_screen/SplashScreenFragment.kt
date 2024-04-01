package com.example.uts_pam.ui.splash_screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.uts_pam.R
import com.example.uts_pam.databinding.FragmentSplashScreenBinding
import com.example.uts_pam.network.LoginPreferences

class SplashScreenFragment : Fragment() {
   private var _binding : FragmentSplashScreenBinding? = null
   private val binding get() = _binding!!

   private lateinit var lh: LoginPreferences

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      lh = LoginPreferences(requireContext())

      (activity as AppCompatActivity).supportActionBar?.hide()

      Handler(Looper.getMainLooper()).removeCallbacksAndMessages(null)
      Handler(Looper.getMainLooper()).postDelayed({
         val navController = findNavController()
         if(lh.getStatus()){
            navController.navigate(R.id.navigation_home)
         } else{
            navController.navigate(R.id.navigation_login)
         }
      }, 3000)
   }

   override fun onDestroyView() {
      super.onDestroyView()
      _binding = null
   }

}