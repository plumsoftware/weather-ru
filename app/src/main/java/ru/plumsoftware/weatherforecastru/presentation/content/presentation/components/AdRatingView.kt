package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRatingBar
import com.yandex.mobile.ads.nativeads.Rating

class AdRatingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatRatingBar(context, attrs, defStyleAttr), Rating {

    override fun getRating(): Float = super.getRating()

    override fun setRating(rating: Float) {
        super.setRating(rating)
    }
}
