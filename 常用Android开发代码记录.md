**Log.d(String tag, String msg)**   //打印日志

- 第一个参数**tag**：**打印信息的标签**（标志）（如果设置该参数为TAG,在查看logcat时，可以通过搜索栏来搜索标签为TAG的打印信息）
- 第二个参数**msg**：**表示需要打印出来的信息**

**format** //格式化字符串

```java
String desc = String.format("收到请求消息：\n请求时间为%s\n请求内容为%s", request_time, request_content);
```

**findViewById**

```java
RadioGroup rb_login = findViewById(R.id.rg_login);
```

**setOnCheckedChangeListener**

```java
rb_login.setOnCheckedChangeListener(this);
```

**addTextChangedListener**

```java
et_phone.addTextChangedListener(new HideTextWatcher(et_phone, 11));
```

**registerForActivityResult**

1. 它可以取代 `startActivityForResult`方法，获取另一个Activity的返回结果。
2. 它可以取代 `requestPermissions`方法，请求运行时权限。

```java
register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
    @Override
    public void onActivityResult(ActivityResult result) {
        Intent intent = result.getData();
        if (intent != null && result.getResultCode() == Activity.RESULT_OK){
            myPassword = intent.getStringExtra("new_password");
        }

    }
});
```

**Intent**

```java
Intent intent = new Intent(this,LoginForgetActivity.class);
intent.putExtra("phone", phone);
register.launch(intent);
```

**AlertDialog**//提示框

```java
AlertDialog.Builder builder =new AlertDialog.Builder(this);
builder.setTitle("验证码提示");
builder.setMessage("手机号"+ phone +",本次验证码是"+ mverifyCode+",请记住并正确输入验证码");
builder.setPositiveButton("OK",null);
AlertDialog dialog = builder.create();
dialog.show();
```

**Toast**//弹窗

```java
Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
```

**HideTextWatcher**//文本监听接口

```java
private class HideTextWatcher implements TextWatcher {
    private EditText mView;
    private int mMaxLength;

    public HideTextWatcher(EditText v, int maxLength) {
        this.mView = v;
        this.mMaxLength = maxLength;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        if (editable.toString().length() == mMaxLength){

            ViewUtil.hideOneInputMethod(LoginMainActivity.this, mView);
        }

    }
}
```

**关闭软键盘**

```java
public class ViewUtil {

    public static void hideOneInputMethod(Activity act, View v) {
        // 从系统服务中获取输入法管理器
        InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 关闭屏幕上的输入法软键盘
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
```

```java
public void afterTextChanged(Editable editable) {

    if (editable.toString().length() == mMaxLength){

        ViewUtil.hideOneInputMethod(LoginMainActivity.this, mView);
    }
```

```xml
&#160;//空格
```

```
currentTimeMillis()//时间戳
```

```
BufferedWriter
BufferedReader//缓冲流
```
