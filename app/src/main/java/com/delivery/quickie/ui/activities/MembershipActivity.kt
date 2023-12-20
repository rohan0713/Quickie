package com.delivery.quickie.ui.activities

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.delivery.quickie.R
import com.delivery.quickie.databinding.ActivityMembershipBinding

class MembershipActivity : AppCompatActivity() {

    lateinit var binding: ActivityMembershipBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMembershipBinding.inflate(layoutInflater).also { binding = it }.root)

        window.statusBarColor = resources.getColor(R.color.base)

        binding.ivBack.setOnClickListener {
            finish()
        }

        // Set the initial value of translationX
        var translationX = 0f

        // Create a ValueAnimator
        val animator = ValueAnimator.ofFloat(0f, 500f)

        animator.addUpdateListener { valueAnimator ->
            // Update the translationX value
            translationX = valueAnimator.animatedValue as Float

            // Apply translation to the TextView
            binding.tvMovingText.translationX = translationX
        }

        animator.duration = 4000
        animator.repeatMode = ValueAnimator.REVERSE
        animator.repeatCount = ValueAnimator.INFINITE

        animator.start()
    }
}