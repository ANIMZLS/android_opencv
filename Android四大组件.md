# Activity（活动）

### 一、启停

#### 1.1 从当前页面跳到新页面

```java
startActivity(new Intent(源页面.this, 目标页面.class));
```

#### 1.2 从当前页面回到上一个页面，相当于关闭当前页面

```java
finsh();
```

#### 1.3 生命周期

![Activity](D:\Program Files\Android学习\img_MD\Activity.jpg)

+ onCreate：创建活动。把页面布局加载进内存，进入了初始状态。
+ onStart：开始活动。把活动页面显示在屏幕上，进入了就绪状态。
+ onResume：恢复活动。活动页面进入活跃状态，能够与用户正常交互，例如允许响应用户的点击动作、允许用户输入文字等等。
+ onPause：暂停活动。页面进入暂停状态，无法与用户正常交互。
+ onStop：停止活动。页面将不在屏幕上显示。
+ onDestroy：销毁活动。回收活动占用的系统资源，把页面从内存中清除。
+ onRestart：重启活动。重新加载内存中的页面数据。
+ onNewIntent：重用已有的活动实例。

四条途径---------![笔记-Android 开发从入门到实战](D:\Program Files\Android学习\img_MD\笔记-Android 开发从入门到实战.jpg)

#### 1.4 启动模式

活动栈（Task Stack），符合出栈入栈先进后出。

##### 1.4.1 启动模式：

```java
//在AndroidManifest.xml中设置
<activity android:name=".JumpFirstActivity" android:launchMode="standard" />
```

launchMode属性的几种取值：

![微信图片_20221106213011](D:\Program Files\Android学习\img_MD\微信图片_20221106213011.jpg)

//在Java代码中实现

* 在两个活动之间交替跳转,A/B活动中都要设置同样的启动标志,这样做就不会返回很多次A/B。

```java
// 创建一个意图对象，准备跳到指定的活动页面
Intent intent = new Intent(this, JumpSecondActivity.class);
//Intent intent = new Intent(this, JumpFirstctivity.class);
// 当栈中存在待跳转的活动实例时，则重新创建该活动的实例，并清除原实例上方的所有实例
intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 设置启动标志
startActivity(intent); // 跳转到意图对象指定的活动页面
```

* 登录成功后不再返回登录页面

```java
// 创建一个意图对象，准备跳到指定的活动页面
Intent intent = new Intent(this, LoginSuccessActivity.class);
// 设置启动标志：跳转到新页面时，栈中的原有实例都被清空，同时开辟新任务的活动栈
intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
				Intent.FLAG_ACTIVITY_NEW_TASK);
startActivity(intent); // 跳转到意图指定的活动页面
```

##### 1.4.2 动态设置启动模式

```java
intent.setFlags();//通过 Intent 动态设置 Activity启动模式
//如果同时有动态和静态设置，那么动态的优先级更高。
```

以上启动标志的取值说明如下：

* Intent.FLAG_ACTIVITY_NEW_TASK：开辟一个新的任务栈
* Intent.FLAG_ACTIVITY_SINGLE_TOP：当栈顶为待跳转的活动实例之时，则重用栈顶的实例
* Intent.FLAG_ACTIVITY_CLEAR_TOP：当栈中存在待跳转的活动实例时，则重新创建一个新实例，
  并清除原实例上方的所有实例
* Intent.FLAG_ACTIVITY_NO_HISTORY：栈中不保存新启动的活动实例
* Intent.FLAG_ACTIVITY_CLEAR_TASK：跳转到新页面时，栈中的原有实例都被清空

### 二、显式Intent和隐式Intent（意图）

**Intent是各个组件之间信息沟通的桥梁，它用于Android各组件之间的通信。**

#### 2.1 组成部分：

![微信截图_20221107131908](D:\Program Files\Android学习\img_MD\微信截图_20221107131908.png)

#### 2.2.1 显式意图,直接指定来源活动与目标活动

* 在Intent的构造函数中指定，示例代码如下：

```java
Intent intent = new Intent(this, ActNextActivity.class); // 创建一个目标确定的意图
startActivity(intent);
```

* 调用意图对象的setClass方法指定，示例代码如下：

```java
Intent intent = new Intent(); // 创建一个新意图
intent.setClass(this, ActNextActivity.class); // 设置意图要跳转的目标活动
startActivity(intent);
```

* 调用意图对象的setComponent方法指定，示例代码如下：

```java
Intent intent = new Intent(); // 创建一个新意图
// 创建包含目标活动在内的组件名称对象
ComponentName component = new ComponentName(this, ActNextActivity.class);
intent.setComponent(component); // 设置意图携带的组件信息
startActivity(intent);
```

#### 2.2.2 隐式Intent，没有明确指定要跳转的目标活动，只给出一个动作字符串让系统自动匹配

| Intent 类的系统动作常量名   | 系统动作的常量值 | 说明 |
| --------- | ----------- |---------|
ACTION_MAIN| android.intent.action.MAIN |App启动时的入口
ACTION_VIEW |android.intent.action.VIEW |向用户显示数据
ACTION_SEND |android.intent.action.SEND |分享内容
ACTION_CALL |android.intent.action.CALL |直接拨号
ACITON_DIAL |android.intent.action.DIAL |准备拨号
ACTION_SENDTO| android.intent.action.SENDTO| 发送短信
ACTION_ANSWER |android.intent.action.ANSWER |接听电话

```java
case R.id.btn_dial:
    // 设置意图动作为准备拨号
    intent.setAction(Intent.ACTION_DIAL);
    // 声明一个拨号的Uri
    Uri uri = Uri.parse("tel:" + phoneNo);
    intent.setData(uri);
    startActivity(intent);
    break;
```

#### 2.3 数据传递

##### 2.3.1 发送数据

1. Intent使用Bundle对象存放待传递的数据信息
2. Bundle对象操作各类型数据的读写方法说明见下表

![微信截图_20221107143100](D:\Program Files\Android学习\img_MD\微信截图_20221107143100.png)

在代码中发送消息包裹，调用意图对象的putExtras方法，即可存入消息包裹。
在代码中接收消息包裹，调用意图对象的getExtras方法，即可取出消息包裹。

##### 2.3.2 返回数据

1. 第一个页面利用bundle打包数据，调用startActivityForResult方法执行跳转动作
2. 第二个页面接收并解析请求数据，进行相应处理
3. 在返回上一个页面时，打包应答数据并调用setResult方法返回数据包裹
4. 第一个页面重写方法onActivityResult，解析获得下一个页面的返回数据

```java
register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
}
```

* 它可以取代 `startActivityForResult`方法，获取另一个Activity的返回结果。

