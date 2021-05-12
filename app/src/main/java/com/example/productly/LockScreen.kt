package com.example.productly

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.exampartone.db.DiaryPlannerContract
import kotlinx.android.synthetic.main.activity_lock_screen.*
import com.example.exampartone.db.DiaryPlannerContract.DiaryPlannerPass
import com.example.exampartone.db.DiaryPlannerHelper
import com.google.android.material.snackbar.Snackbar

object mySingleton{
    //our db helper object
    lateinit var dbHelper:DiaryPlannerHelper

            //yung i-input na apat na pin ay dito ii-store
            var old_pinVal = ""
            //flag checker
            var is_confirmed_pin = false
            var pinCode_is_set = false
}

class LockScreen : AppCompatActivity() {
    var pinCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_screen)

        supportActionBar?.title = "Journal Lockscreen"

        var actionBar = getSupportActionBar()

        // showing the back button in action bar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        //Singleton has an initial value of FALSE
        //dbHelper = instantiation (representation) of DiaryHelper class
        //A context handles the system; lets you access the environment where your app is running
        mySingleton.dbHelper = DiaryPlannerHelper(applicationContext)
        //representation ng dbhelper
        val dbLock = mySingleton.dbHelper.readableDatabase
        //dito magso-store yung pincode
        val colPin = arrayOf(DiaryPlannerContract.DiaryPlannerPass.PASS)

        //Filter results by Title, it is similar with WHERE CLAUSE
        //var selectPin = "${DiaryPlannerContract.LockPin.OLD_PIN} = ?"
        // var selectionPinArgs = arrayOf(mySingleton.old_pin)

        //How do you want the results sorted in the
        //resulting Cursor

        //execute and fetch
        val cursor = dbLock.query(
            DiaryPlannerContract.DiaryPlannerPass.TABLE_NAME, //The table to query
            colPin,         // The array of columns to return (pass null to get all)
            null,          // The columns for the WHERE clause
            null,      // The values for the WHERE clause
            null,        // don't group the rows
            null,         // don't filter by row groups
            null)          // The sort order
        with(cursor) {

            while (moveToNext()) {

                mySingleton.old_pinVal = getString(getColumnIndex(DiaryPlannerContract.DiaryPlannerPass.PASS))

                mySingleton.pinCode_is_set = true

            }
        }

        btnOne.setOnClickListener {
            //actions to be executed
            pinCode += "1"
            edPWd.setText(pinCode).toString()
            pinCodeChecker(pinCode)
        }

        btnTwo.setOnClickListener {
            //actions to be executed
            pinCode += "2"
            edPWd.setText(pinCode).toString()
            pinCodeChecker(pinCode)
        }

        btnThree.setOnClickListener {
            //actions to be executed
            pinCode += "3"
            edPWd.setText(pinCode).toString()
            pinCodeChecker(pinCode)
        }

        btnFour.setOnClickListener {
            //actions to be executed
            pinCode += "4"
            edPWd.setText(pinCode).toString()
            pinCodeChecker(pinCode)
        }

        btnFive.setOnClickListener {
            //actions to be executed
            pinCode += "5"
            edPWd.setText(pinCode).toString()
            pinCodeChecker(pinCode)
        }

        btnSix.setOnClickListener {
            //actions to be executed
            pinCode += "6"
            edPWd.setText(pinCode).toString()
            pinCodeChecker(pinCode)
        }

        btnSeven.setOnClickListener {
            //actions to be executed
            pinCode += "7"
            edPWd.setText(pinCode).toString()
            pinCodeChecker(pinCode)
        }

        btnEight.setOnClickListener {
            //actions to be executed
            pinCode += "8"
            edPWd.setText(pinCode).toString()
            pinCodeChecker(pinCode)
        }

        btnNine.setOnClickListener {
            //actions to be executed
            pinCode += "9"
            edPWd.setText(pinCode).toString()
            pinCodeChecker(pinCode)
        }

        btnZero.setOnClickListener {
            //actions to be executed
            pinCode += "0"
            edPWd.setText(pinCode).toString()
            pinCodeChecker(pinCode)
        }

        btnbackSpace.setOnClickListener{
            if(pinCode.length > 0){
                pinCode = pinCode.substring(0, pinCode.length - 1)
                edPWd.setText(pinCode).toString()
                pinCodeChecker(pinCode)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy(){
        mySingleton.dbHelper.close()
        super.onDestroy()
    }

    //pag nagmatch na,
    fun createDb(pinVal: String){
        val db = mySingleton.dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DiaryPlannerPass.PASS,pinVal)
        }

        val newRowId = db?.insert(DiaryPlannerPass.TABLE_NAME, null, values)

            var intentAddEntry = Intent(this, MainActivity2::class.java)
            startActivity(intentAddEntry)
    }

    fun pinCodeChecker(pinVal: String) {
        //step 0 magse-save ng isang beses (nakaset na ng password)
        //kailangan i-match yung ininput na bago dun sa nakasave
        //else
        //step 1 input ng apat ng pin
        //step 2 input ng apat na pin (confirmation)
        //step 3 pag nag-match, sasabihin pin code match
        //else, step 4 pincode unmatched
        //mag-input ulit ng 4 hanggang mag match
        if (mySingleton.pinCode_is_set) {
            if (pinVal.length == 4) {
                //chinecheck sa database kung yung ininput ay match
                if (mySingleton.old_pinVal == pinVal) {
                    var snack = Snackbar.make(snackbar_, "Password matched!", Snackbar.LENGTH_INDEFINITE)
                    snack.setAction("OK") {
                        var intentAddEntry = Intent(this, MainActivity2::class.java)
                        startActivity(intentAddEntry)
                        edPWd.setText("")
                    }
                    snack.show()
                } else {
                    var snack = Snackbar.make(snackbar_, "Incorrect password", Snackbar.LENGTH_INDEFINITE)
                    snack.setAction("OK") {
                        edPWd.setText("")
                        pinCode = ""
                    }
                    snack.show()
                }
            }
            } else {
                    if(!mySingleton.is_confirmed_pin){
                        if(pinVal.length==4){
                            mySingleton.old_pinVal = pinVal
                            edPWd.setHint("Confirm your new passcode")
                            edPWd.setText("")
                            pinCode = ""
                            mySingleton.is_confirmed_pin = true
                        }
                    } else{
                        if(pinVal.length==4){
                            if(pinVal==mySingleton.old_pinVal){
                                var snack = Snackbar.make(snackbar_, "Password matched!", Snackbar.LENGTH_INDEFINITE)
                                snack.setAction("OK"){
                                    edPWd.setText("")
                                    pinCode = ""
                                    createDb(pinVal)
                                }
                                snack.show()

                            } else{
                                var snack = Snackbar.make(snackbar_, "Password mismatched!", Snackbar.LENGTH_INDEFINITE)
                                snack.setAction("OK"){
                                    edPWd.setText("")
                                    pinCode = ""
                                    mySingleton.is_confirmed_pin = false
                                }
                                snack.show()
                            }
                        }
                    }
                }
            }
    }


