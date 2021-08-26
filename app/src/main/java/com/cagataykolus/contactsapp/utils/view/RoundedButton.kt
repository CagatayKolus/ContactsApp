package com.cagataykolus.contactsapp.utils.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.cagataykolus.contactsapp.R

/**
 * Created by Çağatay Kölüş on 25.08.2021.
 * cagataykolus@gmail.com
 */

class RoundedButton : AppCompatButton {
    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    )

    init {
        setBackgroundResource(R.drawable.rounded_button_background)
        isAllCaps = false
        textSize = 16F

        // Text Color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
        } else {
            @Suppress("DEPRECATION")
            setTextColor(resources.getColor(R.color.white))
        }

        // Font type
        typeface = ResourcesCompat.getFont(context, R.font.montserrat_semibold)

        // Letter Spacing
        letterSpacing = 0.038f
        this.stateListAnimator = null
    }
}