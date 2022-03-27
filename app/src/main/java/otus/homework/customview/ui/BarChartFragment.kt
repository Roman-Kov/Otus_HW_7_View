package otus.homework.customview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import otus.homework.customview.R

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

        showTitle()
    }

    private fun showTitle() {

        view?.findViewById<TextView>(R.id.title)?.apply {
            text = arguments?.getString(CATEGORY).orEmpty()
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