package cn.cnlee.commons.gdt;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonPackage implements ReactPackage{

    // 必须重写该方法. 将我们创建的Modules注册到模块管理列表中
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.asList(new NativeModule[]{
                new GDTModule(reactContext)
        });
    }

    //必须重写该方法. 将我们创建的原生View注册到视图管理列表中
    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        List<ViewManager> list = new ArrayList<>();
        list.add(new GDTBannerViewManager());
        list.add(new GDTUnifiedBannerViewManager());
        list.add(new GDTSplashViewManager());
        return list;
    }
}
