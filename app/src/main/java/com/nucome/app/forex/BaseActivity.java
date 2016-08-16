package com.nucome.app.forex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public abstract class BaseActivity extends AppCompatActivity {


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.news:
                Intent registerIntent = new Intent(getApplicationContext(), WsjActivity.class);
                startActivity(registerIntent);
                return  true;
            case R.id.realTimeRate:
                Intent loginIntent = new Intent(getApplicationContext(), TechnicalActivity.class);
                startActivity(loginIntent);
                return  true;
            case R.id.calendar:
                Intent calendarIntent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(calendarIntent);
                return  true;
            case R.id.whyForex:
                Intent learningIntent = new Intent(getApplicationContext(), WhyForexActivity.class);
                startActivity(learningIntent);
                return  true;
            /*
            case R.id.tradeSignal:
                Intent openAccountIntent = new Intent(getApplicationContext(), SignalActivity.class);
                startActivity(openAccountIntent);
                return true;*/
            case R.id.settints:
                Intent userInfoIntent = new Intent(getApplicationContext(), UserInfoActivity.class);
                startActivity(userInfoIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
