package com.dice.djetmovie.ui.splash

import android.animation.Animator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dice.djetmovie.databinding.ActivitySplashBinding
import com.dice.djetmovie.ui.main.MainActivity
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.animSplash.addAnimatorListener(object: Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) { }

            override fun onAnimationEnd(animation: Animator?) {
                startActivity<MainActivity>()
                finish()
            }

            override fun onAnimationCancel(animation: Animator?) { }

            override fun onAnimationStart(animation: Animator?) { }
        })
    }
}