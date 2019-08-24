package com.gyosanila.mymovie.features.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.CompoundButton
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.core.common.Alarm
import com.gyosanila.mymovie.core.service.AlarmReceiver
import com.gyosanila.mymovie.features.domain.local.SharedPreferences
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.*

class SettingsActivity : AppCompatActivity(), View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var alarmNotification: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setupUI()
    }

    fun setupUI() {
        setSupportActionBar(toolbar)
        sharedPreferences = SharedPreferences(this)
        alarmNotification = AlarmReceiver()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        switchDaily.isChecked = sharedPreferences.dailyRemainder
        switchRelease.isChecked = sharedPreferences.releaseRemainder
        textDescLanguage.text =  Locale.getDefault().displayCountry
        cardLanguage.setOnClickListener(this)
        cardDailyRemainder.setOnClickListener(this)
        cardReleaseRemainder.setOnClickListener(this)
        switchDaily.setOnCheckedChangeListener(this)
        switchRelease.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView) {
            switchDaily -> setAlarmDaily(isChecked)
            switchRelease -> setAlarmRelease(isChecked)
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            cardLanguage -> navigationToLanguageSettings()
            cardDailyRemainder -> switchDaily.isChecked = !switchDaily.isChecked
            cardReleaseRemainder -> switchRelease.isChecked = !switchRelease.isChecked
        }
    }

    private fun navigationToLanguageSettings() {
        startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
    }

    private fun setAlarmDaily(isActive: Boolean) {
        sharedPreferences.dailyRemainder = isActive
        if (isActive) {
            alarmNotification.setRepeatingAlarm(
                this,
                AlarmReceiver.REQUEST_RELEASE,
                Alarm.TIME_RELEASE
            )
        } else {
            alarmNotification.cancelAlarm(this, AlarmReceiver.REQUEST_RELEASE)
        }

    }

    private fun setAlarmRelease(isActive: Boolean) {
        sharedPreferences.releaseRemainder = isActive
        if (isActive) {
            alarmNotification.setRepeatingAlarm(
                this,
                AlarmReceiver.REQUEST_DAILY,
                Alarm.TIME_DAILY,
                getString(R.string.app_name),
                getString(R.string.text_message_alarm_release)
            )
        } else {
            alarmNotification.cancelAlarm(this, AlarmReceiver.REQUEST_DAILY)
        }
    }
}
