package com.delivery.quickie.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.delivery.quickie.R
import com.delivery.quickie.databinding.ActivityPostsBinding
import com.delivery.quickie.network.ProfileResponse
import com.squareup.picasso.Picasso
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PostsActivity : AppCompatActivity() {

    lateinit var binding: ActivityPostsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityPostsBinding.inflate(layoutInflater).also { binding = it }.root)

        val s = intent.getStringExtra("imageId")
        Picasso.get().load("https://quickie-backend.vercel.app/api/images/$s").into(binding.ivPost);

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun getData(response: ProfileResponse) {
        binding.tvUsername.text = response.username
        Glide.with(this).load("https://quickie-backend.vercel.app/api/images/profile/${response.profile}").into(binding.ivProfile)
    }

    override fun onStart() {
        super.onStart()
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
