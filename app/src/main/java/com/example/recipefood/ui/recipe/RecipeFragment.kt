package com.example.recipefood.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.recipefood.R
import com.example.recipefood.adapters.PopularAdapter
import com.example.recipefood.adapters.RecentFoodAdapter
import com.example.recipefood.data.model.recipe.ResponseRecipes
import com.example.recipefood.databinding.FragmentRecipeBinding
import com.example.recipefood.utils.MyResponseNetworkRequest
import com.example.recipefood.utils.onceObserve
import com.example.recipefood.viewmodel.recipe.RecipeViewModel
import com.example.recipefood.viewmodel.register.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject


@AndroidEntryPoint
class RecipeFragment : Fragment() {

    lateinit var binding: FragmentRecipeBinding


    private val registerViewModel: RegisterViewModel by viewModels()


    @Inject
    lateinit var popularAdapter: PopularAdapter

    @Inject
    lateinit var recentAdapter: RecentFoodAdapter

    private var autoScrollIndex = 0


    private val viewModel: RecipeViewModel by viewModels()






private val  args:RecipeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        callPopularData()




        callRecentRecipeApi()




        loadPopularData()
        loadRecentData()




        lifecycleScope.launchWhenCreated {


            registerViewModel.readDataStore.collect {

                binding.userName.text =
                    "${getString(R.string.hello)},${it.userName}${String(Character.toChars(0x1f44b))}"

            }
        }


















    }


    private fun callPopularData() {

        initPopularRecycler()
        viewModel.readAllPopularRecipes.onceObserve(viewLifecycleOwner) { database ->

            if (database.isNotEmpty()) {

                database[0].foodRecipe.results?.let { response ->


                    binding.popularFoodsRecy.hideShimmer()
                    fillPopularAdapter(response.toMutableList())


                }


            } else {

                viewModel.callRecipeFoodsApi(viewModel.popularQueries())


            }


        }

    }





    private fun loadPopularData(){



        viewModel.recipeData.observe(viewLifecycleOwner) { response ->


            when (response) {

                is MyResponseNetworkRequest.LOADING -> {


                    binding.popularFoodsRecy.showShimmer()
                }

                is MyResponseNetworkRequest.SUCCESS -> {
                    binding.popularFoodsRecy.hideShimmer()
                    response.data?.let {data->

                        if (data.results!!.isNotEmpty()) {


                            fillPopularAdapter(data.results.toMutableList())














                        }


                    }


                }

                is MyResponseNetworkRequest.ERROR -> {
                    binding.popularFoodsRecy.hideShimmer()

                    Snackbar.make(binding.root, response.error.toString(), Snackbar.LENGTH_SHORT)
                        .show()


                }


            }


        }




    }





    private fun fillPopularAdapter(list: MutableList<ResponseRecipes.Result>) {

        popularAdapter.setData(list)

        autoScroolRecycler(list)


    }


    private fun initPopularRecycler() {


        val snapHelper = LinearSnapHelper()

        binding.popularFoodsRecy.adapter = popularAdapter
        binding.popularFoodsRecy.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        snapHelper.attachToRecyclerView(binding.popularFoodsRecy)




        popularAdapter.setOnItemClickListener {


            findNavController().navigate(RecipeFragmentDirections.actionToDetailFragment(it.id!!))


        }

    }


    private fun autoScroolRecycler(list: List<ResponseRecipes.Result>) {


        lifecycleScope.launchWhenCreated {

            repeat(100) {

                delay(5000)

                if (autoScrollIndex < list.size) {


                    autoScrollIndex++

                } else {

                    autoScrollIndex = 0
                }


                binding.popularFoodsRecy.smoothScrollToPosition(autoScrollIndex)


            }


        }


    }




    private fun callRecentRecipeApi() {


        initRecentRecycler()

        viewModel.readRecentRecipes.onceObserve(viewLifecycleOwner) { database ->

            if (database.isNotEmpty() && database.size > 1 && !args.isUpdateData) {

                database[1].foodRecipe.results?.let { result ->

                    binding.shimmerRecentRecycler.hideShimmer()
                    recentAdapter.setData(result)


                }


            } else {

                viewModel.callRecentRecipesApi(
                    viewModel.recentQueries()
                )



            }


        }


    }


    private fun loadRecentData(){


        viewModel.recentLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is MyResponseNetworkRequest.LOADING -> {

                    binding.shimmerRecentRecycler.showShimmer()


                }


                is MyResponseNetworkRequest.SUCCESS -> {

                    binding.shimmerRecentRecycler.hideShimmer()



                    response.data?.let {data->


                        if (data.results!!.isNotEmpty()){

                            recentAdapter.setData(data.results)





                        }








                    }








                }


                is MyResponseNetworkRequest.ERROR -> {
                    binding.shimmerRecentRecycler.hideShimmer()

                    Snackbar.make(binding.root, response.error.toString(), Snackbar.LENGTH_SHORT)
                        .show()

                }


            }


        }


    }









    private fun initRecentRecycler() {


        binding.shimmerRecentRecycler.adapter = recentAdapter
        binding.shimmerRecentRecycler.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )


        recentAdapter.setOnItemClickListener {

findNavController().navigate(RecipeFragmentDirections.actionToDetailFragment(it))




        }





    }



}