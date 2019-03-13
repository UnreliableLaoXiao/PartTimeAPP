package com.schoolpartime.schoolpartime.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.google.android.material.textfield.TextInputLayout;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.schoolpartime.schoolpartime.R;

public class ClearEditText2 extends LinearLayout {

    private TextInputLayout textInputLayout;
    private ClearEditText clearEditText;
    private boolean showerror;
    private int inputtype;
    private String hint;

    public ClearEditText2(Context context) {
        super(context);
        initView(context,null);
    }

    public ClearEditText2(Context context,  AttributeSet attrs) {
        super(context, attrs);

        initView(context,attrs);
    }

    public ClearEditText2(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ClearEditText2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context,attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        if(attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClearEditText2);
            showerror = typedArray.getBoolean(R.styleable.ClearEditText2_showerror,false);
            inputtype = typedArray.getInteger(R.styleable.ClearEditText2_inputtype,1);
            hint = typedArray.getString(R.styleable.ClearEditText2_hint);
        }
        LayoutInflater.from(context).inflate(R.layout.weight_clearedittext,this,true);
        textInputLayout = findViewById(R.id.tll);
        clearEditText = findViewById(R.id.clt);
        clearEditText.setHint(hint);
        switch (inputtype) {
            case 1:
                clearEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case 2:
                clearEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            default:
                break;
        }
    }

    public void addTextChangedListener(TextWatcher watcher){
        clearEditText.addTextChangedListener(watcher);
    }

    public String getText(){
        return clearEditText.getText().toString();
    }

    public void setEnabled(boolean enabled){
        clearEditText.setEnabled(enabled);
    }




}
