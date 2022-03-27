package otus.homework.customview.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_UP
import android.view.View
import kotlin.math.atan
import kotlin.math.pow

class PieChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val paint = Paint()
    private var radius = 0f
    private val innerRadius by lazy { radius - 200 }
    private var centerX = 0f
    private var centerY = 0f
    private val borderRect by lazy { RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius) }
    private val innerRect by lazy {
        RectF(
            centerX - innerRadius,
            centerY - innerRadius,
            centerX + innerRadius,
            centerY + innerRadius
        )
    }
    private var startAngle = 0f
    private var endAngle = 0f
    private var params: List<Pair<Float, Int>>? = null
    private var _x: Float = UNDEFINED
    private var _y: Float = UNDEFINED

    init {
        paint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        //val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val parentWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val parentHeightSize = MeasureSpec.getSize(heightMeasureSpec)
        val size = parentWidthSize.coerceAtMost(parentHeightSize)
        radius = size / 2f
        centerX = radius
        centerY = radius
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        startAngle = 0f
        if (params == null) return
        val touchInCircle = touchPointInCircle()
        params?.forEach { params ->
            endAngle = params.first
            if (touchInCircle && touchPointInCurrentCircleSector()) paint.color = Color.RED
            else paint.color = params.second
            canvas.drawArc(borderRect, startAngle, endAngle, true, paint)
            startAngle += endAngle
        }

        paint.color = Color.WHITE
        canvas.drawOval(innerRect, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == ACTION_DOWN) {
            _x = event.x
            _y = event.y
            invalidate()
        }
        if (event?.action == ACTION_UP) {
            _x = UNDEFINED
            _y = UNDEFINED
            invalidate()
        }

        return true
    }

    private fun touchPointInCircle(): Boolean {
        if (_x == UNDEFINED || _y == UNDEFINED) return false
        val conditionLeft = (_x - centerX).pow(2) + (_y - centerY).pow(2)
        if (conditionLeft <= innerRadius.pow(2)) return false
        return conditionLeft <= radius.pow(2)
    }

    private fun touchPointInCurrentCircleSector(): Boolean {
        val tagA = (_y - centerY) / (_x - centerX)
        val initialAngle = atan(tagA) * 180 / Math.PI
        val additionalAngle = when {
            _x >= centerX && _y >= centerY -> 0
            _x <= centerX && _y >= centerY -> 180
            _x <= centerX && _y <= centerY -> 270
            _x >= centerX && _y <= centerY -> 360
            else -> 0
        }
        val angle = initialAngle + additionalAngle
        return angle in startAngle..startAngle + endAngle
    }

    fun drawChart(params: List<Pair<Float, Int>>) {
        this.params = params
        invalidate()
    }

    companion object {

        private const val UNDEFINED = -1f
    }
}