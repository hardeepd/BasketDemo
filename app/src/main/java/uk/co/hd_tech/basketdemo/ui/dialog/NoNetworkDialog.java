package uk.co.hd_tech.basketdemo.ui.dialog;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * No network available dialog
 */
public class NoNetworkDialog {

    private Context context;

    public NoNetworkDialog(Context context) {
        this.context = context;
    }

    public void showDialog() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title("No Internet Connection");
        builder.content("You do not have an internet connection. Please check your connection and try again");

        builder.positiveText("OK");
        builder.autoDismiss(true);

        builder.show();
    }
}