* 它可以取代 `requestPermissions`方法，请求运行时权限。

```Java
//第一个页面的包裹
Intent intent = new Intent(this, ActResponseActivity.class);
// 创建一个新包裹
Bundle bundle = new Bundle();
bundle.putString("request_time", DateUtil.getNowTime());
bundle.putString("request_content", mRequest);
intent.putExtras(bundle);

//从上一个页面传来的意图中获取快递包裹
Bundle bundle = getIntent().getExtras();
String request_time = bundle.getString("request_time");
String request_content = bundle.getString("request_content");
String desc = String.format("收到请求消息：\n请求时间为%s\n请求内容为%s", request_time, request_content);

//第二个页面的包裹
Intent intent = new Intent();
Bundle bundle = new Bundle();
bundle.putString("response_time", DateUtil.getNowTime());
bundle.putString("response_content", mReponse);
intent.putExtras(bundle);
// 携带意图返回上一个页面。RESULT_OK表示处理成功
setResult(Activity.RESULT_OK, intent);
// 结束当前的活动页面，必须要有
finish();

//验证返回消息并解析
register = registerForActivityResult(new    ActivityResultContracts.StartActivityForResult(), result -> {
    if (result != null) {
        Intent intent = result.getData();//
        if (intent != null && result.getResultCode() == Activity.RESULT_OK) {
            Bundle bundle = intent.getExtras();
            String response_time = bundle.getString("response_time");
            String response_content = bundle.getString("response_content");
            String desc = String.format("收到返回消息：\n应答时间为%s\n应答内容为%s", response_time, response_content);
// 把返回消息的详情显示在文本视图上
            tv_response.setText(desc);
        }
     }
});
```

#### 2.4 附加信息

##### 2.4.1 资源文件配置字符串

1. 在values\strings.xml资源文件中配置字符串`<string name="weather_str">晴天</string>`

##### 2.4.2 利用元数据传递配置信息

1. 在AndroidManifest.xml配置文件中对应的activity节点写入

```xml
<activity android:name=".MetaDataActivity">
<meta-data android:name="weather" android:value="晴天" />
</activity>
```

2. 也可引用strings.xml已定义的字符串资源，引用格式形如“@string/字符串的资源名称”。

```xml
<activity android:name=".MetaDataActivity">
<meta-data
android:name="weather"
android:value="@string/weather_str" />
</activity>
```

3. 配置好了activity节点的meta-data标签，再回到Java代码获取元数据信息：

+ 调用getPackageManager方法获得当前应用的包管理器；
+ 调用包管理器的getActivityInfo方法获得当前活动的信息对象；
+ 活动信息对象的metaData是Bundle包裹类型，调用包裹对象的getString即可获得指定名称的参数值；

```java
PackageManager pm = getPackageManager();
try {
    // 从应用包管理器中获取当前的活动信息
    ActivityInfo info = pm.getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
    // 获取活动附加的元数据信息
    Bundle bundle = info.metaData;
    String weather = bundle.getString("weather");
    tv_meta.setText(weather);
} catch (PackageManager.NameNotFoundException e) {
    e.printStackTrace();
}
```

#### 2.5 给应用界面注册快捷方式

```xml
<?xml version="1.0" encoding="utf-8"?>
<shortcuts xmlns:android="http://schemas.android.com/apk/res/android">

    <shortcut
        android:enabled="true"
        android:icon="@mipmap/ic_launcher"
        android:shortcutId="first"
        android:shortcutLongLabel="@string/first_long"
        android:shortcutShortLabel="@string/first_short">
//在string资源文件中配置过的字符串
        <intent
            android:action="android.intent.action.VIEW"
            android:targetClass="com.dongnaoedu.chapter04.ActStartActivity"
            android:targetPackage="com.dongnaoedu.chapter04" />
        <categories android:name="android.shortcut.conversation" />
    </shortcut>
</shortcuts>
```

1. 一个shortcut标签表示一个快捷方式，必须有shortcutId和shortcutShortLabel；其他值都是可选的。各个属性定义：

+ shortcutId: ID
+ shortcutShortLabel:简短的短语，描述了快捷方式的用途。
+ shortcutLongLabel:描述快捷方式用途的扩展短语。优先展示长标签的文本，长标签放不下时才展示短标签的文本。
+ shortcutDisabledMessage:当用户尝试启动禁用的快捷方式时，在受支持的启动器中显示的消息。该消息应向用户说明为什么现在禁用了快捷方式。
+ enabled:默认值是true，如果将其设置为false，则应在android:shortcutDisabledMessage中说明为什么禁用了快捷方式。如果您不需要提供此类消息，则最简单的方法是从XML文件中完全删除快捷方式。
+ icon:图标，也可以是包含图像的资源文件。

2. 元数据meta-data标签的resource属性：

```xml
 <activity
            android:name=".MainActivity"
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
	<!-- 指定快捷方式。在桌面上长按应用图标，就会弹出@xml/shortcuts所描述的快捷菜单 -->
            <meta-data
                android:name="android.app.shortcuts"
                android:resource="@xml/shortcuts" />
//该属性可指定一个XML文件，表示元数据想要的复杂信息保存于XML数据之中。
        </activity>
```

# 数据存储

### 一、共享参数

#### 1.1 SharedPreferences（共享参数）

1. SharedPreferences是Android的一个轻量级存储工具，它采用的存储结构是Key-Value的键值对方式。
2. 共享参数的存储介质是符合XML规范的配置文件。保存路径是：/data/data/应用包名/s
   hared_prefs/文件名.xml。

共享参数主要适用于如下场合：

* 简单且孤立的数据。若是复杂且相互间有关的数据，则要保存在数据库中。
* 文本形式的数据。若是二进制数据，则要保存在文件中。
* 需要持久化存储的数据。在App退出后再次启动时，之前保存的数据仍然有效。

实际开发中，共享参数经常存储的数据有App的个性化配置信息、用户使用App的行为信
息、临时需要保存的片段信息等。

共享参数对数据的存储和读取操作类似于Map，也有存储数据的put方法，以及读取数据的get方法。调用getSharedPreferences方法可以获得共享参数实例，获取代码示例如下：

```java
// 从config.xml获取共享参数实例
SharedPreferences preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
reload();//一个加载保存数据的方法
```

```java
private void reload() {
    String name = preferences.getString("name", null);
    if (name != null) {
        et_name.setText(name);
    }

    int age = preferences.getInt("age", 0);
    if (age != 0) {
        et_age.setText(String.valueOf(age));
    }

    float height = preferences.getFloat("height", 0f);
    if (height != 0f) {
        et_height.setText(String.valueOf(height));
    }

    float weight = preferences.getFloat("weight", 0f);
    if (weight != 0f) {
        et_weight.setText(String.valueOf(weight));
    }

    boolean married = preferences.getBoolean("married", false);
    ck_married.setChecked(married);
}
```

