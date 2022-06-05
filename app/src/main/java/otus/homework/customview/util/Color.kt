package otus.homework.customview.util

import android.graphics.Color
import kotlin.random.Random

fun getRandomColor(): Int {
    return Color.argb(255, Random.nextInt(0, 256), Random.nextInt(0, 256), Random.nextInt(0, 256))
}