package com.example.aacharttest

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.*
import com.opencsv.CSVReaderBuilder
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private val data = arrayListOf<StudentData>()
    private val wrappedData1 = arrayListOf<BarEntry>()
    private val wrappedData2 = arrayListOf<BarEntry>()
    private lateinit var chart : BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chart = findViewById(R.id.chart)

        getDataFromSheet()

        val dataSet = BarDataSet(wrappedData1, "Parents have bachelor")
        val dataSet2 = BarDataSet(wrappedData2, "Parents don't have bachelor")
        dataSet.setColor(Color.RED)
        dataSet2.setColor(Color.GREEN)
        val barData = BarData(dataSet, dataSet2)
        chart.data = barData
        chart.setFitBars(true)
        chart.axisLeft.axisMinimum = 0f
        chart.axisLeft.axisMaximum = 80f
        chart.invalidate()

    }
    //Sort and show avg of each groups math score
    private fun sortToEntry(){
        val mathScores1 = arrayListOf<Float>()
        val mathScores2 = arrayListOf<Float>()
            for (studentData in data){
                if(studentData.parentalEducation == "bachelor's degree"){
                    mathScores1.add(studentData.mathScore)
                }
                else{
                    mathScores2.add(studentData.mathScore)
                }
            }
        wrappedData1.add(object: BarEntry(0f, mathScores1.average().toFloat()){})
        wrappedData2.add(object: BarEntry(2f, mathScores2.average().toFloat()){})

    }

    private fun getDataFromSheet(){
        try {
            val inputStream = assets.open("StudentsPerformance.csv")
            val fileReader = InputStreamReader(inputStream)
            val reader = CSVReaderBuilder(fileReader).withSkipLines(1).build()

            val lines = reader.readAll()
            for (line in lines){
                val singleData = StudentData(line[0], line[1], line[2], line[3], line[4], line[5].toFloat(), line[6].toFloat(), line[7].toFloat())
                data.add(singleData)
            }
        }
        catch (e: IOException){
            println("Reading CSV Error!")
            e.printStackTrace()
        }
        finally {
            sortToEntry()
        }
    }
}
