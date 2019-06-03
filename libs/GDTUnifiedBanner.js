import React, {PureComponent} from 'react';
import {requireNativeComponent, View} from 'react-native';
import PropTypes from 'prop-types';

const RNGDTUnifiedBanner = requireNativeComponent("GDTUnifiedBanner", GDTUnifiedBanner);

export default class GDTUnifiedBanner extends PureComponent {

    static propTypes = {

        ...View.propTypes, //包含默认的View的属性，如果没有这句会报‘has no propType for native prop’错误

        appInfo: PropTypes.shape({
            appId: PropTypes.string,
            posId: PropTypes.string,
        }),
        /**
         *  广告刷新间隔 [可选][30-120] 单位为 s, 0表示不自动轮播,默认30S
         */
        interval: PropTypes.number,//需要写在appInfo属性之前才能生效
        /**
         *  请求广告条数据成功后调用
         *  详解:当接收服务器返回的广告数据成功后调用该函数
         */
        onReceived: PropTypes.func,
        /**
         *  请求广告条数据失败后调用
         *  详解:当接收服务器返回的广告数据失败后调用该函数
         */
        onFailToReceived: PropTypes.func,
        /**
         *  应用进入后台时调用
         *  详解:当点击应用下载或者广告调用系统程序打开，应用将被自动切换到后台
         */
        onViewWillLeaveApplication: PropTypes.func,
        /**
         *  banner条被用户关闭时调用
         *  详解:当打开showCloseBtn开关时，用户有可能点击关闭按钮从而把广告条关闭
         */
        onViewWillClose: PropTypes.func,
        /**
         *  banner条曝光回调
         */
        onViewWillExposure: PropTypes.func,
        /**
         *  banner条点击回调
         */
        onClicked: PropTypes.func,
        /**
         *  banner广告点击以后即将弹出全屏广告页
         */
        onViewWillPresentFullScreenModal: PropTypes.func,
        /**
         *  banner广告点击以后弹出全屏广告页完毕
         */
        onViewDidPresentFullScreenModal: PropTypes.func,
        /**
         *  全屏广告页即将被关闭
         */
        onViewWillDismissFullScreenModal: PropTypes.func,
        /**
         *  全屏广告页已经被关闭
         */
        onViewDidDismissFullScreenModal: PropTypes.func,
    };

    _onFailToReceived(event) {
        this.props.onFailToReceived && this.props.onFailToReceived(new Error(event.nativeEvent.error))
    }

    render() {
        return <RNGDTUnifiedBanner {...this.props} onFailToReceived={this._onFailToReceived.bind(this)}/>;
    }
}