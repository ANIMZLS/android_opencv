# 常用

### 布局管理器

1. RelativeLayout     相对布局管理器
2. LinearLayout        线性布局管理器
3. FrameLayout        帧布局管理器
4. TableLayout          表格布局管理器
5. AbsoluteLayout    绝对布局管理器

#### 一、相对布局管理器

1. gravity
2. ignoreGravity   忽略布局
3. LayoutParams

+ ![image-20221102211652262](C:\Users\zls05\AppData\Roaming\Typora\typora-user-images\image-20221102211652262.png)
+ layout_above/below/toLeftof/toRightof         相对于其他组件的位置
+ layout_alignParentBootom/Left/Right/Top    组件与布局管理器哪边对齐
+ layout_alignBottom/Left/Right/Top                 组件与其他组件哪边对齐
+ layout_centerHorizontal/InParent/Vertical     组件位于布局管理器的位置

4. layout_marginBottom/Left/Right/Top             外边距

#### 二、线性布局管理器

1. orientation="vertical"/"horizontal"(垂直分布/水平分布)
2. gravity    下级相对于当前位置   layout_gravity    当前相对于上级位置
3. layout_weight      组件所占剩余空间权重

#### 三、帧布局管理器<FrameLayout>

1. foreground            设置前景图像 
2. foregroundGravity          设置前景图像位置

#### 四、表格布局管理器

1. TableRow
2. collapseColumns         隐藏列
3. stretchColumns           拉伸列
4. shrinkColumns            收缩列

#### 五、网格布局管理器<GridLayout>

1. columnCount            网格最大列数
2. orientation                排列方式
3. rowCount                  最大行数
4. LayoutParams

+ layout_column/columnSpan/columnWeight/gravity/row/rowSpan/rowWeight

#### 六、布局管理器的嵌套

### 基本UI组件

1. 文本类组件
2. 按钮类组件
3. 时间日期类组件

#### 一、文本类

1. 文本框<TextView>

```java
textColor
singleLine
textSize
```

1. 编辑框<EditText>

```java
hint
inputType
drawableLeft/Top/Bottom/End/Right/Start
drawablePadding    //内边距
line 
scaleType
```

#### 二、按钮类

1. 普通按钮 Button
2. 单击事件 setOnclickListener 单击监听器/onClick属性
3. 图片按钮 ImageButton

​		background="#0000" 透明背景

```
getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);//设置全屏显示
```

4. 单选按钮 <RadioButton  RadioGroup>

   setOnCheckedChangeListener()   转换监听器

   checked 默认状态

   getChildAt() 返回指定位置 getChildCount()  子元素个数

   ```
   Toast.makeText(MainActivity.this, "回答正确",Toast.LENGTH_SHORT).show();
   //消息弹窗
   AlertDialog.Builder 对话框
   ```

5. 复选框组件<CheckBox>

6. 日期选择器<DatePicker>

7. 时间选择器<TimePicker>

8. 计时器<Chronmeter>

​		format 时间格式

​		setBase() setFormat() start() stop() setOnChronmeterTickListener()

### 高级UI组件

一、进度条

1. 圆形进度条    ProgressBar
2. 条形进度条    style="?android:attr/progressBarStyleHorizontal"

​							   max=" "    progress=""

二、拖动条<SeckBar>

三、星级评分条<RatingBar>

四、图像视图<ImageView>

五、图像切换器<ImageSwitcher>

六、网格试图<GridView>

七、下拉列表框<Spinner>

八、列表视图<ListView>

九、滚动视图<ScrollView><HorizontalScrollView>

十、选项卡<TabHost>

# 中级控件

#### 1.1 图形Drawable

1. Drawable 类型表达了各种各样的图形，包括图片、色块、画板、背景等。
2. 包含图片在内的图形文件放在res目录的各个drawable目录下，其中drawable目录一般
   保存描述性的XML文件，而图片文件一般放在具体分辨率的drawable目录下。

#### 1.2 形状图形Shape

1. rectangle：矩形。默认值
2. oval：椭圆。此时corners节点会失效
3. line：直线。此时必须设置stroke节点，不然会报错
4. ring：圆环

