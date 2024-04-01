package com.example.uts_pam.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_pam.databinding.FragmentHomeBinding
import com.example.uts_pam.model.DataItem
import com.example.uts_pam.model.ResponseUser
import com.example.uts_pam.network.ApiConfig
import com.example.uts_pam.network.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var adapter: HomeAdapter
    private lateinit var DataUser : ArrayList<DataItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val lm = LinearLayoutManager(activity)
        lm.orientation = LinearLayoutManager.VERTICAL

        (activity as AppCompatActivity).supportActionBar?.show()

        DataUser = arrayListOf()

        adapter = HomeAdapter(DataUser)

        val recyclerView = binding.rvUser
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = lm
        recyclerView.adapter = adapter

        getUserData()
        searchUser()

        return root
    }

    private fun getUserData(){
        val client = ApiConfig.getApiService().getListUsers("20")

        client.enqueue(object : Callback<ResponseUser>{
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if(response.isSuccessful){
                    val dataArray = response.body()?.data as List<DataItem>
                    for (data in dataArray){
                        adapter.setData(data)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }

    private fun searchUser(){
        val searchView = binding.svUser
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Handler(Looper.getMainLooper()).removeCallbacksAndMessages(null)
                Handler(Looper.getMainLooper()).postDelayed({
                    if(newText != null){
                        val filterList = ArrayList<DataItem>()
                        for (item in DataUser){
                            if(item.firstName?.lowercase(Locale.ROOT)!!.contains(newText) || item.lastName?.lowercase(
                                    Locale.ROOT)!!.contains(newText)){
                                filterList.add(item)
                            }
                        }
                        if(filterList.isEmpty()){
                            Toast.makeText(requireContext(),"Data Tidak Ditemukan", Toast.LENGTH_SHORT).show()
                        } else{
                            adapter.setFilterUser(filterList)
                        }
                    }
                },500)

                return false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}