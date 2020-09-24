package com.sii.studentmanager.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sii.studentmanager.R;
import com.sii.studentmanager.adapter.SpinnerAdapterStudent;
import com.sii.studentmanager.adapter.StudentApdapter;
import com.sii.studentmanager.model.Student;
import com.sii.studentmanager.sqlite.Controller;
import com.sii.studentmanager.sqlite.Students;

import java.util.List;


public class Main3Activity extends AppCompatActivity {
    private EditText edtname,edtBirthday;
    private ListView lvList;
    protected AlertDialog dialog;
    String lop;
    List<Student> students;
    List<Class> classes;
    Controller controller;
    Students student;
    SpinnerAdapterStudent spinnerAdapterStudent;
    StudentApdapter studentApdapter;
    ListView lvStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_tool2);
        edtname = findViewById(R.id.edtName);
        edtBirthday = findViewById(R.id.edtBirthday);
        lvList=findViewById(R.id.lvStudent);
        student = new Students(Main3Activity.this);

    }

    public void addStudent(View view) {
        Student product = new Student();

        product.setName(edtname.getText().toString());
        product.setBirthday((edtBirthday.getText().toString()));


        long result = student.insertData(product);
        if (result > 0) {
            Toast.makeText(this, "them thanh cong", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "co loi xay ra", Toast.LENGTH_SHORT).show();
        }
        List<Student> productList= student.getData();

        //String[] data= new String[productList.size()];

        Log.e("size", productList.size()+"");

        StudentApdapter productAdapter = new StudentApdapter(Main3Activity.this, productList);
        lvList.setAdapter(productAdapter);
    }
}