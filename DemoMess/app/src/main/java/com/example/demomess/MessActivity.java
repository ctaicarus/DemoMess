package com.example.demomess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessActivity extends AppCompatActivity {
    private DatabaseReference mData;
    EditText editText;
    Button btnsend;
    ListView lv;
    ArrayList<String>mess;
    String id; // ten dang nhap
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess);
        mData = FirebaseDatabase.getInstance().getReference(); // not goc
        Intent intent = getIntent();
        id = intent.getStringExtra("key"); // nhan ten
        anhxa();
        addMess();
        nhandulieu();
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = id + " : " + editText.getText().toString().trim();
                mData.child("status").setValue(s); // cap nhap du lieu len firebase
                editText.setText("");
            }
        });
    }
    private void addMess(){
        mess = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter(MessActivity.this , android.R.layout.simple_expandable_list_item_1 , mess);
        lv.setAdapter(adapter);
    }
    private void anhxa(){
        lv = findViewById(R.id.listview);
        editText = findViewById(R.id.editmess);
        btnsend = findViewById(R.id.btnsend);
    }
    private void nhandulieu(){ // nhận tự nút Họ Tên
        mData.child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // dữ liệu có sự thay đổi trên firebase , lấy dữ liệu về ngay lập tức
                String newmess = dataSnapshot.getValue().toString();
                mess.add(newmess);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
