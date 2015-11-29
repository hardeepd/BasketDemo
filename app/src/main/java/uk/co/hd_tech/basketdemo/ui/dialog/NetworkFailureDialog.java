package uk.co.hd_tech.basketdemo.ui.dialog;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Network failure dialog
 */
public class NetworkFailureDialog {

    private Context context;
    private String message;

    public NetworkFailureDialog(Context context, String message) {
        this.context = context;
        this.message = message;
    }

    public void showDialog() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title("Network Failure");
        builder.content("Failed to retrieve FX rates: " + message);

        builder.positiveText("OK");
        builder.autoDismiss(false);

        builder.show();
    }
}
