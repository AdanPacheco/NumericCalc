package com.udemy.numericcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.udemy.numericcalc.databinding.ActivityMainBinding
import java.lang.ArithmeticException
import javax.net.ssl.SSLSessionBindingEvent

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false
    private var operatorAdded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
    }

    private fun initListeners() {
        binding.btnZero.setOnClickListener(this)
        binding.btnOne.setOnClickListener(this)
        binding.btnTwo.setOnClickListener(this)
        binding.btnThree.setOnClickListener(this)
        binding.btnFour.setOnClickListener(this)
        binding.btnFive.setOnClickListener(this)
        binding.btnSix.setOnClickListener(this)
        binding.btnSeven.setOnClickListener(this)
        binding.btnEight.setOnClickListener(this)
        binding.btnNine.setOnClickListener(this)
        binding.btnCLR.setOnClickListener(this)
        binding.btnDivide.setOnClickListener(this)
        binding.btnMultiply.setOnClickListener(this)
        binding.btnMinus.setOnClickListener(this)
        binding.btnPlus.setOnClickListener(this)
        binding.btnEqual.setOnClickListener(this)
        binding.btnDot.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (binding.tvResult.text.startsWith("0")) {
            binding.tvResult.text = ""
        }
        when (v?.id) {
            binding.btnZero.id -> {
                binding.tvResult.append("0")
                setFlags(true, false)
            }
            binding.btnOne.id -> {
                binding.tvResult.append("1")
                setFlags(true, false)
            }
            binding.btnTwo.id -> {
                binding.tvResult.append("2")
                setFlags(true, false)
            }
            binding.btnThree.id -> {
                binding.tvResult.append("3")
                setFlags(true, false)
            }
            binding.btnFour.id -> {
                binding.tvResult.append("4")
                setFlags(true, false)
            }
            binding.btnFive.id -> {
                binding.tvResult.append("5")
                lastNumeric = true
                setFlags(true, false)
            }
            binding.btnSix.id -> {
                binding.tvResult.append("6")
                setFlags(true, false)
            }
            binding.btnSeven.id -> {
                binding.tvResult.append("7")
                setFlags(true, false)
            }
            binding.btnEight.id -> {
                binding.tvResult.append("8")
                setFlags(true, false)
            }
            binding.btnNine.id -> {
                binding.tvResult.append("9")
                setFlags(true, false)
            }
            binding.btnCLR.id -> {
                binding.tvResult.text = "0"
                setFlags(true, false)
                operatorAdded = false
            }
            binding.btnDivide.id -> addOperator("/")
            binding.btnMultiply.id -> addOperator("*")
            binding.btnPlus.id -> addOperator("+")
            binding.btnMinus.id -> addOperator("-")
            binding.btnEqual.id -> {
                if (lastNumeric && operatorAdded) {
                    var input = binding.tvResult.text.toString()
                    var prefix = ""
                    val operator: String

                    if (input.startsWith("-")) {
                        prefix = "-"
                        input = input.substring(1)
                    }

                    val numbers = input.split('/', '*', '-', '+')
                    var number1 = numbers[0]
                    val number2 = numbers[1]

                    operator = input.substring(number1.length, number1.length + 1)

                    if (prefix.isNotEmpty()) {
                        number1 = prefix + number1
                    }

                    doOperation(number1, number2, operator)
                }
            }
            binding.btnDot.id -> {
                if (lastNumeric && !lastDot) {
                    if (!binding.tvResult.text.contains(".")) {
                        binding.tvResult.append(".")
                        setFlags(false, true)
                    }
                }
            }

        }
    }

    private fun doOperation(number1: String, number2: String, op: String) {
        var result = 0.0
        try {
            when (op) {
                "/" -> result = (number1.toDouble() / number2.toDouble())
                "*" -> result = (number1.toDouble() * number2.toDouble())
                "-" -> result = (number1.toDouble() - number2.toDouble())
                "+" -> result = (number1.toDouble() + number2.toDouble())
            }
        } catch (e: ArithmeticException) {
            e.printStackTrace()
        }

        binding.tvResult.text = removeZeroAfterDot(result.toString())
        operatorAdded = false
    }

    private fun removeZeroAfterDot(value:String):String{
        var result = value
        if(value.contains(".0")){
            result = value.substring(0,value.length-2)
        }
        return result
    }

    private fun addOperator(op: String) {
        if (lastNumeric && !containsAnOperator(binding.tvResult.text.toString())) {
            binding.tvResult.append(op)
            setFlags(false, false)
            operatorAdded = true
        }
    }

    private fun setFlags(ln: Boolean, ld: Boolean) {
        lastNumeric = ln
        lastDot = ld
    }

    private fun containsAnOperator(value: String): Boolean {
        return if (value.startsWith("-")) {
            return false
        } else {
            value.contains("/") || value.contains("*") || value.contains("-") || value.contains("+")
        }
    }

}