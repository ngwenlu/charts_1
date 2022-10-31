package com.example.charts_1

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.io.IOException


class MainActivity : AppCompatActivity() {

    //fuel used per day against dist travelled per day
    //lateinit var fuelDistChart: LineChart
    //lateinit var distTravelledChart: LineChart
    lateinit var companiesChart: PieChart //number of times fuel company is used
    lateinit var expenditureChart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set chart view
        //fuelDistChart = findViewById(R.id.linechart_fueluse)
        //distTravelledChart = findViewById(R.id.linechart_disttravelled)
        companiesChart = findViewById(R.id.piechart_companies)
        expenditureChart = findViewById(R.id.barchart_expenditure)

        //REPLACE: get data from firestore and store in arrays for
        // xAxis, yAxis (linechart)
        // num, labels (pie, barchart)
        //REPLACE
        val fuel_used_per_day_Array = arrayOf<Float>(5F, 3.11F, 6.32F, 1.21F, 0F, 3.44F, 4.37F)
        //REPLACE
        val dist_travelled_per_day_Array = arrayOf<Float>(20.1F, 43.71F, 22.2F, 61F, 14.31F, 66.1F, 2F)

        //val dist_travelled_per_day_Array
        //val dist_date_ordered_Array
        //REPLACE
        val times_per_month_company_used_Array = arrayOf<Int>(4, 4, 7, 3, 4, 5)
        //REPLACE
        val companies_used_ordered_Array = arrayOf<String>("Shell", "Mobil", "39 by Esso", "32 by Caltex", "29 by BP", "8 by SPC")
        //REPLACE
        val total_money_spent_per_month_Array = arrayOf<Float>(500F, 239.11F, 432.01F, 777.7F, 444.16F, 333.33F, 201.30F, 100.1F, 321.0F, 212.2F, 111.1F, 99.9F)

        val months = arrayOf<String>(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )

        //draw charts
        //drawLineChart(fuel_used_per_day_Array, dist_travelled_per_day_Array, fuelDistChart, "")

        //drawLineChart(dist_travelled_per_day_Array, dist_date_ordered_Array, distTravelledChart, "")
        drawPieChart(times_per_month_company_used_Array, companies_used_ordered_Array, companiesChart)

        drawBarChart(total_money_spent_per_month_Array, expenditureChart)


    }

    private fun drawLineChart(dataListX: Array<Float>, dataListY: Array<Float>, chart: LineChart, description: String) {

        //data
        val data: ArrayList<Entry> = ArrayList()

        var i = 0
        var length: Int = dataListX.size
        while (i < length){
            data.add(Entry(dataListX[i],dataListY[i]))
            println(data[i])
            i++
       }
//        while (i < 7){
//            data.add(Entry(dataListX[i],dataListY[i]))
//
//            i++
//        }

        val lineDataSet = LineDataSet(data, "")
        val tempData = LineData(lineDataSet)
        lineDataSet.setColors(*ColorTemplate.MATERIAL_COLORS)

        //formatting
        val desc = Description()
        desc.text = description
        desc.textSize = 14F
        chart.description = desc

        //axis
        chart.axisLeft.setDrawGridLines(true)
        chart.xAxis.setDrawGridLines(true)
        chart.xAxis.setDrawAxisLine(true)
        chart.legend.isEnabled = false

        //chart.data = tempData

        //animation
        chart.animateX(1000)

        //refresh
        chart.invalidate()

    }

    private fun drawPieChart(dataList: Array<Int>, labels: Array<String>, chart: PieChart){
        // (total num of times company is used in last month: int, company name: string)
        //data
        val data = ArrayList<PieEntry>()
        var i=0
        /*while (i < 6){
            data.add(PieEntry(dataList[i].toFloat(), labels[i]))
            i++
        }

         */

        while (i < 6){
            data.add(PieEntry(dataList[i].toFloat(), labels[i]))
            i++
        }

        val pieDataSet = PieDataSet(data, "")
        val tempData = PieData(pieDataSet)

        //formatting
        val pieShades: ArrayList<Int> = ArrayList()
        pieShades.add(Color.parseColor("#F7EDA3"))
        pieShades.add(Color.parseColor("#FF7D63"))
        pieShades.add(Color.parseColor("#F8B679"))
        pieShades.add(Color.parseColor("#FFACB8"))
        pieShades.add(Color.parseColor("#6786C6"))
        pieShades.add(Color.parseColor("#B5E19A"))

        pieDataSet.colors= pieShades
        pieDataSet.sliceSpace = 0.5f
        chart.isDrawHoleEnabled = false
        chart.description.isEnabled = false

        chart.legend.isEnabled = true
        chart.legend.textColor = Color.parseColor("#191970")
        chart.legend.orientation = Legend.LegendOrientation.VERTICAL
        chart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        chart.legend.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        chart.legend.isWordWrapEnabled = true
        chart.setDrawEntryLabels(false)


        chart.data = tempData

        //animation
        chart.animateY(1400, Easing.EaseInOutQuad)

        //refresh
        chart.invalidate()

    }

    private fun drawBarChart(dataList: Array<Float>, chart: BarChart){
        //(dataList: Array<Float>,labels: Array<String>, chart: BarChart)
        //data

        /*val months = arrayOf<String>(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )
         */

        var xAxisValues = ArrayList<String>()
        xAxisValues.add("Jan")
        xAxisValues.add("Feb")
        xAxisValues.add("Mar")
        xAxisValues.add("Apr")
        xAxisValues.add("May")
        xAxisValues.add("June")
        xAxisValues.add("Jul")
        xAxisValues.add("Aug")
        xAxisValues.add("Sep")
        xAxisValues.add("Oct")
        xAxisValues.add("Nov")
        xAxisValues.add("Dec")

        val data = ArrayList<BarEntry>()
        var i=0
        /*while (i<12){
            data.add(BarEntry(i.toFloat(), dataList[i]))
            i++
        }

         */

        while (i<12){
            data.add(BarEntry(i.toFloat(), dataList[i]))
            i++
        }

        chart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)
        chart.xAxis.position = XAxis.XAxisPosition.TOP
        chart.xAxis.textColor = Color.parseColor("#191970")
        chart.xAxis.textSize = 9F


        chart.setDrawGridBackground(false)
        chart.axisLeft.isEnabled = false
        chart.axisRight.isEnabled = false
        chart.description.isEnabled = false
        chart.legend.isEnabled = false


        //chart.setDrawValueAboveBar(true)//

        val barShades: ArrayList<Int> = ArrayList()
        barShades.add(Color.parseColor("#F7EDA3"))
        barShades.add(Color.parseColor("#FF7D63"))
        barShades.add(Color.parseColor("#F8B679"))
        barShades.add(Color.parseColor("#FFACB8"))
        barShades.add(Color.parseColor("#6786C6"))
        barShades.add(Color.parseColor("#B5E19A"))

        val set = BarDataSet(data, "")
        set.setColors(barShades)
        set.valueTextSize = 7f
        set.valueTextColor = Color.parseColor("#191970")

        chart.data = BarData(set)
        chart.invalidate()

    }



}
