package com.example.recipefood.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.recipefood.R
import com.example.recipefood.data.model.register.BodyRegister
import com.example.recipefood.databinding.FragmentRegisterBinding
import com.example.recipefood.utils.Constants.MY_API_KEY
import com.example.recipefood.utils.MyResponseNetworkRequest
import com.example.recipefood.utils.NetworkConnection
import com.example.recipefood.viewmodel.register.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding


    @Inject
    lateinit var networkChecker: NetworkConnection
    private val viewModel: RegisterViewModel by viewModels()

    private var email = ""

    @Inject
    lateinit var body: BodyRegister

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.bigImg.load(R.drawable.register_logo)



        binding.emailEdt.addTextChangedListener {

            if (it.toString().contains("@")) {

                email = it.toString()

                binding.emailTxtLay.error = ""


            } else {
                binding.emailTxtLay.error = getString(R.string.emailNotValid)
            }


        }





        binding.btnRegister.setOnClickListener {

            val name = binding.nameEdt.text.toString()
            val lastName = binding.lastNameEdt.text.toString()
            val userName = binding.userNameEdt.text.toString()



            body.firstName = name
            body.lastName = lastName
            body.email = email
            body.username = userName



            lifecycleScope.launchWhenStarted {


                networkChecker.checkNetwork().collect { hasInternet ->


                    if (hasInternet) {

                        viewModel.register(MY_API_KEY, body)

                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.checkConnection),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            }


        }



        loadRegister()


    }


    private fun loadRegister() {

        viewModel.registerData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is MyResponseNetworkRequest.SUCCESS -> {

response.data?.let {
    findNavController().popBackStack(R.id.registerFragment,true)
    viewModel.saveDataStore(it.username.toString(),it.hash.toString())
    findNavController().navigate(R.id.action_to_recipeFragment)

}





                }

                is MyResponseNetworkRequest.ERROR -> {
                    Snackbar.make(binding.root, response.error.toString(), Snackbar.LENGTH_SHORT)
                        .show()


                }

                is MyResponseNetworkRequest.LOADING -> {
                }


            }


        }


    }


}