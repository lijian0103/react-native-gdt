
# react-native-gdt

## Getting started

`$ npm install react-native-gdt-ad --save`

### Mostly automatic installation

`$ react-native link react-native-gdt-ad`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import cn.cnlee.commons.CommonPackage;` to the imports at the top of the file
  - Add `new CommonPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-gdt'
  	project(':react-native-gdt').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-gdt/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      implementation project(':react-native-gdt')
  	```


## Usage
1. Splash 广告示例
```javascript
import GDT from 'react-native-gdt-ad';

<GDT.Splash style={{flex: 1}}
   onFailToReceived={(error) => {
       console.log(error);
   }}
   onNextAction={() => {
       //下一步路由动作
   }}
   showLogo={true}
   appInfo={{appId: '1101152570', posId: '8863364436303842593'}}
   fetchDelay={4000}
/>
```
2. Banner 广告示例
```javascript
<GDT.Banner style={{height: 64}}
   onReceived={() => {
   }}
   onViewWillExposure={() => {
   }}
   onFailToReceived={(err) => console.log(err)}
   showCloseBtn={false}
   appInfo={{appId: '1101152570', posId: '9079537218417626401'}}
/>
```
3. Banner2.0 广告示例
```javascript
<GDT.UnifiedBanner style={{height: 66}}
   onReceived={() => {
   }}
   onViewWillExposure={() => {
   }}
   onFailToReceived={(err) => console.log(err)}
   interval={50}
   appInfo={{appId: '1101152570', posId: '4080052898050840'}}
 />
```
4. 插屏 广告示例
```javascript
<TouchableOpacity
    activeOpacity={0.9}
    onPress={() => {
        GDT.Module.showInterstitialAD('1101152570', '8575134060152130849', true);
    }}>
    <Text style={{color: 'black', fontSize: 12,}}>插屏广告</Text>
</TouchableOpacity>
```
5. 插屏2.0 广告示例
```javascript
<TouchableOpacity
    activeOpacity={0.9}
    onPress={() => {
        GDT.Module.showUnifiedInterstitialAD('1101152570', '3040652898151811', true);
    }}>
    <Text style={{color: 'black', fontSize: 12,}}>插屏2.0广告</Text>
</TouchableOpacity>
```
6.  HYBRID广告示例
```javascript
<TouchableOpacity
    activeOpacity={0.9}
    onPress={() => {
        GDT.Module.openWeb('1101152570', 'http://m.baidu.com', {title: '测试标题', titleBarHeight: 45, titleSize: 20, titleColor: '#ff0000ff'});
    }}>
    <Text style={{color: 'black', fontSize: 12,}}>HYBRID广告</Text>
</TouchableOpacity>
```
## License
MIT