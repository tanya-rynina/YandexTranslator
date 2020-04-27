package com.example.yandextranslater;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TransCallBack, LoadLangCallBack {

    private Spinner srcLang;
    private Spinner transLang;
    private EditText srcText;
    private TextView transText;
    private Button btnTrans;
    private TextView tvError;
    private TextView tvSrcLangMessage;
    private TextView tvTransLangMessage;
    private LangsData langsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        srcLang = findViewById(R.id.spSrcLang);
        transLang = findViewById(R.id.spTransLang);
        srcText = findViewById(R.id.etSrcText);
        transText = findViewById(R.id.tvTransText);
        btnTrans = findViewById(R.id.btnTranslate);
        tvError = findViewById(R.id.errorText);
        tvSrcLangMessage = findViewById(R.id.tvSrcLangMessage);
        tvTransLangMessage = findViewById(R.id.tvTransLangMessage);


        transText.setMovementMethod(new ScrollingMovementMethod());
        srcLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               validate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        transLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                validate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        LangsTask langsTask = new LangsTask(this);
        langsTask.execute();
    }

    public void translate(View view) {
        Params params = new Params();
        params.setSrcLang(langsData.getReverseMap().get(srcLang.getSelectedItem().toString()));
        params.setText(srcText.getText().toString());
        params.setTransLang(langsData.getReverseMap().get(transLang.getSelectedItem().toString()));

        TranslateTask task = new TranslateTask(this);
        task.execute(params);
    }

    @Override
    public void pretranslate() {
        transText.setText("");
        tvError.setText("");
        btnTrans.setEnabled(false);
        btnTrans.setText("Переводится...");
    }

    @Override
    public void posttranslate(Result result) {
        if (!result.hasError() && result.getCode() == 200) {

            transText.setText(result.getText()[0]);


        } else {
            tvError.setText(result.getException().getMessage());
        }

        btnTrans.setEnabled(true);
        btnTrans.setText("Перевести");
    }

    @Override
    public void preLoadLang() {
        srcLang.setVisibility(View.GONE);
        transLang.setVisibility(View.GONE);
        tvSrcLangMessage.setText("Загружаются языки...");
        tvTransLangMessage.setText("Загружаются языки...");
        tvSrcLangMessage.setTextColor(Color.BLACK);
        tvTransLangMessage.setTextColor(Color.BLACK);
        tvSrcLangMessage.setVisibility(View.VISIBLE);
        tvTransLangMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void postLoadLang(LangsData langsData) {
        this.langsData = langsData;
        if (!langsData.hasError()) {

            ArrayAdapter srcArrayAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    new ArrayList(langsData.getReverseMap().keySet()));
            srcLang.setAdapter(srcArrayAdapter);

            ArrayAdapter transArrayAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    new ArrayList(langsData.getReverseMap().keySet()));
            transLang.setAdapter(transArrayAdapter);


            srcLang.setVisibility(View.VISIBLE);
            transLang.setVisibility(View.VISIBLE);
            tvSrcLangMessage.setVisibility(View.GONE);
            tvTransLangMessage.setVisibility(View.GONE);

        } else {

            srcLang.setVisibility(View.GONE);
            transLang.setVisibility(View.GONE);
            tvSrcLangMessage.setText(langsData.getException().getMessage());
            tvTransLangMessage.setText(langsData.getException().getMessage());
            tvSrcLangMessage.setTextColor(Color.RED);
            tvTransLangMessage.setTextColor(Color.RED);
            tvSrcLangMessage.setVisibility(View.VISIBLE);
            tvTransLangMessage.setVisibility(View.VISIBLE);
        }


    }

    private void validate() {

        String selectedSrcItem = srcLang.getSelectedItem().toString();
        String selectedTransItem = transLang.getSelectedItem().toString();

        String selectedSrcCode = langsData.getReverseMap().get(selectedSrcItem);
        String selectedTransCode = langsData.getReverseMap().get(selectedTransItem);

        String selectedDirs = selectedSrcCode + "-" + selectedTransCode;

        if (langsData.getDirs().contains(selectedDirs)) {
            btnTrans.setEnabled(true);
            tvError.setVisibility(View.GONE);

        } else {
            btnTrans.setEnabled(false);
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("Перевод невозможен");

            if(selectedSrcCode.equals(selectedTransCode)){
                tvError.setText("Выбраны одинаковые языки");
            }


        }


    }
}
