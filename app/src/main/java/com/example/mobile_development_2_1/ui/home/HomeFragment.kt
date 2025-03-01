package com.example.mobile_development_2_1.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_development_2_1.DayListAdapter
import com.example.mobile_development_2_1.DayPrognosis
import com.example.mobile_development_2_1.databinding.FragmentHomeBinding
import com.google.android.material.button.MaterialButton.OnCheckedChangeListener
import com.google.android.material.search.SearchBar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: DayListAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.rView.layoutManager = LinearLayoutManager(context)
        val root: View = binding.root
        val searchBar: SearchView = binding.searchBar
        val radioGroup: RadioGroup = binding.typeOfTemperature
        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when(checkedId){
                    binding.FahrenheitButton.id -> homeViewModel.isCelsius.value = false
                    binding.CelciusButton.id -> homeViewModel.isCelsius.value = true
                }
                homeViewModel.fetchWeather()
            }

        })
        homeViewModel.items.observe(viewLifecycleOwner){

            adapter = DayListAdapter()
            binding.rView.adapter = adapter
        }
        homeViewModel.errorMessage.observe(viewLifecycleOwner){
            val toast = Toast.makeText(context, homeViewModel.errorMessage.value, Toast.LENGTH_LONG)
            toast.show()
        }
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                homeViewModel.selectedCity.value = query
                homeViewModel.fetchWeather()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        }) /*
        homeViewModel.selectedCity.observe(viewLifecycleOwner){
            homeViewModel.fetchWeather()
        }

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        homeViewModel.fetchWeather()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}