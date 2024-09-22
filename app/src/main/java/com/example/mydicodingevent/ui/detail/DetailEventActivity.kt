package com.example.mydicodingevent.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.example.mydicodingevent.databinding.ActivityDetailEventBinding

class DetailEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailEventBinding
    private val viewModel: DetailEventViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventId = intent.getStringExtra("EVENT_ID") ?: return
        viewModel.fetchEventDetail(eventId)

        viewModel.eventDetail.observe(this) { event ->
            Glide.with(this)
                .load(event.imageLogo)
                .into(binding.imgEventLogo)
            binding.tvEventName.text = event.name
            binding.tvEventOwner.text = event.ownerName
            binding.tvEventTime.text = event.beginTime
            binding.tvEventQuota.text = (event.quota - event.registrants).toString()
            binding.tvEventDescription.text = HtmlCompat.fromHtml(
                event.description,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )

            binding.btnRegister.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
                startActivity(intent)
            }
        }
    }
}