往共享参数存储数据要借助于Editor类，保存数据的代码示例如下：

```java
SharedPreferences.Editor editor = preferences.edit(); // 获得编辑器的对象
editor.putString("name", name); // 添加一个名为name的字符串参数
editor.putInt("age", Integer.parseInt(age)); // 添加一个名为age的整型参数
editor.putFloat("height", Float.parseFloat(height)); 
editor.putFloat("weight", Float.parseFloat(weight)); // 添加一个名为weight的浮点数参数
editor.putBoolean("married", ck_married.isChecked());// 添加一个名为married的布尔型参数
editor.commit(); // 提交编辑器中的修改
```

### 二、数据库SQLite

#### 2.1 数据定义语言

数据定义语言全称Data Definition Language，简称DDL，它描述了怎样变更数据实体的框架结构。就SQLite而言，DDL语言主要包括3种操作：创建表格、删除表格、修改表结构

1. 创建表格
   表格的创建动作由create命令完成，格式为“CREATE　TABLE　IF　NOT　EXISTS　表格名称（以逗号分隔的各字段定义）；”。以用户信息表为例，它的建表语句如下所示：

```SQL
CREATE TABLE IF NOT EXISTS user_info (
	_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	name VARCHAR NOT NULL,
	age INTEGER NOT NULL,
	height LONG NOT NULL,
	weight FLOAT NOT NULL,
	married INTEGER NOT NULL,
	update_time VARCHAR NOT NULL);
```

上面的SQL语法与其他数据库的SQL语法有所出入，相关的注意点说明见下：

* ①SQL语句不区分大小写，无论是create与table这类关键词，还是表格名称、字段名称，都不区分大小写。唯一区分大小写的是被单引号括起来的字符串值。
* ②为避免重复建表，应加上IF　NOT　EXISTS关键词，例如CREATE　TABLE　IF　NOT EXISTS　表格名称……
* ③SQLite支持整型INTEGER、长整型LONG、字符串VARCHAR、浮点数FLOAT，但不支持布尔类型。布尔类型的数据要使用整型保存，如果直接保存布尔数据，在入库时SQLite会自动将它转为0或1，其中0表示false，1表示true。
* ④建表时需要唯一标识字段，它的字段名为id。创建新表都要加上该字段定义，例如id INTEGER　PRIMARY　KEY　AUTOINCREMENT　NOT　NULL。

2. 删除表格

```sql
DROP TABLE IF EXISTS user_info;
```

3. 修改表结构

SQLite只支持增加字段，不支持修改字段，也不支持删除字段

注意，SQLite的ALTER语句每次只能添加一列字段，若要添加多列，就得分多次添加。

```sql
ALTER TABLE user_info ADD COLUMN phone VARCHAR;
```

#### 2.2 数据操纵语言

1. 添加记录

```sql
INSERT INTO user_info (name,age,height,weight,married,update_time)
VALUES ('张三',20,170,50,0,'20200504');
```

2. 删除记录

多个字段的条件交集通过“AND”连接，条件并集通过“OR”连接。

```sql
DELETE FROM user_info WHERE name='张三';
```

3. 修改记录

```sql
UPDATE user_info SET married=1 WHERE name='张三';
```

4. 查询记录

格式为“SELECT　以逗号分隔的字段名列表　FROM　表格名称　WHERE　查询条件;”如果字段名列表填星号“*”，则表示查询该表的所有字段,

```sql
SELECT name FROM user_info WHERE name='张三';
```

表达式为“ORDER　BY　字段名　ASC或者DESC”，意指对查询结果按照某个字段排序，其中ASC
代表升序，DESC代表降序。

```sql
SELECT * FROM user_info ORDER BY age ASC;
```

```java
String sql = " select * from " + TABLE_NAME + "where remember = 1 RRDER BY _id DESC limit 1"; 
```

#### 2.3 数据库管理器SQLiteDatabase

SQLiteDatabase是SQLite的数据库管理类，它提供了若干操作数据表的API，常用的方
法有3类：

1. 管理类，用于数据库层面的操作。
   * openDatabase：打开指定路径的数据库。
   * isOpen：判断数据库是否已打开。
   * close：关闭数据库。
   * getVersion：获取数据库的版本号。
   * setVersion：设置数据库的版本号。

2. 事务类，用于事务层面的操作。事务具有一致性。

   ```java
   try {
       mWDB.beginTransaction();
       mWDB.insert(TABLE_NAME, null, values);
       //int i = 10 / 0;
       mWDB.insert(TABLE_NAME, null, values);
       mWDB.setTransactionSuccessful();
   } catch (Exception e) {
       e.printStackTrace();
   } finally {
       mWDB.endTransaction();
   }
   ```

   * beginTransaction：开始事务。
   * setTransactionSuccessful：设置事务的成功标志。
   * endTransaction：结束事务。执行本方法时，系统会判断之前是否调用了
     setTransactionSuccessful方法，如果之前已调用该方法就提交事务，如果没有调用该方法就回滚事务。

3. 数据处理类，用于数据表层面的操作。
   * execSQL：执行拼接好的SQL控制语句。
   * delete：删除符合条件的记录。
   * update：更新符合条件的记录。
   * insert：插入一条记录。
   * query：执行查询操作，返回结果集的游标。
   * rawQuery：执行拼接好的SQL查询语句，返回结果集的游标。

在实际开发中，比较经常用到的是查询语句，建议先写好查询操作的select语句，再调用rawQuery方法执行查询语句。

#### 2.4 数据库帮助器SQLiteOpenHelper

1. SQLiteOpenHelper的具体使用步骤如下：

* 新建一个继承自SQLiteOpenHelper的数据库操作类，提示重写onCreate和onUpgrade两个方法。
* 封装，保证数据库安全的必要方法。包括获取单例对象、打开数据库连接、关闭
  数据库连接，说明如下：
  * 获取单例对象：确保在App运行过程中数据库只会打开一次，避免重复打开引起错误。
  * 打开数据库连接：SQLite有锁机制，即读锁和写锁的处理；故而数据库连接也分两种，读连接可调用getReadableDatabase方法获得，写连接可调用getWritableDatabase获得。
  * 关闭数据库连接：数据库操作完毕，调用数据库实例的close方法关闭连接。

* 提供对表记录进行增加、删除、修改、查询的操作方法。
  + 能被SQLite直接使用的数据结构是ContentValues类，它类似于映射Map，也提供了put和get方法存取键值对。区别之处在于：ContentValues的键只能是字符串，不能是其他类型。ContentValues主要用于增加记录和更新记录，对应数据库的insert和update方法。

