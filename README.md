
# react-native-gdt

## Getting started

`$ npm install react-native-gdt --save`

### Mostly automatic installation

`$ react-native link react-native-gdt`

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
      compile project(':react-native-gdt')
  	```


## Usage
```javascript
import GDT from 'react-native-gdt';

// TODO: What to do with the module?
GDT;
```
  