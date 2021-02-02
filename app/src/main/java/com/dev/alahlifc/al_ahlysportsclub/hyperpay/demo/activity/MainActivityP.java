package com.dev.alahlifc.al_ahlysportsclub.hyperpay.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.dev.alahlifc.al_ahlysportsclub.R;
import com.oppwa.mobile.connect.provider.Connect;


public class MainActivityP extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mainp);

        findViewById(R.id.checkout_ui).setOnClickListener(this);
        findViewById(R.id.payment_button).setOnClickListener(this);
        findViewById(R.id.custom_ui).setOnClickListener(this);

        ((TextView) findViewById(R.id.version_number))
                .setText(String.format(getString(R.string.sdk_version_number), Connect.getVersion()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkout_ui:
                startActivity(new Intent(this, CheckoutUIActivity.class));
                break;

        }
    }
}
