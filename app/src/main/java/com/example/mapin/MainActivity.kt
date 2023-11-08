package com.example.mapin

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.mapin.databinding.ActivityMainBinding
import android.animation.Animator

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navController = findNavController(R.id.nav_host_fragment_content_main)


        binding.fab.setOnClickListener {
            if (View.GONE == binding.fabBGLayout.visibility) {
                showFABMenu()
            } else {
                closeFABMenu()
            }
        }

        binding.fabBGLayout.setOnClickListener { closeFABMenu() }

        binding.fab1.setOnClickListener {
            //TODO:가게 이름으로 검색
            navController.navigate(R.id.action_FirstFragment_to_searchShop)
            closeFABMenu()
        }
        binding.fab2.setOnClickListener {
            //TODO:지역별 검색
            navController.navigate(R.id.action_FirstFragment_to_searchLocation)
            closeFABMenu()
        }
        binding.fab3.setOnClickListener {
            //TODO:제품별 검색
            navController.navigate(R.id.action_FirstFragment_to_searchCategory)
            closeFABMenu()
        }
        binding.fab4.setOnClickListener {
            //TODO:게시물 작성
        }
        binding.fab5.setOnClickListener {
            //TODO:분실물 등록
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


    private fun showFABMenu() {
        binding.fabLayout1.visibility = View.VISIBLE
        binding.fabLayout2.visibility = View.VISIBLE
        binding.fabLayout3.visibility = View.VISIBLE
        binding.fabLayout4.visibility = View.VISIBLE
        binding.fabLayout5.visibility = View.VISIBLE
        binding.fabBGLayout.visibility = View.VISIBLE
        binding.fab.animate().rotationBy(45F)
        binding.fabLayout1.animate().translationY(-resources.getDimension(R.dimen.standard_75))
        binding.fabLayout2.animate().translationY(-resources.getDimension(R.dimen.standard_120))
        binding.fabLayout3.animate().translationY(-resources.getDimension(R.dimen.standard_165))
        binding.fabLayout4.animate().translationY(-resources.getDimension(R.dimen.standard_210))
        binding.fabLayout5.animate().translationY(-resources.getDimension(R.dimen.standard_255))
    }

    private fun closeFABMenu() {
        binding.fabBGLayout.visibility = View.GONE
        binding.fab.animate().rotation(0F)
        binding.fabLayout1.animate().translationY(0f)
        binding.fabLayout2.animate().translationY(0f)
        binding.fabLayout3.animate().translationY(0f)
        binding.fabLayout3.animate().translationY(0f)
        binding.fabLayout4.animate().translationY(0f)
        binding.fabLayout5.animate().translationY(0f)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {}
                override fun onAnimationEnd(animator: Animator) {
                    if (View.GONE == binding.fabBGLayout.visibility) {
                        binding.fabLayout1.visibility = View.GONE
                        binding.fabLayout2.visibility = View.GONE
                        binding.fabLayout3.visibility = View.GONE
                        binding.fabLayout4.visibility = View.GONE
                        binding.fabLayout5.visibility = View.GONE
                    }
                }

                override fun onAnimationCancel(animator: Animator) {}
                override fun onAnimationRepeat(animator: Animator) {}
            })

    }
}