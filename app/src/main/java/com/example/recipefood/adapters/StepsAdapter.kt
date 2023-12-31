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
import com.example.recipefood.databinding.ItemStepsBinding
import com.example.recipefood.utils.Constants
import javax.inject.Inject

class StepsAdapter @Inject constructor() :
    RecyclerView.Adapter<StepsAdapter.InstructionsViewHolder>() {

    lateinit var binding: ItemStepsBinding

    private var instructionDetail= emptyList<ResponseDetail.AnalyzedInstruction.Step>()

    inner class InstructionsViewHolder(item: View) : RecyclerView.ViewHolder(item) {


        fun onBind(oneItem:ResponseDetail.AnalyzedInstruction.Step){



binding.stepNumber.text=(adapterPosition+1).toString()




oneItem.length?.let {


    if (oneItem.length.number!!.toInt() > 60){

val hour=(oneItem.length.number)/60

        val minutes=(oneItem.length.number)%60


        binding.timeTxt.text="${hour.toString()} : ${minutes.toString()}"



    }






}



            binding.descStep.text=oneItem.step












        }






    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionsViewHolder {
        binding= ItemStepsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return InstructionsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: InstructionsViewHolder, position: Int) {
        holder.onBind(instructionDetail[position])
    }

    override fun getItemCount()=Constants.STEPS_COUNT

    override fun getItemId(position: Int)=position.toLong()

    override fun getItemViewType(position: Int)=position





    fun setData(data:List<ResponseDetail.AnalyzedInstruction.Step>){

        val instructions=StepDiffUtils(instructionDetail,data)

        val diff=DiffUtil.calculateDiff(instructions)

        instructionDetail=data

        diff.dispatchUpdatesTo(this)





    }



    class StepDiffUtils(
        private val oldItem: List<ResponseDetail.AnalyzedInstruction.Step>,
        private val newItem: List<ResponseDetail.AnalyzedInstruction.Step>
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