2. 记录的查询操作用到了游标类Cursor，调用query和rawQuery方法返回的都是Cursor对象，若要获取全部的查询结果，则需根据游标的指示一条一条遍历结果集合。Cursor的常用方法可分为3类，说明如下：

   * 游标控制类方法，用于指定游标的状态

     * close：关闭游标。

     * isClosed：判断游标是否关闭。

     * isFirst：判断游标是否在开头。

     * isLast：判断游标是否在末尾。

   * 游标移动类方法，把游标移动到指定位置
     * moveToFirst：移动游标到开头。
     * moveToLast：移动游标到末尾。
     * moveToNext：移动游标到下一条记录。
     * moveToPrevious：移动游标到上一条记录。
     * move：往后移动游标若干条记录。
     * moveToPosition：移动游标到指定位置的记录。
   * 获取记录类方法，可获取记录的数量、类型以及取值
     * getCount：获取结果记录的数量。
     * getInt：获取指定字段的整型值。
     * getLong：获取指定字段的长整型值。
     * getFloat：获取指定字段的浮点数值。
     * getString：获取指定字段的字符串值。
     * getType：获取指定字段的字段类型。

3. 数据库记录的添加、查询和删除操作，对应的数据库帮助器关键代码如下所示，尤其关注里面的insert、delete、update和query方法：

```java
public class UserDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "user.db";
    private static final String TABLE_NAME = "user_info";
    private static final int DB_VERSION = 2;
    private static UserDBHelper mHelper = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    private UserDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static UserDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new UserDBHelper(context);
        }
        return mHelper;
    }

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink() {
        if (mRDB == null || !mRDB.isOpen()) {
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink() {
        if (mWDB == null || !mWDB.isOpen()) {
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    // 关闭数据库连接
    public void closeLink() {
        if (mRDB != null && mRDB.isOpen()) {
            mRDB.close();
            mRDB = null;
        }

        if (mWDB != null && mWDB.isOpen()) {
            mWDB.close();
            mWDB = null;
        }
    }

    // 创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " name VARCHAR NOT NULL," +
                " age INTEGER NOT NULL," +
                " height LONG NOT NULL," +
                " weight FLOAT NOT NULL," +
                " married INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN phone VARCHAR;";
        db.execSQL(sql);
        sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN password VARCHAR;";
        db.execSQL(sql);
    }

    public long insert(User user) {
        ContentValues values = new ContentValues();
        values.put("name", user.name);
        values.put("age", user.age);
        values.put("height", user.height);
        values.put("weight", user.weight);
        values.put("married", user.married);
        // 执行插入记录动作，该语句返回插入记录的行号
        // 如果第三个参数values 为Null或者元素个数为0， 由于insert()方法要求必须添加一条除了主键之外其它字段为Null值的记录，
        // 为了满足SQL语法的需要， insert语句必须给定一个字段名 ，如：insert into person(name) values(NULL)，
        // 倘若不给定字段名 ， insert语句就成了这样： insert into person() values()，显然这不满足标准SQL的语法。
        // 如果第三个参数values 不为Null并且元素的个数大于0 ，可以把第二个参数设置为null 。
        //return mWDB.insert(TABLE_NAME, null, values);

        try {
            mWDB.beginTransaction();
            mWDB.insert(TABLE_NAME, null, values);
            //int i = 10 / 0;
            mWDB.insert(TABLE_NAME, null, values);
            mWDB.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mWDB.endTransaction();
        }

        return 1;
    }

    public long deleteByName(String name) {
        //删除所有
        //mWDB.delete(TABLE_NAME, "1=1", null);
        return mWDB.delete(TABLE_NAME, "name=?", new String[]{name});
    }

    public long update(User user) {
        ContentValues values = new ContentValues();
        values.put("name", user.name);
        values.put("age", user.age);
        values.put("height", user.height);
        values.put("weight", user.weight);
        values.put("married", user.married);
        return mWDB.update(TABLE_NAME, values, "name=?", new String[]{user.name});
    }

    public List<User> queryAll() {
        List<User> list = new ArrayList<>();
        // 执行记录查询动作，该语句返回结果集的游标
        Cursor cursor = mRDB.query(TABLE_NAME, null, null, null, null, null, null);
        // 循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            User user = new User();
            user.id = cursor.getInt(0);
            user.name = cursor.getString(1);
            user.age = cursor.getInt(2);
            user.height = cursor.getLong(3);
            user.weight = cursor.getFloat(4);
            //SQLite没有布尔型，用0表示false，用1表示true
            user.married = (cursor.getInt(5) == 0) ? false : true;
            list.add(user);
        }
        return list;
    }

    public List<User> queryByName(String name) {
        List<User> list = new ArrayList<>();
        // 执行记录查询动作，该语句返回结果集的游标
        Cursor cursor = mRDB.query(TABLE_NAME, null, "name=?", new String[]{name}, null, null, null);
        // 循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            User user = new User();
            user.id = cursor.getInt(0);
            user.name = cursor.getString(1);
            user.age = cursor.getInt(2);
            user.height = cursor.getLong(3);
            user.weight = cursor.getFloat(4);
            //SQLite没有布尔型，用0表示false，用1表示true
            user.married = (cursor.getInt(5) == 0) ? false : true;
            list.add(user);
        }
        return list;
    }
}
```

### 三、存储卡的文件操作

#### 3.1 私有存储空间与公共存储空间

```xml
<!-- 存储卡读写 -->
<!-- AndroidManifest.xml清单文件 -->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAG" />
```

```java
String directory = null;
// 外部存储的私有空间
directory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();
// 外部存储的公共空间
directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
```

#### 3.2 在存储卡上读写文本文件

几种IO流读写文件https://blog.csdn.net/zhao_yu_lei/article/details/72823874

```java
// 把字符串保存到指定路径的文本文件
public static void saveText(String path, String txt) {
    BufferedWriter os = null;
    try {
        os = new BufferedWriter(new FileWriter(path));
        os.write(txt);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

// 从指定路径的文本文件中读取内容字符串
public static String openText(String path) {
    BufferedReader is = null;
    StringBuilder sb = new StringBuilder();
    try {
        is = new BufferedReader(new FileReader(path));
        String line = null;
        while ((line = is.readLine()) != null) {
            sb.append(line);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    return sb.toString();
}
```

 #### 3.3 在存储卡上读写图片文件