* 除了根节点shape标签，形状图形还拥有下列规格标签：

  1. size（尺寸），它描述了形状图形的宽高尺寸。

     * height：像素类型，图形高度。
     * width：像素类型，图形宽度。

  2. stroke（描边），它描述了形状图形的描边规格。

     * color：颜色类型，描边的颜色。
     * dashGap：像素类型，每段虚线之间的间隔。
     * dashWidth：像素类型，每段虚线的宽度。若dashGap和dashWidth有一个值为0，则描边为实线。
     * width：像素类型，描边的厚度。

  3. corners（圆角），它描述了形状图形的圆角大小

     * bottomLeftRadius：像素类型，左下圆角的半径。
     * bottomRightRadius：像素类型，右下圆角的半径。
     * topLeftRadius：像素类型，左上圆角的半径。
     * topRightRadius：像素类型，右上圆角的半径。
     * radius：像素类型，4个圆角的半径

  4. solid（填充），它描述了形状图形的填充色彩。

  5. padding（间隔），它描述了形状图形与周围边界的间隔。

  6. gradient（渐变），它描述了形状图形的颜色渐变。

     * angle：整型，渐变的起始角度。为0时表示时钟的9点位置，值增大表示往逆时针方向旋转。

       |渐变类型 |说明|
       |----------------|:---------------|
       |linear| 线性渐变，默认值|
       |radial| 放射渐变，起始颜色就是圆心颜色|
       |sweep |滚动渐变，即一个线段以某个端点为圆心做360度旋转|
       
     * type：字符串类型，渐变类型。渐变类型的取值说明见表5-2
     
     * centerX：浮点型，圆心的X坐标。当android:type="linear"时不可用。
     
     * centerY：浮点型，圆心的Y坐标。当android:type="linear"时不可用
     
     * gradientRadius：整型，渐变的半径。当android:type="radial"时需要设置该属性
     
     * enterColor：颜色类型，渐变的中间颜色。
     
     * startColor：颜色类型，渐变的起始颜色。
     
     * endColor：颜色类型，渐变的终止颜色。
     
     * useLevel：布尔类型，设置为true为无渐变色、false为有渐变色。

#### 1.3 九宫格图片(.9.png)

1. 点九图片拉伸图形时，只拉伸内部区域，不拉伸边缘线条。

#### 1.4 状态列表图形

1. 右击drawable目录，并依次选择右键菜单的New→Drawable resource file，在弹窗中输入文件名称再单击OK按钮，即可自动生成一个XML描述文件。selector (选择器)

```xml
<selector xmlns:android="http://schemas.android.com/apk/res/android">
	<item android:state_pressed="true" android:drawable="@drawable/button_pressed" />
	<item android:drawable="@drawable/button_normal" />
</selector>
```

2. 状态列表图形不仅用于按钮控件，还可用于其他拥有多种状态的控件，这取决于开发者在XML文件中指定了哪种状态类型。

|状态类型的属性名称|说明|适用的控件|
|------------|-------------|----------------|
|state_pressed |是否按下|按钮Button|
|state_checked |是否勾选|复选框CheckBox、单选按钮RadioButton|
|state_focused |是否获取|焦点文本编辑框EditText|
|state_selected |是否选中|各控件通用|

#### 2.1 复选框CheckBox

​		*CompoundButton类是抽象的复合按钮，因为是抽象类，所以它不能直接使用。实际开发中用的是CompoundButton的几个派生类，主要有复选框CheckBox、单选按钮RadioButton以及开关按钮Switch，这些派生类均可使用CompoundButton的属性和方法。加之CompoundButton本身继承了Button类，故以上几种按钮同时具备Button的属性和方法*

1. CompoundButton在XML文件中主要使用下面两个属性。
   * checked：指定按钮的勾选状态，true表示勾选，false则表示未勾选。默认为未勾选。
   * button：指定左侧勾选图标的图形资源，如果不指定就使用系统的默认图标。

2. CompoundButton在Java代码中主要使用下列4种方法。
   * setChecked：设置按钮的勾选状态。
   * setButtonDrawable：设置左侧勾选图标的图形资源。
   * setOnCheckedChangeListener：设置勾选状态变化的监听器。
   * isChecked：判断按钮是否勾选。

#### 2.2 开关按钮Switch

