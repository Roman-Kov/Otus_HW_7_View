package otus.homework.customview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bar_chart, container, false)
    }

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
        barChart?.apply {
            drawChart(data)
        }
    }

    companion object {

        private const val CATEGORY = "CATEGORY"

        fun createInstance(category: String) = BarChartFragment().apply {
            val args = bundleOf().apply {
                putString(CATEGORY, category)
            }
            arguments = args
        }
    }
}