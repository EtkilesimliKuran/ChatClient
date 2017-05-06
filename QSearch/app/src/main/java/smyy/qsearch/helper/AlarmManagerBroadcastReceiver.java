package smyy.qsearch.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import smyy.qsearch.servis.CallBack;

/**
 * Created by Sümeyye on 2.5.2017.
 */

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    final public static String ONE_TIME = "onetime";
    CallBack callBack;

    @Override
    public void onReceive(Context context, Intent intent) {
        final SharedPreferences pref = context.getSharedPreferences(Config.SHARED_PREF, 0);
        String userid = pref.getString("userid", null);
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
        //Acquire the lock
        wl.acquire();

        //You can do the processing here.
        Bundle extras = intent.getExtras();
        StringBuilder msgStr = new StringBuilder();
        if (extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)) {
            //Make sure this intent has been sent by the one-time timer button.
            msgStr.append("One time Timer : ");

        }

        Format formatter = new SimpleDateFormat("hh:mm:ss a");
        msgStr.append(formatter.format(new Date()));
        Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show();
        //Release the lock
        wl.release();
        callBack.GetNotification(userid);
    }

    public void SetAlarm(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.FALSE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        Calendar calendar = Calendar.getInstance();
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, pi);
    }

    public void CancelAlarm(Context context) {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}

