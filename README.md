PLib
=================
PLib是一个Android应用开发库，集成了流行的开源库，整合一些Util,可以帮助开发者更快开发应用

整合开源库：
-------------
1.OrmLite 4.48<br />
2.gson 2.3<br />
3.eventbus 2.2.1<br />

功能:
-------------
1.View及Fragment（只能用V4)注入<br />
2.SharedPreference封装
3.Toast封装
4.Log封装
5.其他一些常用Utils(md5,DES,日期处理,字符串处理,图片处理,网络判断,首次运行检测等)


使用方法：
-----
PLib是一个Library项目，将它引入到你的项目中，Application继承PApplication,所有Activity继承PActivity或PFragmentActivity,所有Fragment继承PFragment或PDialogFragment.


**1.View及Fragment注入**<br />
在Activity类或Fragment类加上Inflat注解，value是layoutId，如<br />
@Inflat(R.layout.main)<br />
所有View字段加上ViewById注解，如<br />
@ViewById(R.id.layout_drawer)<br />
DrawerLayout layout_drawer;<br />
完成后，PLib会自动帮您setContentView以及findViewById,Fragment注入类似，但仅适用v4<br />
**2.SharedPreference封装** <br/>
使用SharedPreference可以串联：
PreferenceManager.getDefaultManager().putBoolean(KEY1,Value1).putString(KEY2,Value2).commit();
最后加Commit()方法即可保存，支持指定保存的文件
**3.Toast封装**
Toast封装后，支持在非UI线程直接调用：
PToast.show(message);
**4.Log封装**
PLog类支持更多的参数类型，如Tag可以直接传入Object,会取object的类名作tag,Message支持基本类型.
**其他**
md5,DES,日期处理,字符串处理,图片处理,网络判断,首次运行检测等