1. 文本文件读写可以转换为对字符串的读写，而图片文件保存的是图像数据，需要专门的位图工具Bitmap处理。位图对象依据来源不同又分成3种获取方式，分别对应位图工厂BitmapFactory的下列3种方法：

   * decodeResource：从指定的资源文件中获取位图数据。例如下面代码表示从资源文件huawei.png获取位图对象：

   ```java
   Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.huawei);
   ```

   * decodeFile：从指定路径的文件中获取位图数据。注意从Android 10开始，该方法只适用于私有目录下的图片，不适用公共空间下的图片。
   * decodeStream：从指定的输入流中获取位图数据。比如使用IO流打开图片文件，此时文件输入流对象即可作为decodeStream方法的入参，相应的图片读取代码如下：

   ```java
   // 从指定路径的图片文件中读取位图数据
   public static Bitmap openImage(String path) {
   	Bitmap bitmap = null; // 声明一个位图对象
   	// 根据指定的文件路径构建文件输入流对象
   	try (FileInputStream fis = new FileInputStream(path)) {
   		bitmap = BitmapFactory.decodeStream(fis); //从文件输入流中解码位图数据
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
   		return bitmap; // 返回图片文件中的位图数据
   }
   ```

2. 得到位图对象之后，就能在图像视图上显示位图。图像视图ImageView提供了下列方法显示各种来源的图片：
   * setImageResource：设置图像视图的图片资源，该方法的入参为资源图片的编号，形如
     “R.drawable.去掉扩展名的图片名称”。
   * setImageBitmap：设置图像视图的位图对象，该方法的入参为Bitmap类型。
   * setImageURI：设置图像视图的路径对象，该方法的入参为Uri类型。字符串格式的文件路径可通过代码“Uri.parse(file_path)”转换成路径对象。
3. 代码示例：

* 从资源图片读取位图数据，再把位图数据保存为私有目录的图片文件，相关代码示例如下：

```java
    String fileName = System.currentTimeMillis() + ".jpeg";
    // 获取当前App的私有下载目录
    path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + File.separatorChar + fileName;
    Log.d("ning", path);
    // 从指定的资源文件中获取位图对象
    Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.huawei);
    // 把位图对象保存为图片文件
    FileUtil.saveImage(path, b1);//saveImage
```

```java
// 把位图数据保存到指定路径的图片文件
public static void saveImage(String path, Bitmap bitmap) {
    FileOutputStream fos = null;
    try {
        fos = new FileOutputStream(path);
        // 把位图数据压缩到文件输出流中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

* 从私有目录找到图片文件，并挑出一张在图像视图上显示，相关代码示例如下：

```java
//1. 先调用FileUtil.openImage获得位图，再调用setImageBitmap方法
Bitmap b2 = FileUtil.openImage(path);
iv_content.setImageBitmap(b2);
//2. 先调用BitmapFactory.decodeFile获得位图，再调用setImageBitmap方法
Bitmap b2 = BitmapFactory.decodeFile(path);
iv_content.setImageBitmap(b2); 
//3. 直接调用setImageURI方法，设置图像视图的路径 
iv_content.setImageURI(Uri.parse(path));
```

```java
    // 从指定路径的图片文件中读取位图数据
    public static Bitmap openImage(String path) {
        Bitmap bitmap = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            bitmap = BitmapFactory.decodeStream(fis); 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }
}
```

### 四、应用组件Application

#### 4.1 Application的生命周期

1. Application是Android的一大组件，在App运行过程中有且仅有一个Application对象贯穿应用的整个生命周期。不过该节点并未指定name属性，此时App采用默认的Application实例。现在尝试给application节点加上name属性，看看其庐山真面目，具体步骤说明如下：

* 打开AndroidManifest.xml，给application节点加上name属性，表示application的入口代码是MainApplication.java。
* 在Java代码的包名目录下创建MainApplication.java，要求该类继承Application，继承之后可供重写的方法主要有以下3个。
  * onCreate：在App启动时调用。
  * onTerminate：在App终止时调用（按字面意思）。
  * onConfigurationChanged：在配置改变时调用，例如从竖屏变为横屏。

```java
public class MainApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
	}
	@Override
	public void onTerminate() {
		super.onTerminate();
		Log.d(TAG, "onTerminate");
	}
}
```

* 运行测试App，在logcat窗口观察应用日志。但是只在启动一开始看到MainApplication的onCreate日志（该日志先于MainActivity的onCreate日志），却始终无法看到它的onTerminate日志，无论是自行退出App还是强行杀掉App，日志都不会打印onTerminate。

Android明明提供了这个方法，同时提供了关于该方法的解释: 该方法供模拟环境使用，它在真机上永远不会被调用，无论是直接杀进程还是代码退出；执行该操作时，不会执行任何用户代码。

#### 4.2 利用Application操作全局变量

1. 适合在Application中保存的全局变量主要有下面3类数据：
   	（1）会频繁读取的信息，例如用户名、手机号码等。
   	（2）不方便由意图传递的数据，例如位图对象、非字符串类型的集合对象等。
   	（3）容易因频繁分配内存而导致内存泄漏的对象，例如Handler处理器实例等。

要想通过Application实现全局内存的读写，得完成以下3项工作：

* 编写一个继承自Application的新类MainApplication。该类采用单例模式，内部先声明自身类的一个静态成员对象，在创建App时把自身赋值给这个静态对象，然后提供该对象的获取方法getInstance。

```java
public class MainApplication extends Application {
	private final static String TAG = "MainApplication";
	private static MainApplication mApp; // 声明一个当前应用的静态实例
    
	// 声明一个公共的信息映射对象，可当作全局变量使用
	public HashMap<String, String> infoMap = new HashMap<String, String>();
    
	// 利用单例模式获取当前应用的唯一实例
	public static MainApplication getInstance() {
		return mApp;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
		mApp = this; // 在打开应用时对静态的应用实例赋值
	}
}
```

* 在活动页面代码中调用MainApplication的getInstance方法，获得它的一个静态对象，再通过该对象访问MainApplication的公共变量和公共方法。

```java
app = MyApplication.getInstance();
```

* 不要忘了在AndroidManifest.xml中注册新定义的Application类名，也就是给application节点增加android:name属性，其值为.MainApplication。

#### 4.3 利用Room简化数据库操作

1. Android提供的数据库帮助器，每次增加一张新表，开发者都要从头实现数据库操作逻辑。

   （1）重写数据库帮助器的onCreate方法，添加该表的建表语句。
   （2）在插入记录之时，必须将数据实例的属性值逐一赋给该表的各字段。
   （3）在查询记录之时，必须遍历结果集游标，把各字段值逐一赋给数据实例。
   （4）每次读写操作之前，都要先开启数据库连接；读写操作之后，又要关闭数据库连接。

2. Room是谷歌公司推出的数据库处理框架，该框架同样基于SQLite，但它通过注解技术极
   大简化了数据库操作，减少了原来相当一部分编码工作量。

   在使用Room之前，要先修改模块的build.gradle文件，往dependencies节点添加下面两行配置，表示导入指定版本的Room库：

```java
implementation 'androidx.room:room-runtime:2.2.5'
annotationProcessor 'androidx.room:room-compiler:2.2.5'
//导入Room库之后，还要编写若干对应的代码文件。
```

3. Room框架的编码步骤

   **以录入书籍信息为例，使用Room框架的编码过程分为下列五步：**

* 编写书籍信息表对应的实体类，该类添加“@Entity”注解。

```java
@Entity
public class BookInfo {

