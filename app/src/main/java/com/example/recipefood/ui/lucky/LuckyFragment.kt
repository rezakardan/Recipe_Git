package com.example.recipefood.ui.lucky

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.request.CachePolicy
import com.example.recipefood.R
import com.example.recipefood.adapters.InstructionsAdapter
import com.example.recipefood.adapters.StepsAdapter
import com.example.recipefood.data.model.detail.ResponseDetail
import com.example.recipefood.data.model.lucky.ResponseLucky
import com.example.recipefood.databinding.FragmentLuckyBinding
import com.example.recipefood.ui.detail.DetailFragmentDirections
import com.example.recipefood.utils.Constants
import com.example.recipefood.utils.MyResponseNetworkRequest
import com.example.recipefood.utils.NetworkConnection
import com.example.recipefood.viewmodel.lucky.LuckyViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LuckyFragment : Fragment() {

lateinit var binding: FragmentLuckyBinding



@Inject
lateinit var stepAdapter:StepsAdapter


@Inject
lateinit var instructionAdapter: InstructionsAdapter


@Inject
lateinit var networkConnection: NetworkConnection



private val viewModel:LuckyViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLuckyBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launchWhenStarted {






        networkConnection.checkNetwork().collect{state->



            if (state){


                binding.contentLay.visibility=View.VISIBLE

                binding.checkInternet.visibility=View.GONE


                viewModel.callLuckyApi(viewModel.luckyQueries())


            }else{

                binding.contentLay.visibility=View.GONE

                binding.checkInternet.visibility=View.VISIBLE

            }






        }

        }

        loadDetailData()


    }





    private fun loadDetailData(){

        viewModel.luckyLiveData.observe(viewLifecycleOwner){response->

            when(response){


                is MyResponseNetworkRequest.LOADING->{


                    binding.progressLoading.visibility=View.VISIBLE

                    binding.contentLay.visibility=View.GONE



                }

                is MyResponseNetworkRequest.ERROR->{


                    binding.progressLoading.visibility=View.GONE

                    Snackbar.make(binding.root,response.error.toString(),Snackbar.LENGTH_SHORT).show()


                    binding.contentLay.visibility=View.VISIBLE







                }


                is MyResponseNetworkRequest.SUCCESS->{


                    binding.contentLay.visibility=View.VISIBLE

                    binding.progressLoading.visibility=View.GONE


                    response.data?.let {

                        initViewsWithData(it.recipes!![0])





                    }





                }



            }







        }





    }




    private fun initViewsWithData(data:ResponseLucky.Recipe){


        val imgSplit=data.image!!.split("-")

        val imgSize=imgSplit[1].replace(Constants.OLD_IMAGE_SIZE,Constants.NEW_IMAGE_SIZE)

        binding.coverImg.load("${imgSplit[0]}-${imgSize}"){

            crossfade(true)
            crossfade(800)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .error(R.drawable.ic_placeholder)
        }







        if (data.readyInMinutes!!.toInt() > 60) {


            val hour = (data.readyInMinutes.toInt()) / 60

            val minutes = (data.readyInMinutes.toInt()) % 60

            binding.timeTxt.text = "${hour.toString()} : ${minutes.toString()}"


        } else {

            binding.timeTxt.text = data.readyInMinutes.toString()

        }


        val htmlFormatter = HtmlCompat.fromHtml(data.summary!!, HtmlCompat.FROM_HTML_MODE_COMPACT)

        binding.txtDesc.text = htmlFormatter


        binding.foodName.text = data.title




        if (data.cheap!!) {

            binding.cheap.compoundDrawables[1].setTint(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.caribbean_green
                )
            )

            binding.cheap.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.caribbean_green
                )
            )


        }

        if (data.veryPopular!!) {

            binding.popularTxt.compoundDrawables[1].setTint(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tart_orange
                )
            )
            binding.popularTxt.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tart_orange
                )
            )

        }

        if (data.vegan!!) {

            binding.veganIcon.compoundDrawables[1].setTint(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.caribbean_green
                )
            )
            binding.veganIcon.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.caribbean_green
                )
            )

        }

        if (data.dairyFree!!) {
            binding.dairyIcon.compoundDrawables[1].setTint(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.caribbean_green
                )
            )
            binding.dairyIcon.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.caribbean_green
                )
            )

        }


        binding.likeTxt.text = data.aggregateLikes.toString()

        binding.priceTxt.text = data.pricePerServing.toString() + "$"



        binding.healthyTxt.text = data.healthScore.toString()

        when (data.healthScore) {

            in 0..59 -> {
                binding.healthyTxt.compoundDrawables[1].setTint(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.tart_orange
                    )
                )

                binding.healthyTxt.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.tart_orange
                    )
                )
            }
            in 60..89 -> {
                binding.healthyTxt.compoundDrawables[1].setTint(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.chineseYellow
                    )
                )
                binding.healthyTxt.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.chineseYellow
                    )
                )
            }


            in 90..100 -> {
                binding.healthyTxt.compoundDrawables[1].setTint(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.caribbean_green
                    )
                )

                binding.healthyTxt.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.caribbean_green
                    )
                )

            }
        }

        binding.instructionCounts.text = data.extendedIngredients!!.size.toString() + " items"

        val htmlFormaterInstruction =
            HtmlCompat.fromHtml(data.instructions!!, HtmlCompat.FROM_HTML_MODE_COMPACT)

        binding.instructionDesc.text = htmlFormaterInstruction

        data.sourceUrl?.let { source ->

            binding.sourceBook.visibility = View.VISIBLE


            binding.sourceBook.setOnClickListener {

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(source))

                startActivity(intent)
            }

        }


        initInstructionAdapter(data.extendedIngredients.toMutableList())

        initStepAdapter(data.analyzedInstructions!!.get(0).steps!!.toMutableList())


        binding.stepShowMore.setOnClickListener {

            findNavController().navigate(
                DetailFragmentDirections.actionToStepsFragment(
                    data.analyzedInstructions.get(0)
                )
            )


        }




        setUpChip(data.diets!!.toMutableList(), binding.dietsChipGroup)


    }


    private fun setUpChip(list: MutableList<String>, view: ChipGroup) {

        list.forEach {

            val chip = Chip(requireContext())

            val drawable =
                ChipDrawable.createFromAttributes(requireContext(), null, 0, R.style.DietChip)

            chip.setChipDrawable(drawable)
            chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.darkGray))
            chip.text = it

            view.addView(chip)


        }


    }

    private fun initInstructionAdapter(list: MutableList<ResponseDetail.ExtendedIngredient>) {

        if (list.isNotEmpty()) {


            instructionAdapter.setData(list)

            binding.instructionListRecy.adapter = instructionAdapter

            binding.instructionListRecy.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        }

    }


    private fun initStepAdapter(list: MutableList<ResponseDetail.AnalyzedInstruction.Step>) {

        if (list.isNotEmpty()) {


            Constants.STEPS_COUNT = if (list.size < 3) {
                list.size
            } else {
                3
            }


            stepAdapter.setData(list)

            binding.stepRecycler.adapter = stepAdapter

            binding.stepRecycler.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            if (list.size > 3) {


                binding.stepShowMore.visibility = View.VISIBLE

            }


        }


    }

}