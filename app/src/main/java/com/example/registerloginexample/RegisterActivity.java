package com.example.registerloginexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_id, et_pw, et_name, et_age;
    private Button btn_register;

    //액티비티 시작시 처음으로 실행되는 생명주기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //아이디 값 찾아주기
        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);

        //회원가입 버튼 클릭 시 수행
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText에 현재 입력되어있는 값을 가져온다
                String userID = et_id.getText().toString();
                String userPass= et_pw.getText().toString();
                String userName = et_name.getText().toString();
                int userAge = Integer.parseInt(et_age.getText().toString());

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Json 오브젝트(운반책) 활용해서 실제적인 회원가입 요청 실행할 것임
                        //중괄호에 갇혀있는 스트링 형태들로 담아서 서버전송할 떄 활용함.
                        try {
                            JSONObject jsonObject = new JSONObject(response);   //회원가입 요청 결과값을 받는 것
                            boolean success = jsonObject.getBoolean("success"); //register.php에서 response를 success로 지정해준 것을 가지고 오는 것임

                            //회원등록에 성공한 경우
                            if(success) {
                                Toast.makeText(getApplicationContext(), "회원등록에 성공하였습니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            //회원등록에 실패한 경우
                            else{
                                Toast.makeText(getApplicationContext(), "회원등록에 실패하였습니다", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };


                //RegisterRequset.java에서 만들어준 생성자를 넣어주면 됨
                //그러면 서버로 Volley를 이용해서 요청을 함
                RegisterRequest registerRequest = new RegisterRequest(userID, userPass, userName, userAge, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);


            }
        });
    }
}
