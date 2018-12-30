package com.cczu.lcy.cloudplatform;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //申明控件
        final EditText num = findViewById(R.id.edit_number);
        final EditText pwd = findViewById(R.id.edit_passwd);
        Button btn_login= findViewById(R.id.btn_login);





        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(num.getText().toString().equals("123456")&&pwd.getText().toString().equals("123456"))
                {
                    Intent show = new Intent(Login.this,Show.class);
                    startActivity(show);

                }
                else
                    Toast.makeText(Login.this,"学号或者密码错误！",Toast.LENGTH_LONG).show();


            }
        });

    }




}
