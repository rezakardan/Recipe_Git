package com.example.recipefood.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.recipefood.BuildConfig
import com.example.recipefood.R
import com.example.recipefood.databinding.FragmentSplashBinding
import com.example.recipefood.viewmodel.register.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashFragment : Fragment() {

lateinit var binding: FragmentSplashBinding


private val viewModel: RegisterViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSplashBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.imgBg.load(R.drawable.bg_splash)

        binding.txtVersion.text="${getString(R.string.version)} : ${BuildConfig.VERSION_NAME}"


        lifecycleScope.launchWhenCreated {


            delay(2500)

            viewModel.readDataStore.asLiveData().observe(viewLifecycleOwner){


                findNavController().popBackStack(R.id.splashFragment,true)

                if (it.userName.isNotEmpty()){
                    findNavController().navigate(R.id.action_to_recipeFragment)

                }else{


                    findNavController().navigate(R.id.action_to_RegisterFragment)
                }





            }

        }



    }

}