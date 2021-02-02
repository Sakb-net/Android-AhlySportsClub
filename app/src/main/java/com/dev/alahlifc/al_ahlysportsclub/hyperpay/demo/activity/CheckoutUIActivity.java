package com.dev.alahlifc.al_ahlysportsclub.hyperpay.demo.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import com.dev.alahlifc.al_ahlysportsclub.R;
import com.dev.alahlifc.al_ahlysportsclub.hyperpay.demo.receiver.CheckoutBroadcastReceiver;
import com.oppwa.mobile.connect.checkout.dialog.CheckoutActivity;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSettings;
import timber.log.Timber;


/**
 * Represents an activity for making payments via {@link CheckoutActivity}.
 */
public class CheckoutUIActivity extends BasePaymentActivity {

     static String Checkout_id = "";
    private String result_url = "";
    private String total = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_ui);

        Checkout_id = getIntent().getStringExtra("checkout_id");
        result_url = getIntent().getStringExtra("result_url");
        total = getIntent().getStringExtra("total");

        String amount =  total;

                //+ " "
                //+ Constants.Config.CURRENCY;

        ((TextView) findViewById(R.id.amount_text_view)).setText(amount);

        findViewById(R.id.button_proceed_to_checkout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // requestCheckoutId(getString(R.string.checkout_ui_callback_scheme));
                openCheckoutUI(Checkout_id, result_url);
            }
        });
    }

    @Override
    public void onCheckoutIdReceived(String checkoutId) {
        super.onCheckoutIdReceived(checkoutId);

       // if (checkoutId != null) {
         //   openCheckoutUI(checkoutId);
        //}
    }

    private void openCheckoutUI(String checkoutId, String result_url) {

        Timber.e("checkout_id---****= "+ checkoutId);

        CheckoutSettings checkoutSettings = createCheckoutSettings(checkoutId, result_url /*getString(R.string.checkout_ui_callback_scheme)*/);

        /* Set componentName if you want to receive callbacks from the checkout */
        ComponentName componentName = new ComponentName(
                getPackageName(), CheckoutBroadcastReceiver.class.getName());

        /* Set up the Intent and start the checkout activity. */
        Intent intent = checkoutSettings.createCheckoutActivityIntent(this, componentName);

        startActivityForResult(intent, CheckoutActivity.REQUEST_CODE_CHECKOUT);
    }
}