    @PrimaryKey(autoGenerate = true)// 该字段是主键，不能重复
    private int id;

    private String name; // 书籍名称
    private String author; // 作者
    private String press; // 出版社
    private double price; // 价格

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return "BookInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", press='" + press + '\'' +
                ", price=" + price +
                '}';
    }
}
```

* 编写书 籍信息表对应的持久化类，该类添加“@Dao”注解。

```java
@Dao
public interface BookDao {

    @Insert
    void insert(BookInfo... book);

    @Delete
    void delete(BookInfo... book);

    // 删除所有书籍信息
    @Query("DELETE FROM BookInfo")
    void deleteAll();

    @Update
    int update(BookInfo... book);

    // 加载所有书籍信息
    @Query("SELECT * FROM BookInfo")
    List<BookInfo> queryAll();

    // 根据名字加载书籍
    @Query("SELECT * FROM BookInfo WHERE name = :name ORDER BY id DESC limit 1")
    BookInfo queryByName(String name);
}
```

* 编写书籍信息表对应的数据库类，该类从RoomDatabase派生而来，并添加“@Database”注解。

```java
//entities表示该数据库有哪些表，version表示数据库的版本号
//exportSchema表示是否导出数据库信息的json串，建议设为false，若设为true还需指定json文件的保存路径
@Database(entities = {BookInfo.class}, version = 1, exportSchema = true)
public abstract class BookDatabase extends RoomDatabase {
    // 获取该数据库中某张表的持久化对象
    public abstract BookDao bookDao();

}
```

```java
//若设为true还需指定json文件的保存路径，在build.gradle文件中
//testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"位置后
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = ["room.schemaLocation": 			"$projectDir/schemas".toString()]//指定数据库schema导出的位置
            }
        }
```

* 在自定义的Application类中声明书籍数据库的唯一实例。

```java
public class MyApplication extends Application {
	private final static String TAG = "MainApplication";
	private static MyApplication mApp; // 声明一个当前应用的静态实例
	// 声明一个公共的信息映射对象，可当作全局变量使用
	public HashMap<String, String> infoMap = new HashMap<String, String>();
    	
	private BookDatabase bookDatabase; // 声明一个书籍数据库对象
    
	// 利用单例模式获取当前应用的唯一实例
	public static MyApplication getInstance() {
		return mApp;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
		mApp = this; // 在打开应用时对静态的应用实例赋值
		// 构建书籍数据库的实例
		bookDatabase = Room.databaseBuilder(mApp, BookDatabase.class,"BookInfo")
            .addMigrations() 
            // 允许迁移数据库（发生数据库变更时，Room默认删除原数据库再创建新数据库。如此一来原来的记录会丢失，故而要改为迁移方式以便保存原有记录）
            .allowMainThreadQueries() 
            // 允许在主线程中操作数据库（Room默认不能在主线程中操作数据库）
            .build();
	}
	// 获取书籍数据库的实例
	public BookDatabase getBookDB(){
		return bookDatabase;
	}
}
```

* 在操作书籍信息表的地方获取数据表的持久化对象。

```java
// 从App实例中获取唯一的图书持久化对象
BookDao bookDao = MyApplication.getInstance().getBookDB().bookDao();
```

# Content Provider（内容提供者）

**ContentProvider为App存取内部数据提供统一的外部接口，让不同的应用之间得以共享数据。**

### 一、在应用之间共享数据

*案例：Client App 将用户的输入内容，通过ContentProvider 跨进程通信传递给Server App。*

![内容提供者案例](D:\Program Files\Android学习\img_MD\内容提供者案例.jpg)

#### 1.1 通过ContentProvider封装数据(server端)

1. ContentProvider只是服务端App存取数据的抽象类，开发者需要在其基础上实现一个完整的内容提供器，并重写下列数据库管理方法。

   * onCreate：创建数据库并获得数据库连接。
   * insert：插入数据。
   * delete：删除数据。
   * update：更新数据。
   * query：查询数据，并返回结果集的游标。
   * getType：获取内容提供器支持的数据类型。

2. ContentProvider作为中间接口，本身并不直接保存数据，而是通过SQLiteOpenHelper与SQLiteDatabase间接操作底层的数据库。所以要想使用ContentProvider，首先得实现SQLite的数据库帮助器，然后由ContentProvider封装对外的接口。以封装用户信息为例，具体步骤主要分成以下3步：

   + 编写用户信息表的数据库帮助器，这个数据库帮助器就是常规的SQLite操作代码，实现过程在数据库帮助器SQLiteOpenHelper”

   ```java
   public class UserDBHelper extends SQLiteOpenHelper {
   
       private static final String DB_NAME = "user.db";
       public static final String TABLE_NAME = "user_info";
       private static final int DB_VERSION = 1;
       private static UserDBHelper mHelper = null;
   
       private UserDBHelper(Context context) {
           super(context, DB_NAME, null, DB_VERSION);
       }
   
       // 利用单例模式获取数据库帮助器的唯一实例
       public static UserDBHelper getInstance(Context context) {
           if (mHelper == null) {
               mHelper = new UserDBHelper(context);
           }
           return mHelper;
       }
   
   
       // 创建数据库，执行建表语句
       @Override
       public void onCreate(SQLiteDatabase db) {
           String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                   "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                   " name VARCHAR NOT NULL," +
                   " age INTEGER NOT NULL," +
                   " height LONG NOT NULL," +
                   " weight FLOAT NOT NULL," +
                   " married INTEGER NOT NULL);";
           db.execSQL(sql);
       }
   
       @Override
       public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
   
       }
   
   }
   ```

   + 编写内容提供器的基础字段类，该类需要实现接口BaseColumns，同时加入几个常量定义。

   ```java
   public class UserInfoContent implements BaseColumns {
   
       public static final String AUTHORITIES = "com.dongnaoedu.chapter07_server.provider.UserInfoProvider";
   
       //content://com.dongnaoedu.chapter07_server.provider.UserInfoProvider/user
   
       // 访问内容提供器的URI
       public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITIES + "/user");
   
       // 下面是该表的各个字段名称
       public static final String USER_NAME = "name";
       public static final String USER_AGE = "age";
       public static final String USER_HEIGHT = "height";
       public static final String USER_WEIGHT = "weight";
       public static final String USER_MARRIED = "married";
   
   }
   ```

   + 通过右键菜单创建内容提供器，右击App模块的包名目录，在弹出的右键菜单中依次选择New→Other→Content Provider，打开如图所示的组件创建对话框。

![cp](D:\Program Files\Android学习\img_MD\cp.jpg)

​		在创建对话框的Class Name一栏填写内容提供器的名称，比如UserInfoProvider；在URI Authorities一
栏填写URI的授权串，比如“com.example.chapter07.provider.UserInfoProvider”；

​		上述创建过程会自动修改App模块的两处地方，一处是往AndroidManifest.xml添加内容提供器的注册
配置，配置信息示例如下：

```xml
<provider
    android:name=".provider.UserInfoProvider"
    android:authorities="com.example.server.provider.UserInfoProvider"
    android:enabled="true"
    android:exported="true" />
