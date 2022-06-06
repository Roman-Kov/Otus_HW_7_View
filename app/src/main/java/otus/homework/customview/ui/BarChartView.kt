package otus.homework.customview.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import otus.homework.customview.data.BarChartParam

class BarChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val paint = Paint()
    private var barWidth = DEFAULT_BAR_WIDTH
    private var barHeight = DEFAULT_BAR_HEIGHT
    private var params: List<BarChartParam>? = null

    init {
        paint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val currentWidth = when (widthMode) {
            MeasureSpec.EXACTLY -> {
                barWidth = (widthSize / (params?.size.takeIf { it != 0 } ?: 1)).toFloat()
                widthSize
            }
            else -> (barWidth * (params?.size.takeIf { it != 0 } ?: 1)).toInt()
        }
        val currentHeight = when (heightMode) {
            MeasureSpec.EXACTLY -> {
                barHeight = heightSize.toFloat()
                heightSize
            }
            else -> barHeight.toInt()
        }

        setMeasuredDimension(currentWidth, currentHeight)
    }

    override fun onDraw(canvas: Canvas) {
        if (params == null) return
        var currentOffset = 0f
        val maxValue = (params?.maxByOrNull { it.weight }?.weight ?: 0).toFloat()
        val percentage = maxValue / barHeight
        params?.forEach { params ->
            paint.color = params.color
            val height = params.weight / percentage
            canvas.drawRect(currentOffset, barHeight - height, currentOffset + barWidth, barHeight, paint)
            currentOffset += barWidth
        }
    }

    fun drawChart(params: List<BarChartParam>) {
        this.params = params
        invalidate()
    }

    companion object {

        private const val DEFAULT_BAR_WIDTH = 150f
        private const val DEFAULT_BAR_HEIGHT = 300f
    }
}