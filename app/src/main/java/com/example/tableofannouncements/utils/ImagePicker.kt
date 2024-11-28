package com.example.tableofannouncements.utils

import io.ak1.pix.models.Flash
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio

object ImagePicker {
     fun launchPix(): Options {
        return Options().apply {
            ratio = Ratio.RATIO_AUTO
            count = 3
            path = "Pix/Camera"
            isFrontFacing = false
            mode = Mode.Picture
            flash = Flash.Auto
        }
    }
}