```

​		另一处是在包名目录下生成名为UserInfoProvider.java的代码文件，打开一看发现该类继承了
ContentProvider，并且提示重写onCreate、insert、delete、query、update、getType等方法，以便
对数据进行增删改查等操作。这个提供器代码显然只有一个框架，还需补充详细的实现代码，为此重写
onCreate方法，在此获取用户信息表的数据库帮助器实例，其他insert、delete、query等方法也要加入
对应的数据库操作代码，修改之后的内容提供器代码如下所示：

```java
public class UserInfoProvider extends ContentProvider {

    private UserDBHelper dbHelper;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int USERS = 1;
    private static final int USER = 2;

    static {
        // 往Uri匹配器中添加指定的数据路径
        URI_MATCHER.addURI(UserInfoContent.AUTHORITIES, "/user", USERS);
        URI_MATCHER.addURI(UserInfoContent.AUTHORITIES, "/user/#", USER);
    }

    @Override
    public boolean onCreate() {
        Log.d("ning", "UserInfoProvider onCreate");
        dbHelper = UserDBHelper.getInstance(getContext());
        return true;
    }

    // content://com.dongnaoedu.chapter07_server.provider.UserInfoProvider/user
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d("ning", "UserInfoProvider insert");
        if (URI_MATCHER.match(uri) == USERS) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long rowId = db.insert(UserDBHelper.TABLE_NAME, null, values);
            /*if (rowId > 0) { // 判断插入是否执行成功
                // 如果添加成功，就利用新记录的行号生成新的地址
                Uri newUri = ContentUris.withAppendedId(UserInfoContent.CONTENT_URI, rowId);
                // 通知监听器，数据已经改变
                getContext().getContentResolver().notifyChange(newUri, null);
            }*/
        }
        return uri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d("ning", "UserInfoProvider query");
        if (URI_MATCHER.match(uri) == USERS) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            return db.query(UserDBHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (URI_MATCHER.match(uri)) {
            //content://com.dongnaoedu.chapter07_server.provider.UserInfoProvider/user
            // 删除多行
            case USERS:
                SQLiteDatabase db1 = dbHelper.getWritableDatabase();
                count = db1.delete(UserDBHelper.TABLE_NAME, selection, selectionArgs);
                db1.close();
                break;

            //content://com.dongnaoedu.chapter07_server.provider.UserInfoProvider/user/2
            //删除单行
            case USER:
                String id = uri.getLastPathSegment();
                SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                count = db2.delete(UserDBHelper.TABLE_NAME, "_id=?", new String[]{id});
                db2.close();
                break;
        }

        return count;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
```

经过以上3个步骤之后，便完成了服务端App的接口封装工作，接下来再由其他App去访问服务端App的数据。

#### 1.2 通过ContentProvider访问数据(client端)

1. 如果客户端App想访问对方的内部数据，就要借助内容解析器ContentResolver。内容解析器是客户端App操作服务端数据的工具，与之对应的内容提供器则是服务端的数据接口。在活动代码中调用getContentResolver方法即可获取内容解析器的实例。ContentResolver提供的方法与ContentProvider一一对应，比如insert、delete、query、update、getType等，甚至连方法的参数类型都雷同。

```java
//添加操作
ContentValues values = new ContentValues();
values.put(UserInfoContent.USER_NAME, et_name.getText().toString());
values.put(UserInfoContent.USER_AGE, Integer.parseInt(et_age.getText().toString()));
values.put(UserInfoContent.USER_HEIGHT, Integer.parseInt(et_height.getText().toString()));
values.put(UserInfoContent.USER_WEIGHT, Float.parseFloat(et_weight.getText().toString()));
values.put(UserInfoContent.USER_MARRIED, ck_married.isChecked());
// content://com.dongnaoedu.chapter07_server.provider.UserInfoProvider/user
getContentResolver().insert(UserInfoContent.CONTENT_URI, values);

//删除操作
int count = getContentResolver().delete(UserInfoContent.CONTENT_URI, "name=?", new String[]{"Jack"});
```

​		查询操作稍微复杂一些，调用query方法会返回游标对象，这个游标正是SQLite的游标Cursor。query方法的输入参数有好几个，具体说明如下（依参数顺序排列）。

+ uri：Uri类型，指定本次操作的数据表路径。
+ projection：字符串数组类型，指定将要查询的字段名称列表。
+ selection：字符串类型，指定查询条件。
+ selectionArgs：字符串数组类型，指定查询条件中的参数取值列表。
+ sortOrder：字符串类型，指定排序条件。

```java
Cursor cursor = getContentResolver().query(UserInfoContent.CONTENT_URI, null, null, null, null);
if (cursor != null) {
    while (cursor.moveToNext()) {
        User info = new User();
        info.id = cursor.getInt(cursor.getColumnIndex(UserInfoContent._ID));
        info.name = cursor.getString(cursor.getColumnIndex(UserInfoContent.USER_NAME));
        info.age = cursor.getInt(cursor.getColumnIndex(UserInfoContent.USER_AGE));
        info.height = cursor.getInt(cursor.getColumnIndex(UserInfoContent.USER_HEIGHT));
        info.weight = cursor.getFloat(cursor.getColumnIndex(UserInfoContent.USER_WEIGHT));
        info.married = cursor.getInt(cursor.getColumnIndex(UserInfoContent.USER_MARRIED)) == 1 ? true : false;
        Log.d("TAG", info.toString());
    }
    cursor.close();
}
```

### 二、使用内容组件获取通讯信息

#### 2.1 运行时动态申请权限(Lazy模式/Hungry模式)

Android支持在Java代码中处理权限，处理过程分为3个步骤，详述如下：

1. **检查App是否开启了指定权限**

* 调用ContextCompat的checkSelfPermission方法

2. **请求系统弹窗，以便用户选择是否开启权限**

* 调用ActivityCompat的requestPermissions方法，即可命令系统自动弹出权限申请窗口

3. **判断用户的权限选择结果**

* 重写onRequestPermissionsResult方法，并在该方法内部处理用户的权限选择结果。

```java
public class PermissionUtil {

    // 检查多个权限。返回true表示已完全启用权限，返回false表示未完全启用权限
    public static boolean checkPermission(Activity act, String[] permissions, int requestCode) {
        // Android 6.0 之后开始采用动态权限管理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int check = PackageManager.PERMISSION_GRANTED;
            for (String permission : permissions) {
                check = ContextCompat.checkSelfPermission(act, permission);
                if (check != PackageManager.PERMISSION_GRANTED) {
                    break;
                }
            }
            // 未开启该权限，则请求系统弹窗，好让用户选择是否立即开启权限
            if (check != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(act, permissions, requestCode);
                return false;
            }
        }
        return true;
    }

    // 检查权限结果数组，返回true表示都已经获得授权。返回false表示至少有一个未获得授权
    public static boolean checkGrant(int[] grantResults) {
        if (grantResults != null) {
            // 遍历权限结果数组中的每条选择结果
            for (int grant : grantResults) {
                // 未获得授权
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
```

* Lazy模式

```java
public class PermissionLazyActivity extends AppCompatActivity implements View.OnClickListener {
//两个权限数组
    private static final String[] PERMISSIONS_CONTACTS = new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
    };

    private static final String[] PERMISSIONS_SMS = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS
    };

    private static final int REQUEST_CODE_CONTACTS = 1;
    private static final int REQUEST_CODE_SMS = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_lazy);
        findViewById(R.id.btn_contact).setOnClickListener(this);
        findViewById(R.id.btn_sms).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_contact:
                PermissionUtil.checkPermission(this, PERMISSIONS_CONTACTS, REQUEST_CODE_CONTACTS);
                break;

            case R.id.btn_sms:
                PermissionUtil.checkPermission(this, PERMISSIONS_SMS, REQUEST_CODE_SMS);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_CONTACTS:
                if (PermissionUtil.checkGrant(grantResults)) {
                    Log.d("ning", "通讯录权限获取成功");
                } else {
                    ToastUtil.show(this, "获取通讯录读写权限失败！");
                    jumpToSettings();
                }
                break;

            case REQUEST_CODE_SMS:
                if (PermissionUtil.checkGrant(grantResults)) {
                    Log.d("ning", "收发短信权限获取成功");
                } else {
                    ToastUtil.show(this, "获取收发短信权限失败！");
                    jumpToSettings();
                }
                break;
        }
    }

    // 跳转到应用设置界面
    private void jumpToSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
