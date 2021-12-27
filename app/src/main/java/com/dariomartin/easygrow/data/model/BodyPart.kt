package com.dariomartin.easygrow.data.model

import android.content.Context
import com.dariomartin.easygrow.R

enum class BodyPart {
    ARM_L, ARM_R, LEG_L, LEG_R, ABS_L, ABS_R;

    fun getFullName(context: Context): String {
        return context.getString(
            when (this) {
                ARM_L -> R.string.arm_l
                ARM_R -> R.string.arm_r
                LEG_L -> R.string.leg_l
                LEG_R -> R.string.leg_r
                ABS_L -> R.string.abd_l
                ABS_R -> R.string.abd_r
            }
        )
    }
}