package com.example.recipefood.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.request.CachePolicy
import com.example.recipefood.R
import com.example.recipefood.adapters.InstructionsAdapter
import com.example.recipefood.adapters.SimilarAdapter
import com.example.recipefood.adapters.StepsAdapter
import com.example.recipefood.data.database.entity.FavoriteEntity
import com.example.recipefood.data.model.detail.ResponseDetail
import com.example.recipefood.data.model.detail.ResponseSimilar
import com.example.recipefood.databinding.FragmentDetailBinding
import com.example.recipefood.ui.recipe.RecipeFragmentDirections
import com.example.recipefood.utils.Constants
import com.example.recipefood.utils.MyResponseNetworkRequest
import com.example.recipefood.utils.NetworkConnection
import com.example.recipefood.viewmodel.detail.DetailViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject


@AndroidEntryPoint
class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding


    private val detailViewModel: DetailViewModel by viewModels()


    private val args: DetailFragmentArgs by navArgs()

    var recipeId = 0


    @Inject
    lateinit var networkConnection: NetworkConnection


    @Inject
    lateinit var similarAdapter: SimilarAdapter

    @Inject
    lateinit var stepAdapter: StepsAdapter


    @Inject
    lateinit var instructionAdapter: InstructionsAdapter


    private var isInDatabase = false

    private var isFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.let {
            recipeId = args.recipeId

            if (recipeId > 0) {


                existInDatabase(recipeId)


            }

        }



        lifecycleScope.launchWhenStarted {


            networkConnection.checkNetwork().collect { state ->

                delay(200)
                if (isInDatabase.not()) {

                    initInternetLayout(state)
                    if (state) {

                        callDetailApi()



                    }


                }


                if (state) {

                    detailViewModel.getSimilarRecipes(recipeId, Constants.MY_API_KEY)


                }


            }


        }

        loadSimilarData()












        binding.btnBack.setOnClickListener { findNavController().popBackStack() }















    }


    private fun callDetailApi() {
        detailViewModel.getDetailsData(recipeId, Constants.MY_API_KEY)
        detailViewModel.detailsLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {

                is MyResponseNetworkRequest.LOADING -> {

                    binding.progressLoading.visibility = View.VISIBLE

                    binding.contentLay.visibility = View.GONE

                }


                is MyResponseNetworkRequest.ERROR -> {

                    binding.progressLoading.visibility = View.GONE
                    binding.contentLay.visibility = View.VISIBLE

                    Snackbar.make(binding.root, response.error.toString(), Snackbar.LENGTH_SHORT)
                        .show()


                }


                is MyResponseNetworkRequest.SUCCESS -> {

                    binding.progressLoading.visibility = View.GONE

                    binding.contentLay.visibility = View.VISIBLE


                    response.data?.let { data ->



    initViewsWithData(data)

                    }
                }


            }


        }


    }


    private fun existInDatabase(id: Int) {


        detailViewModel.existDetailData(id)

        detailViewModel.existLiveData.observe(viewLifecycleOwner) {

            isInDatabase = it

            if (it) {

                readDetailFromDb()
                binding.contentLay.visibility = View.VISIBLE


            }


        }


    }


    private fun readDetailFromDb() {


        detailViewModel.readDetailData(recipeId).observe(viewLifecycleOwner) {

            initViewsWithData(it.detailEntity)


        }


    }


    @SuppressLint("SetTextI18n")
    private fun initViewsWithData(data: ResponseDetail) {
        binding.apply {
            //Favorite
            detailViewModel.existFavorite(data.id!!)
            existFavorite()
            //Click favorites
            btnFavorite.setOnClickListener {
                if (isFavorite) deleteFavorite(data) else saveFavorite(data)
            }
            //Image
            val imageSplit = data.image!!.split("-")
            val imageSize = imageSplit[1].replace(Constants.OLD_IMAGE_SIZE, Constants.NEW_IMAGE_SIZE)
            coverImg.load("${imageSplit[0]}-$imageSize") {
                crossfade(true)
                crossfade(800)
                memoryCachePolicy(CachePolicy.ENABLED)
                error(R.drawable.ic_placeholder)
            }
            //Source
            data.sourceUrl?.let { source ->
                binding.sourceBook.visibility = View.VISIBLE


           binding.sourceBook.setOnClickListener {

               val intent = Intent(Intent.ACTION_VIEW, Uri.parse(source))

               startActivity(intent)
           }
            }
            //Text
            if (data.readyInMinutes!!.toInt() > 60) {
                val hour = (data.readyInMinutes.toInt()) / 60
                val minutes = (data.readyInMinutes.toInt()) % 60
                binding.timeTxt.text = "${hour.toString()} : ${minutes.toString()}"
            } else {
                binding.timeTxt.text = data.readyInMinutes.toString()+" min"
            }
            foodName.text = data.title
            //Desc
            val summary = HtmlCompat.fromHtml(data.summary!!, HtmlCompat.FROM_HTML_MODE_COMPACT)
            txtDesc.text = summary
            //Toggle
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
            //Like
            likeTxt.text = data.aggregateLikes.toString()
            //price
            priceTxt.text = "${data.pricePerServing} $"
            //Healthy
            healthyTxt.text = data.healthScore.toString()
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
            //Instructions
            instructionCounts.text = "${data.extendedIngredients!!.size} ${getString(R.string.items)}"
            val instructions = HtmlCompat.fromHtml(data.instructions!!, HtmlCompat.FROM_HTML_MODE_COMPACT)
            instructionDesc.text = instructions
            initInstructionAdapter(data.extendedIngredients.toMutableList())
            //Steps
            initStepAdapter(data.analyzedInstructions!![0].steps!!.toMutableList())
            stepShowMore.setOnClickListener {
                val direction = DetailFragmentDirections.actionToStepsFragment(data.analyzedInstructions[0])
                findNavController().navigate(direction)
            }
            //Diets
            setUpChip(data.diets!!.toMutableList(), dietsChipGroup)
        }
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


        }else{

            binding.contentLay.visibility=View.GONE
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


        }else{

            binding.contentLay.visibility=View.GONE
        }


    }


    @SuppressLint("SuspiciousIndentation")
    private fun initViewsSimilarAdapter(data: MutableList<ResponseSimilar.ResponseSimilarItem>) {


        if (data.isNotEmpty()) {

            similarAdapter.setData(data)









            binding.similarRecycler.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            binding.similarRecycler.adapter = similarAdapter


            similarAdapter.setOnClickListener {

                findNavController().navigate(RecipeFragmentDirections.actionToDetailFragment(it))


            }

        }else{

            binding.contentLay.visibility=View.GONE
        }

    }

    private fun loadSimilarData(){





        detailViewModel.similarLiveData.observe(viewLifecycleOwner) { similar ->


            when (similar) {

                is MyResponseNetworkRequest.LOADING -> {

                    binding.similarRecycler.showShimmer()


                }

                is MyResponseNetworkRequest.ERROR -> {

                    binding.similarRecycler.hideShimmer()

                    Snackbar.make(binding.root, similar.error.toString(), Snackbar.LENGTH_SHORT)
                        .show()


                }


                is MyResponseNetworkRequest.SUCCESS -> {
                    binding.similarRecycler.hideShimmer()

similar.data?.let { data ->

    if (data.isNotEmpty()) {

        initViewsSimilarAdapter(data)


    }
}
                }


            }


        }
    }


    private fun saveFavorite(data: ResponseDetail) {


        val entity = FavoriteEntity(data.id!!, data)

        detailViewModel.saveFavorites(entity)


    }

    private fun deleteFavorite(data: ResponseDetail) {

        val entity = FavoriteEntity(data.id!!, data)


        detailViewModel.deleteFavorite(entity)


    }


    private fun existFavorite() {

        detailViewModel.existFavorite.observe(viewLifecycleOwner) {

            isFavorite = it

            if (it) {

                binding.btnFavorite.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.tart_orange
                    )
                )

                binding.btnFavorite.setImageResource(R.drawable.ic_heart_circle_minus)


            } else {


                binding.btnFavorite.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.persianGreen
                    )
                )

                binding.btnFavorite.setImageResource(R.drawable.ic_heart_circle_plus)
            }


        }


    }


    private fun initInternetLayout(isConnected: Boolean) {
        binding.checkInternet.isVisible = isConnected.not()
    }


}