```

* Hungry模式

```java
public class PermissionHungryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS
    };

    private static final int REQUEST_CODE_ALL = 1;
    private static final int REQUEST_CODE_CONTACTS = 2;
    private static final int REQUEST_CODE_SMS = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_lazy);
        findViewById(R.id.btn_contact).setOnClickListener(this);
        findViewById(R.id.btn_sms).setOnClickListener(this);
        PermissionUtil.checkPermission(this, PERMISSIONS, REQUEST_CODE_ALL);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_contact:
                PermissionUtil.checkPermission(this, new String[]{PERMISSIONS[0], PERMISSIONS[1]}, REQUEST_CODE_CONTACTS);
                break;

            case R.id.btn_sms:
                PermissionUtil.checkPermission(this, new String[]{PERMISSIONS[2], PERMISSIONS[3]}, REQUEST_CODE_SMS);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ALL:
                if (PermissionUtil.checkGrant(grantResults)) {
                    Log.d("ning", "所有权限获取成功");
                } else {
                    // 部分权限获取失败
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            // 判断是什么权限没有获取成功
                            switch (permissions[i]) {
                                case Manifest.permission.READ_CONTACTS:
                                case Manifest.permission.WRITE_CONTACTS:
                                    ToastUtil.show(this, "获取通讯录读写权限失败！");
                                    jumpToSettings();
                                    return;

                                case Manifest.permission.SEND_SMS:
                                case Manifest.permission.READ_SMS:
                                    ToastUtil.show(this, "获取收发短信权限失败！");
                                    jumpToSettings();
                                    return;
                            }
                        }
                    }
                }
                break;

            case REQUEST_CODE_CONTACTS:
                if (PermissionUtil.checkGrant(grantResults)) {
                    Log.d("ning", "通讯录权限获取成功");
                } else {
                    ToastUtil.show(this, "获取通讯录读写权限失败！");
                    jumpToSettings();
                }
                break;

            case REQUEST_CODE_SMS:
                if (PermissionUtil.checkGrant(grantResults)) {
                    Log.d("ning", "收发短信权限获取成功");
                } else {
                    ToastUtil.show(this, "获取收发短信权限失败！");
                    jumpToSettings();
                }
                break;
        }
    }

    // 跳转到应用设置界面
    private void jumpToSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
```

Android 10默认开启沙箱模式，不允许直接使用公共空间的文件路径，此时要修改AndroidManifest.xml，给application节点添加如下的requestLegacyExternalStorage属性`android:requestLegacyExternalStorage="true"`从Android 11开始，为了让应用升级时也能正常访问公共空间，还得修改AndroidManifest.xml，给application节点添加如下的preserveLegacyExternalStorage属性，表示暂时关闭沙箱模式：`android:preserveLegacyExternalStorage="true"`.

![权限](D:\Program Files\Android学习\img_MD\权限.jpg)

#### 2.2 利用ContentResolver读写联系人

#### 2.3 利用ContentObserver监听短信

### 三、在应用之间共享文件

#### 3.1 使用相册图片发送彩信

#### 3.2 借助FileProvider发送彩信

#### 3.3 借助FileProvider安装应用

# Fragment (2022/11/21)

* Fragment : 片段，碎片。是一部分内容构成的片段，是页面的一部分。
* 为了让屏幕展示更多内容，以及对这些内容统一管理，引入了Fragment。
* 一个Activity可以有很多个Fragment，各个Fragment之间可以传递数据，相互切换。一个Fragment从开始到结束的生命周期流程：

onAttach->oncreate->==onCreateView==->==onViewCreated==->onActivityCreated->onStart->onResume->onpause    ->onStop->onDestroyView->onDestroy->onDetach



