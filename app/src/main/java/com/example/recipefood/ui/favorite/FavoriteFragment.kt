package com.example.recipefood.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipefood.R
import com.example.recipefood.adapters.FavoriteAdapter
import com.example.recipefood.databinding.FragmentFavoriteBinding
import com.example.recipefood.ui.recipe.RecipeFragmentDirections
import com.example.recipefood.viewmodel.favorite.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FavoriteFragment : Fragment() {

lateinit var binding: FragmentFavoriteBinding


@Inject
lateinit var favoriteAdapter: FavoriteAdapter

private val viewModel:FavoriteViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



viewModel.allFavorites.observe(viewLifecycleOwner){


    if (it.isNotEmpty()){
        binding.txtEmpty.visibility=View.GONE

        binding.favoriteRecy.visibility=View.VISIBLE
        favoriteAdapter.setData(it)



        binding.favoriteRecy.adapter=favoriteAdapter

        binding.favoriteRecy.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        favoriteAdapter.setOnItemClickListener {id->

          findNavController().navigate(RecipeFragmentDirections.actionToDetailFragment(id))





        }



    }else{


        binding.txtEmpty.visibility=View.VISIBLE

        binding.favoriteRecy.visibility=View.GONE
    }



}





    }








}