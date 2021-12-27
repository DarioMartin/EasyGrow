package com.dariomartin.easygrow.presentation.patient.reminder

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.databinding.ReminderFragmentBinding
import com.dariomartin.easygrow.presentation.utils.BaseFragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ReminderFragment : BaseFragment<ReminderFragmentBinding, ReminderViewModel>() {

    companion object {
        const val REQUEST_CODE = 1001
    }

    private var datePicker: MaterialTimePicker? = null
    private lateinit var alarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.time.observe(viewLifecycleOwner, {
            updateTime(it)
        })

        binding.time.setOnClickListener {
            showTimePicker()
        }

        updateTimeColor(isAlarmActivated())
        binding.switchButton.isChecked = isAlarmActivated()

        binding.switchButton.setOnCheckedChangeListener { _, active ->
            if (active) setAlarm() else cancelAlarm()
            updateTimeColor(active)
        }
    }

    private fun updateTime(calendar: Calendar) {
        binding.time.text = getString(
            R.string.hour_format,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE)
        )
    }

    private fun updateTimeColor(active: Boolean) {
        binding.time.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                if (active) R.color.eg_purple else R.color.eg_grey_1
            )
        )
    }

    private fun setAlarm() {
        val intent = Intent(activity, AlarmReceiver::class.java)
        intent.action = AlarmReceiver.ACTION_ALARM_RECEIVER

        val pendingIntent = PendingIntent.getBroadcast(
            activity,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        isAlarmActivated()
    }

    private fun cancelAlarm() {
        val intent = Intent(activity, AlarmReceiver::class.java)
        intent.action = AlarmReceiver.ACTION_ALARM_RECEIVER

        val pendingIntent = PendingIntent.getBroadcast(
            activity,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
        isAlarmActivated()
    }

    private fun isAlarmActivated(): Boolean {
        val intent = Intent(activity, AlarmReceiver::class.java)
        intent.action = AlarmReceiver.ACTION_ALARM_RECEIVER

        return PendingIntent.getBroadcast(
            activity,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_NO_CREATE
        ) != null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "easygrowReminderChannel"
            val description = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("easygrow", name, importance)
            channel.description = description

            val notificationManager =
                requireContext().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showTimePicker() {
        val calendar = viewModel.time.value
        datePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(calendar?.get(Calendar.HOUR_OF_DAY) ?: 0)
            .setMinute(calendar?.get(Calendar.MINUTE) ?: 0)
            .setTitleText(R.string.choose_reminder_time)
            .build()

        datePicker?.show(childFragmentManager, "")
        datePicker?.addOnPositiveButtonClickListener {
            viewModel.setNewTime(datePicker?.hour ?: 0, datePicker?.minute ?: 0)
            if (isAlarmActivated()) setAlarm()
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ReminderFragmentBinding {
        return ReminderFragmentBinding.inflate(inflater, container, false)
    }

    override fun provideViewModel(): ReminderViewModel {
        return ViewModelProvider(this)[ReminderViewModel::class.java]
    }

}