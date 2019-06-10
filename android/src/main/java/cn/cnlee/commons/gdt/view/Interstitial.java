package cn.cnlee.commons.gdt.view;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.qq.e.ads.interstitial.InterstitialAD;
import com.qq.e.ads.interstitial.InterstitialADListener;
import com.qq.e.comm.util.AdError;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

public class Interstitial implements InterstitialADListener {

    private static final String TAG = Interstitial.class.getSimpleName();

    private InterstitialAD iad;
    private String posID;
    private boolean asPopup;

    private static Interstitial mInstance;
    private ReactApplicationContext mContext;

    public static synchronized Interstitial getInstance(ReactApplicationContext reactContext) {
        if (mInstance == null) {
            mInstance = new Interstitial(reactContext);
        }
        return mInstance;
    }

    private Interstitial(ReactApplicationContext reactContext){
        this.mContext = reactContext;
    }


    @Override
    public void onNoAD(AdError adError) {
        Log.e(TAG,"onNoAD: eCode=" + adError.getErrorCode() + ",eMsg=" + adError.getErrorMsg());
        WritableMap event = Arguments.createMap();
        event.putString("error", "BannerNoAD，eCode=" + adError);
    }

    @Override
    public void onADReceive() {
        Log.e(TAG,"onADReceive");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Interstitial.this.iad != null){
                    if (asPopup){
                        Interstitial.this.iad.showAsPopupWindow();
                    }else {
                        Interstitial.this.iad.show();
                    }
                }
            }
        });

    }

    @Override
    public void onADOpened() {
        Log.e(TAG,"onADOpened");
    }

    @Override
    public void onADExposure() {
        Log.e(TAG,"onADExposure");
    }

    @Override
    public void onADClosed() {
        Log.e(TAG,"onADClosed");
    }

    @Override
    public void onADClicked() {
        Log.e(TAG,"onADClicked");
    }

    @Override
    public void onADLeftApplication() {
        Log.e(TAG,"onADLeftApplication");
    }

    public void showInterstitialAD(final String appID,final String posID, boolean asPopup) {
        Log.e(TAG, "showInterstitialAD");
        this.asPopup = asPopup;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getIAD(appID, posID).loadAD();
            }
        });


    }

    private InterstitialAD getIAD(String appID, String posID) {
        if (iad != null && this.posID.equals(posID)) {
            Log.e(TAG,"======相同IAD无需创建新的======");
            return iad;
        }
        this.posID = posID;
        if (this.iad != null) {
            iad.closePopupWindow();
            iad.destroy();
            iad = null;
        }
        iad = new InterstitialAD(mContext.getCurrentActivity(), appID, posID);
        iad.setADListener(this);
        return iad;
    }
}
