package com.dev.alahlifc.al_ahlysportsclub.hyperpay.demo.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.oppwa.mobile.connect.checkout.dialog.CheckoutActivity;


/**
 * Broadcast receiver to listen the intents from CheckoutActivity.
 */
public class CheckoutBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (CheckoutActivity.ACTION_ON_BEFORE_SUBMIT.equals(action)) {
            String paymentBrand = intent.getStringExtra(CheckoutActivity.EXTRA_PAYMENT_BRAND);
            String checkoutId = intent.getStringExtra(CheckoutActivity.EXTRA_CHECKOUT_ID);

            ComponentName senderComponentName = intent.getParcelableExtra(
                    CheckoutActivity.EXTRA_SENDER_COMPONENT_NAME);

            /* This callback can be used to request a new checkout ID if selected payment brand requires
               some specific parameters or just send back the same checkout id to continue checkout process */
            intent = new Intent(CheckoutActivity.ACTION_ON_BEFORE_SUBMIT);
            intent.setComponent(senderComponentName);
            intent.setPackage(senderComponentName.getPackageName());

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(CheckoutActivity.EXTRA_CHECKOUT_ID, checkoutId);

            /* Also it can be used to cancel the checkout process by sending
               the CheckoutActivity.EXTRA_CANCEL_CHECKOUT */
            intent.putExtra(CheckoutActivity.EXTRA_TRANSACTION_ABORTED, false);

            context.startActivity(intent);
        }
    }
}
