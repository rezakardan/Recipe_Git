package com.example.recipefood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.example.recipefood.R
import com.example.recipefood.data.model.detail.ResponseDetail
import com.example.recipefood.data.model.detail.ResponseSimilar
import com.example.recipefood.databinding.ItemSimilarBinding
import com.example.recipefood.utils.Constants
import javax.inject.Inject

class SimilarAdapter@Inject constructor():RecyclerView.Adapter<SimilarAdapter.SimilarViewHolder>() {


    lateinit var binding:ItemSimilarBinding

    private var similarDetail= emptyList<ResponseSimilar.ResponseSimilarItem>()
    inner class SimilarViewHolder(item:View):RecyclerView.ViewHolder(item){


        fun onBind(oneItem:ResponseSimilar.ResponseSimilarItem){










            binding.coverImg.load("${Constants.BASE_URL_IMAGE_SIMILAR}${oneItem.id}-${Constants.NEW_IMAGE_SIZE}"){

                crossfade(true)
                crossfade(800)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .error(R.drawable.ic_placeholder)

            }

            binding.nameTxt.text=oneItem.title



            binding.root.setOnClickListener {


                onItemClick?.let {

                    it(oneItem.id!!)
                }



            }





        }





    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        binding=ItemSimilarBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return SimilarViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
holder.onBind(similarDetail[position])    }

    override fun getItemCount()=similarDetail.size


    override fun getItemId(position: Int)=position.toLong()
    override fun getItemViewType(position: Int)=position


    private var onItemClick:((Int)->Unit)?=null

    fun setOnClickListener(listener:(Int)->Unit){

        onItemClick=listener



    }


    fun setData(data:List<ResponseSimilar.ResponseSimilarItem>){

        val similarDiff=SimilarDiffUtils(similarDetail,data)

        val diff=DiffUtil.calculateDiff(similarDiff)

        similarDetail=data

        diff.dispatchUpdatesTo(this)


    }

    class SimilarDiffUtils(private val oldItem:List<ResponseSimilar.ResponseSimilarItem>,private val newItem:List<ResponseSimilar.ResponseSimilarItem>):DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
return newItem.size       }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
return oldItem[oldItemPosition]===newItem[newItemPosition]       }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition]===newItem[newItemPosition]        }
    }


}