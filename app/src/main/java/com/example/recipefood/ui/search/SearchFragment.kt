package com.example.recipefood.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipefood.R
import com.example.recipefood.adapters.RecentFoodAdapter
import com.example.recipefood.databinding.FragmentSearchBinding
import com.example.recipefood.ui.recipe.RecipeFragmentDirections
import com.example.recipefood.utils.MyResponseNetworkRequest
import com.example.recipefood.utils.NetworkConnection
import com.example.recipefood.viewmodel.search.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

lateinit var binding: FragmentSearchBinding




@Inject
lateinit var recentAdapter:RecentFoodAdapter



@Inject
lateinit var networkConnection: NetworkConnection

private val viewModel:SearchViewModel by viewModels()


    private var isNetworkAvailable=false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)






        lifecycleScope.launchWhenStarted {


            networkConnection.checkNetwork().collect{state->


isNetworkAvailable=state

                if (state){


                    binding.internetLay.visibility=View.GONE







                }else{

                    binding.internetLay.visibility=View.VISIBLE


                }








            }



        }



        binding.searchEdt.addTextChangedListener {



            if (it.toString().length>2  && isNetworkAvailable){







            viewModel.callSearchApi(viewModel.searchQueries(it.toString()))


            }



        }
        loadRecentData()

    }
    private fun loadRecentData() {
        binding.apply {
            viewModel.searchLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponseNetworkRequest.LOADING -> {
                        searchList.showShimmer()
                    }
                    is MyResponseNetworkRequest.SUCCESS -> {
                        searchList.hideShimmer()
                        response.data?.let { data ->
                            if (data.results!!.isNotEmpty()) {
                                recentAdapter.setData(data.results)
                                initRecentRecycler()
                            }
                        }
                    }
                    is MyResponseNetworkRequest.ERROR -> {
                        searchList.hideShimmer()
                       Snackbar.make(binding.root,response.error.toString(),Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun initRecentRecycler() {
        binding.searchList.layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.searchList.adapter=     recentAdapter

        //Click
        recentAdapter.setOnItemClickListener {

            findNavController().navigate(RecipeFragmentDirections.actionToDetailFragment(it))
        }
    }



}