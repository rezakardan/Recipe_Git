package com.example.recipefood.ui.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.recipefood.R
import com.example.recipefood.databinding.FragmentMenuBinding
import com.example.recipefood.viewmodel.menu.MenuViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MenuFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentMenuBinding


    private lateinit var menuViewModel: MenuViewModel


    private var chipCounter = 1

    private var chipMealsTitle = ""
    private var chipDietTitle = ""
    private var chipMealsId=0
    private var chipDietId=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        menuViewModel = ViewModelProvider(requireActivity())[MenuViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpChip(menuViewModel.mealsList(), binding.mealChipGroup)

        setUpChip(menuViewModel.dietsList(), binding.dietChipGroup)


        menuViewModel.readMenuItems.asLiveData().observe(viewLifecycleOwner) {

            chipMealsTitle = it.mealsType
            chipDietTitle = it.dietType

            updateChip(it.mealsId, binding.mealChipGroup)

            updateChip(it.dietId, binding.dietChipGroup)


        }

        binding.mealChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->


          var chip:Chip

          checkedIds.forEach {

              chip=group.findViewById(it)

              chipMealsTitle=chip.text.toString().lowercase()


              chipMealsId=it






          }




        }



        binding.dietChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->

            var chip:Chip
            checkedIds.forEach {

                chip=group.findViewById(it)

                chipDietTitle=chip.text.toString().lowercase()

chipDietId=it


            }






        }


      binding.submitBtn.setOnClickListener {

          menuViewModel.saveMenuItems(chipMealsTitle,chipMealsId,chipDietTitle,chipDietId)


findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToRecipeFragment().setIsUpdateData(true))






      }


    }


    private fun setUpChip(list: MutableList<String>, view: ChipGroup) {

        list.forEach {


            val chip = Chip(requireContext())

            val drawable =
                ChipDrawable.createFromAttributes(requireContext(), null, 0, R.style.DarkChip)

            chip.setChipDrawable(drawable)

            chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

            chip.id = chipCounter++

            chip.text = it

            view.addView(chip)

        }


    }


    private fun updateChip(id: Int, view: ChipGroup) {


        if (id != 0) {


            view.findViewById<Chip>(id).isChecked = true
        }


    }


}