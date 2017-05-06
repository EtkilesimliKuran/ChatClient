package smyy.qsearch.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import smyy.qsearch.R;
import smyy.qsearch.helper.AlarmManagerBroadcastReceiver;
import smyy.qsearch.helper.Config;
import smyy.qsearch.servis.CallBack;

public class SettingsActivity extends AppCompatActivity {

    RadioButton rBtnMeal, rBtnHadis, rBtnSunnet;
    Button btnSave;
    CallBack callBack;
    AlarmManagerBroadcastReceiver alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SetActivityElements();

        alarm = new AlarmManagerBroadcastReceiver();
        final SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        final SharedPreferences.Editor editor = pref.edit();
        final Boolean isCheckMeal = pref.getBoolean("isCheckMeal", false);
        final Boolean isCheckHadis = pref.getBoolean("isCheckHadis", false);
        final Boolean isCheckSunnet = pref.getBoolean("isCheckSunnet", false);
        rBtnMeal.setChecked(isCheckMeal);
        rBtnHadis.setChecked(isCheckHadis);
        rBtnSunnet.setChecked(isCheckSunnet);

        rBtnMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rBtnMeal.setChecked(!isCheckMeal);
                editor.putBoolean("isCheckMeal", !isCheckMeal);
                editor.commit();
            }
        });
        rBtnHadis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rBtnHadis.setChecked(!isCheckHadis);
                editor.putBoolean("isCheckHadis", !isCheckHadis);
                editor.commit();
            }
        });
        rBtnSunnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rBtnSunnet.setChecked(!isCheckSunnet);
                editor.putBoolean("isCheckSunnet", !isCheckSunnet);
                editor.commit();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid = pref.getString("userid", null);
                List<String> user_choice = new ArrayList<>();
                if (rBtnMeal.isChecked()) {
                    user_choice.add("1");
                }
                if (rBtnHadis.isChecked()) {
                    user_choice.add("2");
                }
                if (rBtnSunnet.isChecked()) {
                    user_choice.add("3");
                }
                if (user_choice.size() == 0)
                    alarm.CancelAlarm(SettingsActivity.this);
                else
                    alarm.SetAlarm(SettingsActivity.this);
                callBack = new CallBack(SettingsActivity.this);
                callBack.UpdateChoice(userid, user_choice);
            }
        });
    }

    private void SetActivityElements() {
        rBtnMeal = (RadioButton) findViewById(R.id.rBtnMeal);
        rBtnHadis = (RadioButton) findViewById(R.id.rBtnHadis);
        rBtnSunnet = (RadioButton) findViewById(R.id.rBtnSunnet);
        btnSave = (Button) findViewById(R.id.btnSave);
    }

}
