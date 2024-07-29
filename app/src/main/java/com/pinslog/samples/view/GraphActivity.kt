package com.pinslog.samples.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.asLiveData
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.pinslog.samples.R
import com.pinslog.samples.databinding.ActivityGraphBinding
import dagger.hilt.android.AndroidEntryPoint


const val LOG_TAG = "FakeDataCollectTest"
private const val SHOW_DATA_COUNT = 35f

@AndroidEntryPoint
class GraphActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGraphBinding
    private val viewModel: GraphViewModel by viewModels()

    // 테스트 데이터 엔트리
    private val fakeDataEntries = mutableListOf<Entry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraphBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initChart(binding.testChart)

        viewModel.fakeDataStateFlow.asLiveData().observe(this@GraphActivity) { data ->
            data?.let { num ->
                Log.d(LOG_TAG, "observe: $num")
                addFakeData(num)
            }

        }

        binding.startBtn.setOnClickListener {
            viewModel.startToGetFakeData(isContinue = true)
        }

        binding.stopBtn.setOnClickListener {
            viewModel.startToGetFakeData(isContinue = false)
        }
    }

    private fun initChart(testChart: LineChart) {
        testChart.apply {
            setDrawGridBackground(false) // 격자 제거
            description.isEnabled = false
            xAxis.setDrawGridLines(false)
            axisLeft.setDrawGridLines(false)
            getAxis(YAxis.AxisDependency.LEFT).isEnabled = false
            axisLeft.axisMaximum = 100f
            axisLeft.axisMinimum = -100f

            xAxis.isEnabled = false
            getAxis(YAxis.AxisDependency.RIGHT).isEnabled = false
            setTouchEnabled(false)
            isDragEnabled = false
            setScaleEnabled(true)
            setPinchZoom(false)
            setDrawBorders(true)
            legend.isEnabled = false
            clearData()

            // 실질적인 그래프 그려지는 부분
            val fakeDataSet = LineDataSet(fakeDataEntries, "test").apply {
                lineWidth = 2.5f
                color = getColor(R.color.teal_700)
                setDrawCircles(false)
            }
            val iLineDataSets = mutableListOf<ILineDataSet>()
            iLineDataSets.add(fakeDataSet)
            val lineData = LineData(iLineDataSets)
            data = lineData
        }
    }

    private fun addFakeData(fakeData: Int) {
        shiftList()
        fakeDataEntries.add(Entry(SHOW_DATA_COUNT, fakeData.toFloat()))
        val fakeLineDataSet = LineDataSet(fakeDataEntries, "test").apply {
            lineWidth = 2.5f
            color = getColor(R.color.teal_700)
            setDrawCircles(false)
        }
        val iLineDataSets = mutableListOf<ILineDataSet>()
        iLineDataSets.add(fakeLineDataSet)
        binding.testChart.data = LineData(iLineDataSets)
        binding.testChart.invalidate()
    }

    private fun shiftList() {
        fakeDataEntries.removeAt(0)
        for (i in 1 until SHOW_DATA_COUNT.toInt() - 1) {
            val entry = fakeDataEntries[i]
            entry.x -= 1
            fakeDataEntries[i - 1] = entry
        }
    }

    private fun clearData() {
        fakeDataEntries.clear()

        for (i in 0 until SHOW_DATA_COUNT.toInt()) {
            fakeDataEntries.add(Entry(i.toFloat(), 0f))
        }
        binding.testChart.invalidate()
    }

}