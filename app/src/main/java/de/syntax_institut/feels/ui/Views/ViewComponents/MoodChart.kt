package de.syntax_institut.feels.ui.Views.ViewComponents

import android.graphics.ColorSpace
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.alpha
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import de.syntax_institut.feels.ui.Models.MoodListViewModel
import de.syntax_institut.feels.ui.theme.deepRed


@Composable
fun MoodChart(
    viewModel: MoodListViewModel,
    modifier: Modifier = Modifier,
) {
    val moodEntries by viewModel.last7MoodEntries.collectAsState()

    AndroidView(
        factory = { context ->
            BarChart(context).apply {
                description.isEnabled = false
                setDrawGridBackground(false)
                axisRight.isEnabled = false
                axisLeft.axisMinimum = 1f



                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    granularity = 1f

                }
            }
        },
        update = { chart ->
            val entries = moodEntries.mapIndexed { index, moodEntry ->
                BarEntry(index.toFloat(), moodEntry.mood.toFloat())
            }

            val dataSet = BarDataSet(entries, "Mood").apply {
                color = android.graphics.Color.rgb(20, 152, 0)
                valueTextSize = 12f
            }

            chart.data = BarData(dataSet)
            chart.invalidate()
        },
        modifier = modifier
            .height(250.dp)
            .fillMaxWidth()
    )
}

