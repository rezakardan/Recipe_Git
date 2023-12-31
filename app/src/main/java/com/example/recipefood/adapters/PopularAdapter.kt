package com.example.recipefood.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.example.recipefood.R
import com.example.recipefood.data.model.recipe.ResponseRecipes
import com.example.recipefood.databinding.ItemPopularFoodsBinding
import com.example.recipefood.utils.Constants
import javax.inject.Inject

class PopularAdapter@Inject constructor():RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    lateinit var binding:ItemPopularFoodsBinding

    private var recipeFoods= emptyList<ResponseRecipes.Result>()


    inner class PopularViewHolder(item:View):RecyclerView.ViewHolder(item){


        @SuppressLint("SetTextI18n")
        fun onBind(oneItem:ResponseRecipes.Result){



            binding.txtCoins.text="${oneItem.pricePerServing} $"









            binding.popularNameTxt.text=oneItem.title





val imageSplit=oneItem.image!!.split("-")







            val imageSize=imageSplit[1].replace(Constants.OLD_IMAGE_SIZE,Constants.NEW_IMAGE_SIZE)


            binding.popularImg.load("${imageSplit[0]}-${imageSize}"){
                crossfade(true)
                crossfade(800)
                memoryCachePolicy(CachePolicy.ENABLED)
                error(R.drawable.ic_placeholder)
            }





            binding.root.setOnClickListener {

                onItemClick?.let {

                    it(oneItem)


                }




            }


        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        binding=ItemPopularFoodsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PopularViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        holder.onBind(recipeFoods[position])

    }

    override fun getItemCount()=recipeFoods.size


    override fun getItemViewType(position: Int)=position

    override fun getItemId(position: Int)=position.toLong()


    private var onItemClick:((ResponseRecipes.Result)->Unit)?=null

    fun setOnItemClickListener(listener:(ResponseRecipes.Result)->Unit){


        onItemClick=listener


    }


    fun setData(data:List<ResponseRecipes.Result>){

        val popular=PopularDiffUtils(recipeFoods,data)

        val diff=DiffUtil.calculateDiff(popular)

        recipeFoods=data

        diff.dispatchUpdatesTo(this)


    }




    class PopularDiffUtils(private val oldItem:List<ResponseRecipes.Result>,private val newItem:List<ResponseRecipes.Result>):DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
return oldItem[oldItemPosition]===newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition]===newItem[newItemPosition]
        }


    }


}