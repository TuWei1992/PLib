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
1.View注入


使用方法：
-----
PLib是一个Library项目，将它引入到你的项目中，Application继承PApplication,所有Activity继承PActivity或PFragmentActivity,所有Fragment继承PFragment或PDialogFragment.


**1.View注入**<br />
在Activity类或Fragment类加上Inflat注解，value是layoutId，如<br />
@Inflat(R.layout.main)<br />
所有View字段加上ViewById注解，如<br />
@ViewById(R.id.layout_drawer)<br />
DrawerLayout layout_drawer;<br />
完成后，PLib会自动帮您setContentView以及findViewById<br />