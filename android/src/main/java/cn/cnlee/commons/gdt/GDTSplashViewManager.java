package cn.cnlee.commons.gdt;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.google.gson.Gson;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;

import java.util.Map;

import javax.annotation.Nullable;

import cn.cnlee.commons.gdt.view.Splash;

public class GDTSplashViewManager extends SimpleViewManager implements SplashADListener {

    private static final String TAG = "GDTSplash";

    // 重写getName()方法, 返回的字符串就是RN中使用该组件的名称
    @Override
    public String getName() {
        return TAG;
    }

    @Override
    public void onNoAD(AdError adError) {
        Log.e(TAG, "onNoAD: eCode=" + adError.getErrorCode() + ",eMsg=" + adError.getErrorMsg());
        WritableMap event = Arguments.createMap();
        event.putString("error", new Gson().toJson(adError));
        mEventEmitter.receiveEvent(mContainer.getId(), Events.EVENT_FAIL_TO_RECEIVED.toString(), event);
        long alreadyDelayMills = System.currentTimeMillis() - fetchSplashADTime;//从拉广告开始到onNoAD已经消耗了多少时间
        //为防止加载广告失败后立刻跳离开屏可能造成的视觉上类似于"闪退"的情况，根据设置的minSplashTimeWhenNoAD 计算出还需要延时多久
        long shouldDelayMills = alreadyDelayMills > minSplashTimeWhenNoAD ? 0 : minSplashTimeWhenNoAD - alreadyDelayMills;
        Log.e(TAG, "shouldDelayMills: " + shouldDelayMills);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextAction();
            }
        }, shouldDelayMills);
    }

    @Override
    public void onADPresent() {
        Log.e(TAG, "onADPresent");
        if (mSplash != null) {
            mSplash.hideSplashHolder(true);
            mSplash.requestLayout();
        }
        mEventEmitter.receiveEvent(mContainer.getId(), Events.EVENT_PRESENT.toString(), null);
    }

    @Override
    public void onADExposure() {
        Log.e(TAG, "onADExposure");
        mEventEmitter.receiveEvent(mContainer.getId(), Events.EVENT_WILL_EXPOSURE.toString(), null);
    }

    @Override
    public void onADDismissed() {
        Log.e(TAG, "onADDismissed");
        mEventEmitter.receiveEvent(mContainer.getId(), Events.EVENT_WILL_DISMISSED.toString(), null);
        nextAction();
    }

    @Override
    public void onADClicked() {
        Log.e(TAG, "onADClicked");
        mEventEmitter.receiveEvent(mContainer.getId(), Events.EVENT_ON_CLICK.toString(), null);
    }

    @Override
    public void onADTick(long millisUntilFinished) {
        Log.e(TAG, "onADTick " + millisUntilFinished + "ms");
        if (mSplash != null) {
            mSplash.setTickTxt(millisUntilFinished);
        }
        WritableMap event = Arguments.createMap();
        event.putString("timeLeft", millisUntilFinished + "");
        mEventEmitter.receiveEvent(mContainer.getId(), Events.EVENT_ON_TICK.toString(), event);
    }

    private void nextAction() {
        mEventEmitter.receiveEvent(mContainer.getId(), Events.EVENT_NEXT_ACTION.toString(), null);
    }

    public enum Events {
        EVENT_FAIL_TO_RECEIVED("onFailToReceived"),
        EVENT_PRESENT("onPresent"),
        EVENT_WILL_DISMISSED("onDismissed"),
        EVENT_NEXT_ACTION("onNextAction"),
        EVENT_WILL_EXPOSURE("onViewWillExposure"),
        EVENT_ON_CLICK("onClicked"),
        EVENT_ON_TICK("onTick"),
        EVENT_WILL_LEAVE_APP("onViewWillLeaveApplication"),
        EVENT_WILL_OPEN_FULL_SCREEN("onViewWillPresentFullScreenModal"),
        EVENT_DID_OPEN_FULL_SCREEN("onViewDidPresentFullScreenModal"),
        EVENT_WILL_CLOSE_FULL_SCREEN("onViewWillDismissFullScreenModal"),
        EVENT_DID_CLOSE_FULL_SCREEN("onViewDidDismissFullScreenModal");

        private final String mName;

        Events(final String name) {
            mName = name;
        }

        @Override
        public String toString() {
            return mName;
        }
    }

    private FrameLayout mContainer;
    private RCTEventEmitter mEventEmitter;
    private ThemedReactContext mThemedReactContext;
    private Splash mSplash;
    private int fetchDelay = 0;

    private int minSplashTimeWhenNoAD = 2000;
    private long fetchSplashADTime = 0;
    private Handler handler = new Handler(Looper.getMainLooper());


    @Override
    protected View createViewInstance(ThemedReactContext reactContext) {
        mThemedReactContext = reactContext;
        mEventEmitter = reactContext.getJSModule(RCTEventEmitter.class);
        FrameLayout viewGroup = new FrameLayout(reactContext);
        mContainer = viewGroup;
        return viewGroup;
    }

    @Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        MapBuilder.Builder<String, Object> builder = MapBuilder.builder();
        for (Events event : Events.values()) {
            builder.put(event.toString(), MapBuilder.of("registrationName", event.toString()));
        }
        return builder.build();
    }

    // 其中，可以通过@ReactProp（或@ReactPropGroup）注解来导出属性的设置方法。
    // 该方法有两个参数，第一个参数是泛型View的实例对象，第二个参数是要设置的属性值。
    // 方法的返回值类型必须为void，而且访问控制必须被声明为public。
    // 组件的每一个属性的设置都会调用Java层被对应ReactProp注解的方法
    @ReactProp(name = "appInfo")
    public void setAppInfo(FrameLayout view, ReadableMap appInfo) {
        String appID = appInfo.getString("appId");
        String posID = appInfo.getString("posId");
        Log.e(TAG, "fetchDelay: " + fetchDelay);
        fetchSplashADTime = System.currentTimeMillis();
        Splash splash = new Splash(mThemedReactContext.getCurrentActivity(), appID, posID, this, this.fetchDelay);
        mSplash = splash;
        view.addView(splash);
    }

    @ReactProp(name = "showLogo")
    public void showLogo(FrameLayout view, boolean show) {
        if (mSplash != null) {
            mSplash.showLogo(show);
        }
    }

    /***
     * 需要放在 appInfo 属性之后设置才能生效
     * @param view
     * @param delay
     */
    @ReactProp(name = "fetchDelay")
    public void setFetchDelay(FrameLayout view, int delay) {
        this.fetchDelay = delay;
    }

}
