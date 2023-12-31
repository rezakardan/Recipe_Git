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
import com.example.recipefood.databinding.FragmentDetailBinding
import com.example.recipefood.databinding.ItemInstuctionBinding
import com.example.recipefood.utils.Constants
import javax.inject.Inject

class InstructionsAdapter @Inject constructor() :
    RecyclerView.Adapter<InstructionsAdapter.InstructionsViewHolder>() {

    lateinit var binding: ItemInstuctionBinding

    private var instructionDetail= emptyList<ResponseDetail.ExtendedIngredient>()

    inner class InstructionsViewHolder(item: View) : RecyclerView.ViewHolder(item) {


        fun onBind(oneItem:ResponseDetail.ExtendedIngredient){



            val image="${Constants.BASE_URL_IMAGE_INGREDIANTS}${oneItem.image}"







            binding.imgInstruction.load(image){

                crossfade(true)
                crossfade(800)
                    .memoryCachePolicy(CachePolicy.ENABLED)

                    .error(R.drawable.ic_placeholder)

            }

            binding.countTxt.text="${oneItem.amount.toString()} ${oneItem.unit.toString()}"

            binding.titleInstruction.text=oneItem.name






        }






    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionsViewHolder {
        binding= ItemInstuctionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return InstructionsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: InstructionsViewHolder, position: Int) {
        holder.onBind(instructionDetail[position])
    }

    override fun getItemCount()=instructionDetail.size

    override fun getItemId(position: Int)=position.toLong()

    override fun getItemViewType(position: Int)=position





    fun setData(data:List<ResponseDetail.ExtendedIngredient>){

        val instructions=InstructionsDiffUtils(instructionDetail,data)

        val diff=DiffUtil.calculateDiff(instructions)

        instructionDetail=data

        diff.dispatchUpdatesTo(this)





    }



    class InstructionsDiffUtils(
        private val oldItem: List<ResponseDetail.ExtendedIngredient>,
        private val newItem: List<ResponseDetail.ExtendedIngredient>
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