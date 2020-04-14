package com.example.aacharttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.github.mikephil.charting.charts.LineChart
import com.opencsv.CSVReader
import com.opencsv.CSVReaderBuilder
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chart: LineChart = findViewById(R.id.chart)

        try {
            val inputStream = assets.open("StudentsPerformance.csv")
            val fileReader = InputStreamReader(inputStream)
            val reader = CSVReaderBuilder(fileReader).withSkipLines(1).build()

            val lines = reader.readAll()
            for (line in lines){
                println(line[0] + "|" + line[1])
            }
        }
        catch (e: IOException){
            println("Reading CSV Error!")
            e.printStackTrace()
        }
    }
}