1. Switch控件新添加的XML属性说明如下：
   * textOn：设置右侧开启时的文本。
   * textOff：设置左侧关闭时的文本。
   * track：设置开关轨道的背景。
   * thumb：设置开关标识的图标。

默认Switch开关并不美观

2. 但要让Android实现类似iOS的开关按钮，主要思路是借助“状态列表图形”，首先创建一个图形专用的XML文件，给状态列表指定选中与未选中时候的开关图标。

```xml
<selector xmlns:android="http://schemas.android.com/apk/res/android">
	<item android:state_checked="true" android:drawable="@drawable/switch_on"/>
	<item android:drawable="@drawable/switch_off"/>
</selector>
```

3. 然后把CheckBox标签的background属性设置为@drawable/switch_selector，同时将button属性设置为@null

```xml
<CheckBox
        android:id="@+id/ck_status"
        android:layout_width="60dp"
        android:layout_height="30dp"
        android:background="@drawable/switch_selector"
        android:button="@null" />
```

#### 2.3 单选按钮RadioGroup -> RadioButton

1. RadioGroup在Java代码中的3个常用方法。
   * check：选中指定资源编号的单选按钮。
   * getCheckedRadioButtonId：获取已选中单选按钮的资源编号。
   * setOnCheckedChangeListener：设置单选按钮勾选变化的监听器。
2. RadioGroup下面除了RadioButton，还可以挂载其他子控件（如TextView、ImageView等）

#### 3.1 编辑框EditText

1. EditText 的常用属性说明如下：
   * inputType：指定输入的文本类型。若同时使用多种文本类型，则可使用竖线“|”把多种文本类
     型拼接起来。
   * maxLength：指定文本允许输入的最大长度。
   * hint：指定提示文本的内容。
   * textColorHint：指定提示文本的颜色。

   |输入类型|说明|
   |--------|---------|
   |text| 文本|
   |textPassword |文本密码。显示时用圆点“·”代替|
   |number |整型数|
   |numberSigned |带符号的数字。允许在开头带负号“－”|
   |numberDecimal |带小数点的数字|
   |numberPassword| 数字密码。显示时用圆点“·”代替|
   |datetime |时间日期格式。除了数字外，还允许输入横线、斜杆、空格、冒号|
   |date |日期格式。除了数字外，还允许输入横线“-”和斜杆“/”|
   |time |时间格式。除了数字外，还允许输入冒号“:”|

2. 改变编辑框背景为更加美观的圆角矩形

**实际工程中也常用状态列表图形selector这种方式来改变图形背景**

+ 首先编写圆角矩形的形状图形文件shape，它的XML定义文件示例如下：

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android" >
    
	<!-- 指定了形状内部的填充颜色 -->
	<solid android:color="#ffffff" />
	<!-- 指定了形状轮廓的粗细与颜色 -->
	<stroke
		android:width="1dp"
		android:color="#aaaaaa" />
    
	<!-- 指定了形状四个圆角的半径 -->
	<corners android:radius="5dp" />
    
	<!-- 指定了形状四个方向的间距 -->
	<padding
		android:bottom="2dp"
		android:left="2dp"
		android:right="2dp"
		android:top="2dp" />
</shape>
```

上述的shape_edit_normal.xml定义了一个灰色的圆角矩形，可在未输入时展示该形状。正在输入时候的形状要改为蓝色的圆角矩形，其中轮廓线条的色值从aaaaaa（灰色）改成0000ff（蓝色）

+ 接着编写编辑框背景的状态列表图形文件，主要在selector节点下添加两个item，一个item设置了获得焦点时刻（android:state_focused="true"）的图形为@drawable/shape_edit_focus；另一个item设置了图形@drawable/shape_edit_normal但未指定任何状态，表示其他情况都展示该图形。完整的状态列表图形定义示例如下：

```xml
<selector xmlns:android="http://schemas.android.com/apk/res/android">
	<item android:state_focused="true"
          android:drawable="@drawable/shape_edit_focus"/>
	<item android:drawable="@drawable/shape_edit_normal"/>
