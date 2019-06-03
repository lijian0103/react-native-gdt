
import { NativeModules } from 'react-native';
import Banner from './libs/GDTBanner';
import UnifiedBanner from './libs/GDTUnifiedBanner';
import Splash from './libs/GDTSplash';
const Module = NativeModules.GDTModule;

const GDT = {
    Banner,
    UnifiedBanner,
    Splash,
    Module
};
module.exports = GDT;