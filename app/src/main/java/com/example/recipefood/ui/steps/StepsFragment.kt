package com.example.recipefood.ui.steps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipefood.R
import com.example.recipefood.adapters.StepsAdapter
import com.example.recipefood.data.model.detail.ResponseDetail
import com.example.recipefood.databinding.FragmentStepsBinding
import com.example.recipefood.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StepsFragment : BottomSheetDialogFragment() {
  lateinit var binding: FragmentStepsBinding





  private val args:StepsFragmentArgs by navArgs()

    @Inject
lateinit var stepAdapter:StepsAdapter

private lateinit var recivedStepData:MutableList<ResponseDetail.AnalyzedInstruction.Step>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentStepsBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        args.let {


            recivedStepData=it.data.steps!!.toMutableList()


            if (recivedStepData.isNotEmpty()){



                Constants.STEPS_COUNT=recivedStepData.size

                stepAdapter.setData(recivedStepData)

                binding.stepsList.adapter=stepAdapter
                binding.stepsList.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)




            }


        }



    }


}