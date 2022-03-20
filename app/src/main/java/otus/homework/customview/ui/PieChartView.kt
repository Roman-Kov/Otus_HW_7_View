package otus.homework.customview.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class PieChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val paint = Paint()
    private val radius = 400f
    private val innerRadius = 250f
    private val centerX = 500f
    private val centerY = 500f
    private val borderRect = RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
    private val innerRect =
        RectF(centerX - innerRadius, centerY - innerRadius, centerX + innerRadius, centerY + innerRadius)
    private var startAngle = 0f
    private var endAngle = 0f
    private var params: List<Pair<Float, Int>>? = null

    init {
        paint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        if (params == null) return
        params?.forEach { params ->
            endAngle = params.first
            paint.color = params.second
            canvas.drawArc(borderRect, startAngle, endAngle, true, paint)
            startAngle += endAngle
        }

        paint.color = Color.WHITE
        canvas.drawOval(innerRect, paint)
    }

    fun drawChart(params: List<Pair<Float, Int>>) {
        this.params = params
        invalidate()
    }
}