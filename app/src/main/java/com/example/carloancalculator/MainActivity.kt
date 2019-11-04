package com.example.carloancalculator

import android.content.Context
import android.content.res.Configuration
import android.opengl.GLES11Ext
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.os.ConfigurationCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonCalculate.setOnClickListener{calculate(it)}
    }



    fun resetInput(view: View) {
        //TODO: clear all input and output
        editTextCarPrice.setText("")
        editTextDownPayment.setText("")
        editTextLoanPeriod.setText("")
        editTextInterestRate.setText("")
        textViewLoan.setText(getString(R.string.loan))
        textViewInterest.setText(getString(R.string.interest))
        textViewMonthlyRepayment.setText(getString(R.string.monthly_repayment))
    }

    fun calculate(view: View){
        //todo: get all inputs from user and perform calculation
        if(editTextCarPrice.text.isEmpty()){
            editTextCarPrice.setError(getString(R.string.input_required))
            return
        }
        if(editTextDownPayment.text.isEmpty()){
            editTextDownPayment.setError(getString(R.string.input_required))
            return
        }
        if(editTextLoanPeriod.text.isEmpty()){
            editTextLoanPeriod.setError(getString(R.string.input_required))
            return
        }
        if(editTextInterestRate.text.isEmpty()){
            editTextInterestRate.setError(getString(R.string.input_required))
            return
        }

        val carPrice = editTextCarPrice.text.toString().toInt()
        val downPayment = editTextDownPayment.text.toString().toInt()


        if(carPrice < downPayment){
            editTextDownPayment.setError(getString(R.string.bad_downPayment))
            return
        }

        val loanPeriod = editTextLoanPeriod.text.toString().toInt()
        if(loanPeriod > 9){
            editTextLoanPeriod.setError(getString(R.string.bad_loan_period))
            return
        }
        val interestRate = editTextInterestRate.text.toString().toFloat()

        var carLoan = carPrice - downPayment
        var interest = carLoan * loanPeriod * interestRate
        var monthlyRepayment = (carLoan + interest) / loanPeriod / 12

        val currency = Currency.getInstance(Locale.getDefault()).getSymbol()

        textViewLoan.setText(getString(R.string.loan) + currency + "${carLoan}")
        textViewInterest.setText(getString(R.string.interest) + currency + interest.toString())
        textViewMonthlyRepayment.setText(getString(R.string.monthly_repayment) + currency + monthlyRepayment.toString())

        // Hide the keyboard.
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
