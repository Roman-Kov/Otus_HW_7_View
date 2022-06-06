package otus.homework.customview.ui

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import otus.homework.customview.R
import otus.homework.customview.data.BarChartParam
import otus.homework.customview.util.getRandomColor

class BarChartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_bar_chart, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCategory { category ->
            showTitle(category)
            drawChart(category)
        }
    }

    private fun getCategory(onSuccess: (category: String) -> Unit) {
        arguments?.getString(CATEGORY)?.let {
            onSuccess(it)
        }
    }

    private fun showTitle(name: String) {
        view?.findViewById<TextView>(R.id.title)?.apply {
            text = name
        }
    }

    private fun drawChart(categoryName: String) {
        val data = (activity as? MainActivity)?.let { mainActivity ->
            mainActivity.dataProvider.getExpenses(categoryName).sortedBy { it.time }
        }?.map { BarChartParam(it.time, it.amount.toFloat(), getRandomColor()) } ?: listOf()
        val barChart = view?.findViewById<BarChartView>(R.id.barChart)
        barChart?.drawChart(data)
        drawAnimatedCharts(data)
    }

    private fun drawAnimatedCharts(data: List<BarChartParam>) {
        val container = view?.findViewById<LinearLayout>(R.id.animatedChartsContainer) ?: return
        val animators = mutableListOf<Animator>()
        val maxWeight = (data.maxOfOrNull { it.weight }?.takeIf { it != 0f } ?: 1).toInt()
        val ratio = MAX_BAR_HEIGHT.toFloat() / maxWeight
        data.forEach { params ->
            val bar = View(context).apply {
                layoutParams = ViewGroup.LayoutParams(BAR_WIDTH, MIN_BAR_HEIGHT)
                setBackgroundColor(params.color)
            }
            val currentHeight = (params.weight * ratio).toInt()
            container.addView(bar)
            animators.add(
                ValueAnimator.ofInt(MIN_BAR_HEIGHT, currentHeight).apply {
                    duration = 300
                    interpolator = OvershootInterpolator()
                    addUpdateListener { va ->
                        val layoutParam = bar.layoutParams
                        layoutParam.height = va.animatedValue as Int
                        bar.layoutParams = layoutParam
                    }
                }
            )
        }
        AnimatorSet().apply {
            playTogether(animators)
            start()
        }
    }

    companion object {

        private const val CATEGORY = "CATEGORY"
        private const val MAX_BAR_HEIGHT = 300
        private const val MIN_BAR_HEIGHT = 20
        private const val BAR_WIDTH = 150

        fun createInstance(category: String) = BarChartFragment().apply {
            val args = bundleOf().apply {
                putString(CATEGORY, category)
            }
            arguments = args
        }
    }
}