package com.sii.studentmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.sii.studentmanager.R;
import com.sii.studentmanager.adapter.ClassAdpater;
import com.sii.studentmanager.sqlite.Controller;

import java.util.List;


public class Main2Activity extends AppCompatActivity {
    Controller controller = new Controller(this);
    List<Class> classes;
    ListView lv;
    protected AlertDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_class2);
        controller = new Controller(Main2Activity.this);
        classes = controller.getData();
        lv = findViewById(R.id.lv);
        final ClassAdpater classAdpater = new ClassAdpater(Main2Activity.this, classes);
        lv.setAdapter(classAdpater);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                final Class aClass = classes.get(position);

                final AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                View v = LayoutInflater.from(Main2Activity.this).inflate(R.layout.showdialog, parent, false);
                builder.setView(v);

                final Button btnDelete, btnEdit;
                btnDelete = v.findViewById(R.id.btnDelete);
                btnEdit = v.findViewById(R.id.btnEdit);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            long result = controller.deleteData(aClass.getId());
                            classes.remove(position);
                            lv.setAdapter(classAdpater);
                            Toast.makeText(Main2Activity.this, "Đã xóa thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(Main2Activity.this, "Lỗi " + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        View editView = LayoutInflater.from(Main2Activity.this).inflate(R.layout.editclass, parent, false);
                        builder.setView(editView);

                        final EditText tvId, tvName;
                        Button btnClear, btnSave;
                        tvId = editView.findViewById(R.id.edtID);
                        tvName = editView.findViewById(R.id.edtName);
                        btnSave = editView.findViewById(R.id.btnSave);
                        btnClear = editView.findViewById(R.id.btnClear);
                        tvId.setText(aClass.getId());
                        tvName.setText(aClass.getName());

                        btnClear.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tvId.setText("");
                                tvName.setText("");
                            }
                        });

                        btnSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    if (!tvId.getText().toString().equals("") && !tvName.getText().toString().equals("")) {
                                        long result = controller.updateData(aClass.getId(), tvId.getText().toString(), tvName.getText().toString());
                                        classes.set(position, new Class(tvId.getText().toString(), tvName.getText().toString()));
                                        lv.setAdapter(classAdpater);
                                        Toast.makeText(Main2Activity.this, "Đã cập nhật thành công", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(Main2Activity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(Main2Activity.this, "Lỗi " + e, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        builder.create();
                        dialog = builder.show();
                    }
                });

                builder.create();
                dialog = builder.show();
                return false;
            }
        });
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void closeDialog(View view) {
        dialog.dismiss();
    }

}