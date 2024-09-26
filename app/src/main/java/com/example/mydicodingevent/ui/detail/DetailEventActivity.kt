package com.example.mydicodingevent.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mydicodingevent.R
import com.example.mydicodingevent.database.FavoriteEvent
import com.example.mydicodingevent.databinding.ActivityDetailEventBinding
import com.example.mydicodingevent.helper.ViewModelFactory

class DetailEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailEventBinding
    private lateinit var detailEventViewModel: DetailEventViewModel
    private val viewModel: DetailEventViewModel by viewModels()
    private var isFavorite = false
    private var favoriteEvent: FavoriteEvent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailEventViewModel = obtainViewModel(this@DetailEventActivity)
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

            // Initialize favoriteEvent with current event details
            favoriteEvent = FavoriteEvent(
                id = event.id.toString(),
                name = event.name,
                mediaCover = event.imageLogo
            )

            // Observe the favorite event status
            detailEventViewModel.getFavoriteEventById(event.id.toString()).observe(this) { favorite ->
                isFavorite = favorite != null
                updateFavoriteIcon()
            }

            binding.btnRegister.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
                startActivity(intent)
            }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }

        binding.fabFavorite.setOnClickListener {
            isFavorite = !isFavorite
            updateFavoriteIcon()

            if (isFavorite) {
                favoriteEvent?.let {
                    detailEventViewModel.insert(it)
                    Toast.makeText(this, R.string.added_to_favorite, Toast.LENGTH_SHORT).show()
                }
            } else {
                favoriteEvent?.let {
                    detailEventViewModel.delete(it)
                    Toast.makeText(this, R.string.removed_from_favorite, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateFavoriteIcon() {
        if (isFavorite) {
            binding.fabFavorite.setImageResource(R.drawable.ic_fav_filled)
        } else {
            binding.fabFavorite.setImageResource(R.drawable.ic_fav_border)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailEventViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailEventViewModel::class.java]
    }
}