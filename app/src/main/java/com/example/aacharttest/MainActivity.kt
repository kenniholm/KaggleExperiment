package com.example.aacharttest

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.opencsv.CSVReaderBuilder
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    val data = arrayListOf<StudentData>()
    val wrappedData = arrayListOf<Entry>()
    lateinit var chart : LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chart = findViewById(R.id.chart)

        getDataFromSheet()

        val dataSet = LineDataSet(wrappedData, "labelTest")
        dataSet.setColor(Color.RED)
        val lineData = LineData(dataSet)
        chart.data = lineData
        chart.invalidate()

    }

    private fun wrapToEntry(){
        for (student in data){
            wrappedData.add(object: Entry(student.writingScore, student.mathScore){})
        }
    }

    private fun getDataFromSheet(){
        try {
            val inputStream = assets.open("StudentsPerformance.csv")
            val fileReader = InputStreamReader(inputStream)
            val reader = CSVReaderBuilder(fileReader).withSkipLines(1).build()

            val lines = reader.readAll()
            for (line in lines){

                //println(line[0] + "|" + line[1] + line[2] + line[3] + line[4] + line[5] + line[6] + line[7])

                val singleData = StudentData(line[0], line[1], line[2], line[3], line[4], line[5].toFloat(), line[6].toFloat(), line[7].toFloat())
                data.add(singleData)
            }
        }
        catch (e: IOException){
            println("Reading CSV Error!")
            e.printStackTrace()
        }
        finally {
            wrapToEntry()
        }
    }
}
