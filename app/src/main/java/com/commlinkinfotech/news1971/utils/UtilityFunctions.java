package com.commlinkinfotech.news1971.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

/**
 * Created by tarik on 11/14/16.
 */
public class UtilityFunctions {

    public static void openFacebookPage(Context context, String facebookUrl, String facebookPageId) {

        String facebookUrlForApp = facebookUrl;

        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                facebookUrlForApp = "fb://facewebmodal/f?href=" + facebookUrl;
            } else { //older versions of fb app
                facebookUrlForApp = "fb://page/" + facebookPageId;
            }
        } catch (PackageManager.NameNotFoundException e) {
            facebookUrlForApp = facebookUrl; //normal web url
        }

        if (isFacebookInstalled(context)) {
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);

            facebookIntent.setData(Uri.parse(facebookUrlForApp));
            context.startActivity(facebookIntent);
        } else {
            openUrlInBrowser(context, facebookUrl);
        }

    }

    public static void openUrlInBrowser(Context context, String url) {
        if (isInstalledChrome(context)) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setPackage("com.android.chrome");
            context.startActivity(i);
        } else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(browserIntent);
        }
    }

    public static boolean isFacebookInstalled(Context context) {

        try {
            ApplicationInfo info = context.getPackageManager().
                    getApplicationInfo("com.facebook.katana", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isInstalledChrome(Context context) {

        try {
            ApplicationInfo info = context.getPackageManager().
                    getApplicationInfo("com.android.chrome", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }

    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static boolean isLocationServiceAvailable(Context context) {
        LocationManager lm = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            return true;
        else
            return false;

    }
}
