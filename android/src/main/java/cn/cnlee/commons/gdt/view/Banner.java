package cn.cnlee.commons.gdt.view;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.BannerADListener;
import com.qq.e.ads.banner.BannerView;

import cn.cnlee.commons.gdt.R;


public class Banner extends FrameLayout {

    private BannerView mBanner;
    private Runnable mLayoutRunnable;

    public Banner(Context context, String appID, String posID, BannerADListener listener) {
        super(context);
        // 把布局加载到这个View里面
        inflate(context, R.layout.layout_banner,this);
        initView(appID, posID, listener);
    }

    /**
     * 初始化View
     */
    private void initView(String appID, String posID, BannerADListener listener) {
        closeBanner();
        mBanner = new BannerView((Activity) this.getContext(), ADSize.BANNER,appID, posID);
        mBanner.setADListener(listener);
        mBanner.setRefresh(30);
        addView(mBanner);
        getBanner().loadAD();
    }

    public void closeBanner() {
        removeAllViews();
        if (mBanner != null) {
            mBanner.destroy();
            mBanner = null;
            Log.e("UnifiedBanner","关闭广告");
        }
        if (mLayoutRunnable != null){
            removeCallbacks(mLayoutRunnable);
        }
    }

    public BannerView getBanner() {
        return mBanner;
    }

    public void setInterval(int interval) {
        getBanner().setRefresh(interval);
    }

    public void setShowCloseBtn(boolean showCloseBtn) {
        getBanner().setShowClose(showCloseBtn);
    }

    @Override
    protected void onDetachedFromWindow() {
        closeBanner();
        super.onDetachedFromWindow();
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        if (mLayoutRunnable != null){
            removeCallbacks(mLayoutRunnable);
        }
        mLayoutRunnable = new Runnable() {
            @Override
            public void run() {
                measure(
                        MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
                layout(getLeft(), getTop(), getRight(), getBottom());
            }
        };
        post(mLayoutRunnable);
    }
}
