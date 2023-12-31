package com.example.recipefood.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.example.recipefood.R
import com.example.recipefood.data.model.recipe.ResponseRecipes
import com.example.recipefood.databinding.ItemRecipesBinding
import com.example.recipefood.utils.Constants
import javax.inject.Inject

class RecentFoodAdapter @Inject constructor() :
    RecyclerView.Adapter<RecentFoodAdapter.RecentViewHolder>() {


    lateinit var binding: ItemRecipesBinding

    lateinit var context: Context

    private var recentFoods = emptyList<ResponseRecipes.Result>()

    inner class RecentViewHolder(item: View) : RecyclerView.ViewHolder(item) {




        fun onBind(oneItem: ResponseRecipes.Result) {


            binding.recipeNameTxt.text = oneItem.title



            val htmlFormater=HtmlCompat.fromHtml(oneItem.summary!!,HtmlCompat.FROM_HTML_MODE_COMPACT)
            binding.recipeDesc.text = htmlFormater

            binding.recipeLikeTxt.text = oneItem.aggregateLikes.toString()





            if (oneItem.readyInMinutes!! >=60){

                val oclock=(oneItem.readyInMinutes)/60

                val minutes=(oneItem.readyInMinutes)%60
                binding.recipeTimeTxt.text = "${oclock}:${minutes}"

            }else{

                binding.recipeTimeTxt.text = oneItem.readyInMinutes.toString()+"min"

            }




            binding.recipeHealthTxt.text = oneItem.healthScore.toString()


            val imageSplit = oneItem.image!!.split("-")


            val imageSize =
                imageSplit[1].replace(Constants.OLD_IMAGE_SIZE, Constants.NEW_IMAGE_SIZE)


            binding.recipeImg.load("${imageSplit[0]}-${imageSize}") {
                crossfade(true)
                crossfade(800)
                memoryCachePolicy(CachePolicy.ENABLED)
                error(R.drawable.ic_placeholder)
            }



            if (oneItem.vegan!!) {
                binding.recipeVeganTxt.compoundDrawables[1].setTint(
                    ContextCompat.getColor(
                        context,
                        R.color.caribbean_green
                    )
                )
                binding.recipeVeganTxt.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.caribbean_green
                    )
                )

            } else {
                binding.recipeVeganTxt.compoundDrawables[1].setTint(
                    ContextCompat.getColor(
                        context,
                        R.color.darkGray
                    )
                )
                binding.recipeVeganTxt.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.darkGray
                    )
                )
            }


            when (oneItem.healthScore) {

                in 90..100 -> {
                    binding.recipeHealthTxt.compoundDrawables[1].setTint(
                        ContextCompat.getColor(
                            context,
                            R.color.caribbean_green
                        )
                    )
                    binding.recipeHealthTxt.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.caribbean_green
                        )
                    )
                }

                in 60..89 -> {
                    binding.recipeHealthTxt.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.chineseYellow
                        )
                    )
                    binding.recipeHealthTxt.compoundDrawables[1].setTint(
                        ContextCompat.getColor(
                            context,
                            R.color.chineseYellow
                        )
                    )
                }

                in 0..59 -> {
                    binding.recipeHealthTxt.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.tart_orange
                        )
                    )

                    binding.recipeHealthTxt.compoundDrawables[1].setTint(
                        ContextCompat.getColor(
                            context,
                            R.color.tart_orange
                        )
                    )
                }


            }



            binding.root.setOnClickListener {

                onItemClick?.let {

                    it(oneItem.id!!)
                }
            }






        }


        fun initAnimation(){

            binding.root.animation=AnimationUtils.loadAnimation(context,R.anim.item_anim)


        }

        fun clearAnimation(){
            binding.root.clearAnimation()
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        context = parent.context
        binding = ItemRecipesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.onBind(recentFoods[position])
    }

    override fun getItemCount() = recentFoods.size

    override fun getItemViewType(position: Int) = position
    override fun getItemId(position: Int) = position.toLong()



    override fun onViewAttachedToWindow(holder: RecentViewHolder) {
        super.onViewAttachedToWindow(holder)
    holder.initAnimation()
    }



    override fun onViewDetachedFromWindow(holder: RecentViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.clearAnimation()
    }

    private var onItemClick: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {


        onItemClick = listener


    }


    fun setData(data: List<ResponseRecipes.Result>) {

        val recent = RecentFoodsDiffUtils(recentFoods, data)

        val diff = DiffUtil.calculateDiff(recent)

        recentFoods = data

        diff.dispatchUpdatesTo(this)


    }

    class RecentFoodsDiffUtils(
        private val oldItem: List<ResponseRecipes.Result>,
        private val newItem: List<ResponseRecipes.Result>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }


    }
}