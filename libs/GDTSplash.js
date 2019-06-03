import React, {PureComponent} from 'react';
import {requireNativeComponent, View, StatusBar} from 'react-native';
import PropTypes from 'prop-types';

const  RNGDTSplash = requireNativeComponent("GDTSplash", GDTSplash);

export default class GDTSplash extends PureComponent {

    static propTypes = {
        ...View.propTypes, //包含默认的View的属性，如果没有这句会报‘has no propType for native prop’错误

        appInfo: PropTypes.shape({
            appId: PropTypes.string,
            posId: PropTypes.string,
        }),
        /**
         * 拉取广告的超时时长：即开屏广告从请求到展示所花的最大时长（并不是指广告曝光时长）取值范围[3000, 5000]，设为0表示使用广点通 SDK 默认的超时时长。
         */
        fetchDelay: PropTypes.number,
        /**
         *是否显示app logo
         */
        showLogo: PropTypes.bool, //需要写在appInfo属性之前才能生效
        /**
         *  请求广告条数据成功后调用
         *  详解:当接收服务器返回的广告数据成功后调用该函数
         */
        onPresent: PropTypes.func,
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
         *  splash曝光回调
         */
        onViewWillExposure: PropTypes.func,
        /**
         *  splash点击回调
         */
        onClicked: PropTypes.func,
        /**
         *  splash广告关闭消失
         */
        onDismissed: PropTypes.func,
        /**
         *  splash广告下一步路由动作
         */
        onNextAction: PropTypes.func,
        /**
         *  splash广告倒计时回调
         */
        onTick: PropTypes.func,
        /**
         *  splash广告点击以后即将弹出全屏广告页
         */
        onViewWillPresentFullScreenModal: PropTypes.func,
        /**
         *  splash广告点击以后弹出全屏广告页完毕
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
        StatusBar.setHidden(true);
        return <RNGDTSplash {...this.props} onFailToReceived={this._onFailToReceived.bind(this)}/>;
    }
}