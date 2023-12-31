package com.example.recipefood.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.recipefood.R
import com.example.recipefood.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.github.inflationx.viewpump.ViewPumpContextWrapper

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding





  private  lateinit var navHost:NavHostFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        navHost=supportFragmentManager.findFragmentById(R.id.fragmentContainerView)as NavHostFragment


        binding.bottomNavMain.background=null

        binding.bottomNavMain.setupWithNavController(navHost.navController)


navHost.navController.addOnDestinationChangedListener(object:NavController.OnDestinationChangedListener{
    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when(destination.id){

            R.id.registerFragment->{binding.bottomAppBarMain.visibility=View.GONE
            binding.btnFabMainMenu.visibility=View.GONE}
            R.id.splashFragment->{binding.bottomAppBarMain.visibility=View.GONE
                binding.btnFabMainMenu.visibility=View.GONE}

            R.id.detailFragment->{
                binding.bottomAppBarMain.visibility=View.GONE
                binding.btnFabMainMenu.visibility=View.GONE
            }



            else->{
                binding.bottomAppBarMain.visibility=View.VISIBLE
                binding.btnFabMainMenu.visibility=View.VISIBLE
            }

        }
    }
})



binding.btnFabMainMenu.setOnClickListener {


    navHost.navController.navigate(R.id.action_To_menuFragment)



}




    }


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }


    override fun onNavigateUp(): Boolean {
        return navHost.navController.navigateUp()||super.onNavigateUp()
    }
}