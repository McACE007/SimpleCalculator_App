package com.example.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.security.KeyStore.TrustedCertificateEntry

class MainActivity : AppCompatActivity() {
    var lastNumeric = false
    var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun onDigit(view : View){
        tvInput.append((view as Button).text)
        lastNumeric = true
    }
    fun onClear(view: View){
        tvInput.text = ""
        var lastNumeric = false
        var lastDot = false
    }
    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View){
        if(lastNumeric && !isOperatorAdded(tvInput.text.toString())){
            tvInput.append((view as Button).text)
        }
    }

    fun onEqual(view: View){
        if(lastNumeric) {
            var inputString = tvInput.text.toString()
            var outputString = ""
            var prefix = ""
            try {
                if (tvInput.text.startsWith("-")) {
                    prefix = "-"
                    inputString = inputString.substring(1)
                }

                if(inputString.contains("-")){
                    var inputList = inputString.split("-")

                    var a = inputList[0]
                    var b = inputList[1]

                    if(prefix.isNotEmpty())
                        a = prefix + a

                    outputString = (a.toDouble() - b.toDouble()).toString()
                }

                else if(inputString.contains("+")){
                    var inputList = inputString.split("+")

                    var a = inputList[0]
                    var b = inputList[1]

                    if(!prefix.isEmpty())
                        a = prefix + a

                    outputString = (a.toDouble() + b.toDouble()).toString()
                }

                else if(inputString.contains("/")){
                    var inputList = inputString.split("/")

                    var a = inputList[0]
                    var b = inputList[1]

                    if(!prefix.isEmpty())
                        a = prefix + a

                    outputString = (a.toDouble() / b.toDouble()).toString()
                }

                else if(inputString.contains("X")){
                    var inputList = inputString.split("X")

                    var a = inputList[0]
                    var b = inputList[1]

                    if(!prefix.isEmpty())
                        a = prefix + a

                    outputString = (a.toDouble() * b.toDouble()).toString()
                }
                tvInput.text = removeZeros(outputString)

            }catch (e: java.lang.ArithmeticException){
                e.printStackTrace()
            }

        }
    }

    private fun removeZeros(value: String) : String{
        if(value.contains(".0")){
            return value.substring(0, value.length - 2)
        }
        return value
    }
    private fun isOperatorAdded(value: String) : Boolean{
        return if(value.startsWith("-")){
            false
        }
        else{
            value.contains("/") || value.contains("*") ||
                    value.contains("+") || value.contains("-")
        }
    }
}