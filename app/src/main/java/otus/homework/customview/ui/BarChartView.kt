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
    private var barSize = 150f
    var maxHeight = 300f
    private var params: List<BarChartParam>? = null

    init {
        paint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val parentWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val parentHeightSize = MeasureSpec.getSize(heightMeasureSpec)
        val size = parentWidthSize.coerceAtMost(parentHeightSize)

        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        if (params == null) return
        var currentOffset = 0f
        val maxValue = (params?.maxByOrNull { it.weight }?.weight ?: 0).toFloat()
        val percentage = maxValue / maxHeight
        params?.forEach { params ->
            paint.color = params.color
            val height = params.weight / percentage
            canvas.drawRect(currentOffset, maxHeight - height, currentOffset + barSize, maxHeight, paint)
            currentOffset += barSize
        }
    }

    fun drawChart(params: List<BarChartParam>) {
        this.params = params
        invalidate()
    }
}