</selector>
```

#### 3.2 焦点变更监听器

焦点变更监听器来自于接口==View.OnFocusChangeListener==，若想注册该监听器，就要调用编辑框对象的==setOnFocusChangeListener==方法，即可在光标切换之时（获得光标和失去光标）触发焦点变更事件。

#### 3.3 文本变更监听器

**实例：**输入法的软键盘往往会遮住页面下半部分，使得“登录”“确认”“下一步”等按钮看不到了，用户若想点击这些按钮还得再点一次返回键才能关闭软键盘。为了方便用户操作，最好在满足特定条件时自动关闭软键盘，比如手机号码输入满11位后自动关闭软键盘，又如密码输入满6位后自动关闭软键盘，等等。

1. 如何关闭软键盘，在工具包util中写入

```java
public static void hideOneInputMethod(Activity act, View v) {
	// 从系统服务中获取输入法管理器
	InputMethodManager imm = (InputMethodManager)
	act.getSystemService(Context.INPUT_METHOD_SERVICE);
	// 关闭屏幕上的输入法软键盘
	imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    //代码里面的视图对象v，虽然控件类型为View，但它必须是EditText类型才能正常关闭软键盘。
}
```

2. 如何判断已输入的文字达到指定位数

+ 该功能点要求实时监控当前已输入的文本长度，这个监控操作用文本监听器接口TextWatcher，该接口提供了3个监控方法，具体说明如下：
  + beforeTextChanged：在文本改变之前触发。
  + onTextChanged：在文本改变过程中触发。
  + afterTextChanged：在文本改变之后触发

```java
// 定义一个编辑框监听器，在输入文本达到指定长度时自动隐藏输入法
//implements表示对接口的实现
private class HideTextWatcher implements TextWatcher {
	private EditText mView; // 声明一个编辑框对象
	private int mMaxLength; // 声明一个最大长度变量
	public HideTextWatcher(EditText v, int maxLength) {
		super();
		mView = v;
		mMaxLength = maxLength;
	}
	// 在编辑框的输入文本变化前触发
	public void beforeTextChanged(CharSequence s, int start, int count, int
after) {}
	// 在编辑框的输入文本变化时触发
	public void onTextChanged(CharSequence s, int start, int before, int count)
{}
	// 在编辑框的输入文本变化后触发
	public void afterTextChanged(Editable s) {
		String str = s.toString(); // 获得已输入的文本字符串
		// 输入文本达到11位（如手机号码），或者达到6位（如登录密码）时关闭输入法
		if ((str.length() == 11 && mMaxLength == 11)
			|| (str.length() == 6 && mMaxLength == 6)) {
			ViewUtil.hideOneInputMethod(EditHideActivity.this, mView); // 隐藏输入法软键盘
		}
	}
}
```

+ 写好文本监听器代码，还要给手机号码编辑框和密码编辑框分别注册监听器，注册代码示例如下：

```java
// 从布局文件中获取名为et_phone的手机号码编辑框
EditText et_phone = findViewById(R.id.et_phone);
// 从布局文件中获取名为et_password的密码编辑框
EditText et_password = findViewById(R.id.et_password);
// 给手机号码编辑框添加文本变化监听器
et_phone.addTextChangedListener(new HideTextWatcher(et_phone, 11));
// 给密码编辑框添加文本变化监听器
et_password.addTextChangedListener(new HideTextWatcher(et_password, 6));
```

#### 4.1 提醒对话框

1. AlertDialog名为提醒对话框，它是Android中最常用的对话框，可以完成常见的交互操作，例如提示、确认、选择等功能。由于AlertDialog没有公开的构造方法，因此必须借助建造器AlertDialog.Builder才能完成参数设置，AlertDialog.Builder的常用方法说明如下。
   * setIcon：设置对话框的标题图标。
   * setTitle：设置对话框的标题文本。
   * setMessage：设置对话框的内容文本。
   * setPositiveButton：设置肯定按钮的信息，包括按钮文本和点击监听器。
   * setNegativeButton：设置否定按钮的信息，包括按钮文本和点击监听器。
   * setNeutralButton：设置中性按钮的信息，包括按钮文本和点击监听器，该方法比较少用

​		通过AlertDialog.Builder设置完对话框参数，还需调用建造器的create方法才能生成对话框实例。最后调用对话框实例的show方法，在页面上弹出提醒对话框。

```java
// 创建提醒对话框的建造器
AlertDialog.Builder builder = new AlertDialog.Builder(this);//一种设计模式
builder.setTitle("尊敬的用户"); // 设置对话框的标题文本
builder.setMessage("你真的要卸载我吗？"); // 设置对话框的内容文本
// 设置对话框的肯定按钮文本及其点击监听器
builder.setPositiveButton("残忍卸载", new DialogInterface.OnClickListener() {
	@Override
	public void onClick(DialogInterface dialog, int which) {
		tv_alert.setText("虽然依依不舍，但是只能离开了");
	}
});
// 设置对话框的否定按钮文本及其点击监听器
builder.setNegativeButton("我再想想", new DialogInterface.OnClickListener() {
	@Override
	public void onClick(DialogInterface dialog, int which) {
		tv_alert.setText("让我再陪你三百六十五个日夜");
	}
});
AlertDialog alert = builder.create(); // 根据建造器构建提醒对话框对象
alert.show(); // 显示提醒对话框
```

#### 4.2 日期对话框DataPickerDialog

1. 布局属性：calenderViewShown="" ; dataPickerMode="";
2. java代码

```java
// 该页面类实现了接口OnDateSetListener，意味着要重写日期监听器的onDateSet方法
public class DatePickerActivity extends AppCompatActivity implements
		View.OnClickListener, DatePickerDialog.OnDateSetListener {
	private TextView tv_date; // 声明一个文本视图对象
	private DatePicker dp_date; // 声明一个日期选择器对象
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date_picker);
		tv_date = findViewById(R.id.tv_date);
		// 从布局文件中获取名叫dp_date的日期选择器
		dp_date = findViewById(R.id.dp_date);
		findViewById(R.id.btn_date).setOnClickListener(this);//监听器
	}
    @Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_date) {
		// 获取日历的一个实例，里面包含了当前的年月日
		Calendar calendar = Calendar.getInstance();
		// 构建一个日期对话框，该对话框已经集成了日期选择器。
		// DatePickerDialog的第二个构造参数指定了日期监听器
		DatePickerDialog dialog = new DatePickerDialog(this, this,
			calendar.get(Calendar.YEAR), // 年份
			calendar.get(Calendar.MONTH), // 月份
			calendar.get(Calendar.DAY_OF_MONTH)); // 日子
			dialog.show(); // 显示日期对话框
		}
	}
	// 一旦点击日期对话框上的确定按钮，就会触发监听器的onDateSet方法
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int
dayOfMonth) {
		// 获取日期对话框设定的年月份
		String desc = String.format("您选择的日期是%d年%d月%d日",
					year, monthOfYear + 1, dayOfMonth);//月份是0-11
		tv_date.setText(desc);
	}
}
```

#### 4.3 时间对话框TimePickerDialog

1. 布局属性： timePickerMode="";

2. Java代码

```java
// 该页面类实现了接口OnTimeSetListener，意味着要重写时间监听器的onTimeSet方法
public class TimePickerActivity extends AppCompatActivity implements
		View.OnClickListener, TimePickerDialog.OnTimeSetListener {
	private TextView tv_time; // 声明一个文本视图对象
	private TimePicker tp_time; // 声明一个时间选择器对象
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_picker);
		tv_time = findViewById(R.id.tv_time);
		// 从布局文件中获取名叫tp_time的时间选择器
		tp_time = findViewById(R.id.tp_time);
		findViewById(R.id.btn_time).setOnClickListener(this);
	}
    
	@Override
    public void onClick(View v) {
		if (v.getId() == R.id.btn_time) {
		// 获取日历的一个实例，里面包含了当前的时分秒
		Calendar calendar = Calendar.getInstance();
		// 构建一个时间对话框，该对话框已经集成了时间选择器。
		// TimePickerDialog的第二个构造参数指定了时间监听器
		TimePickerDialog dialog = new TimePickerDialog(this, this,
			calendar.get(Calendar.HOUR_OF_DAY), // 小时
			calendar.get(Calendar.MINUTE), // 分钟
			true); // true表示24小时制，false表示12小时制
		dialog.show(); // 显示时间对话框
		}
	}
	// 一旦点击时间对话框上的确定按钮，就会触发监听器的onTimeSet方法
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// 获取时间对话框设定的小时和分钟
		String desc = String.format("您选择的时间是%d时%d分", hourOfDay, minute);
		tv_time.setText(desc);
	}
}
```

