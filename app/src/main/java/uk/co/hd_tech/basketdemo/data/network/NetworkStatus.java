package uk.co.hd_tech.basketdemo.data.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import uk.co.hd_tech.basketdemo.ui.dialog.NoNetworkDialog;

/**
 * Network availability tester
 */
public class NetworkStatus {

    public static boolean isOnline(Context context, boolean showDialog) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if ((networkInfo != null && networkInfo.isConnected())) {
            return true;
        } else {
            if (showDialog) {
                new NoNetworkDialog(context).showDialog();
            }
            return false;
        }
    }
}
