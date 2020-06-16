package com.zzh.netcontrol;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TestProviderActivity extends AppCompatActivity implements View.OnClickListener {

    private Uri uri;
    private ArrayList<Student> mDatas = new ArrayList<>();
    private ContentResolver resolver;
    private RecyclerView proRecycler;
    private ProAdapter adapter;
    ContentValues values = new ContentValues();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_provider);
        /*
         * 对MyApplication项目中创建的student表进行操作
         */
        uri = Uri.parse("content://com.zzh.provider/student");
        resolver = getContentResolver();
        initView();
    }

    private void initView() {
        proRecycler = findViewById(R.id.providerRecycler);
        proRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        findViewById(R.id.insert).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
        findViewById(R.id.update).setOnClickListener(this);
        findViewById(R.id.query).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert:        //插入表的数据
                values.clear();
                values.put("id", mDatas.size() > 0 ? mDatas.get(mDatas.size() - 1).getId() + 1 : 1);
                values.put("name", "Tony");

                //根据URI向ContentProvider中插入数据
                resolver.insert(uri, values);
                Student student = new Student();
                student.setId(values.getAsInteger("id"));
                student.setName(values.getAsString("name"));
                mDatas.add(student);
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.delete:    //根据uri删除provider中的数据
                if (mDatas.size() > 0) {
                    resolver.delete(uri, "id=?", new String[]{String.valueOf(mDatas.get(0).getId())});
                    mDatas.remove(0);
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
            case R.id.update:       //根据uri修改provider中的数据
                values.clear();
                values.put("name", "苍老师");
                resolver.update(uri, values, "id=?", new String[]{String.valueOf(mDatas.get(0).getId())});

                Student student1 = new Student();
                student1.setId(mDatas.get(0).getId());
                student1.setName(values.getAsString("name"));
                mDatas.set(0, student1);
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.query:                //通过ContentResolver向provider中查询数据
                Cursor cursor = resolver.query(uri, new String[]{"id", "name", "gender", "className"}, null, null, null);
                if (cursor != null) {
                    if (adapter == null) {
                        while (cursor.moveToNext()) {
                            Student student2 = new Student();
                            student2.setId(cursor.getInt(cursor.getColumnIndex("id")));
                            student2.setName(cursor.getString(cursor.getColumnIndex("name")));
                            student2.setGender(cursor.getString(cursor.getColumnIndex("gender")));
                            student2.setClassName(cursor.getString(cursor.getColumnIndex("className")));
                            mDatas.add(student2);
                        }

                        adapter = new ProAdapter();
                        proRecycler.setAdapter(adapter);

                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    cursor.close();
                }
                break;
        }
    }


    class ProAdapter extends RecyclerView.Adapter<ProAdapter.ProHolder> {

        @NonNull
        @Override
        public ProHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_data_layout, null, false);
            return new ProHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProHolder holder, int position) {
            Student student = mDatas.get(position);
            holder.contentTv.setText(student.getId() + "  " + student.getName() + "  " + student.getGender() + "  " + student.getClassName());
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class ProHolder extends RecyclerView.ViewHolder {
            TextView contentTv;
            TextView editTv;
            TextView deleteTv;

            ProHolder(@NonNull View itemView) {
                super(itemView);
                contentTv = itemView.findViewById(R.id.contentTv);
                editTv = itemView.findViewById(R.id.editTv);
                deleteTv = itemView.findViewById(R.id.deleteTv);
                editTv.setOnClickListener(v -> update(mDatas, getLayoutPosition()));
                deleteTv.setOnClickListener(v -> delete(mDatas, getLayoutPosition()));
            }

            private void delete(ArrayList<Student> mDatas, int position) {
                resolver.delete(uri, "id=?", new String[]{String.valueOf(mDatas.get(position).getId())});
                mDatas.remove(position);
                notifyItemRemoved(position);
            }

            private void update(final ArrayList<Student> mDatas, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TestProviderActivity.this);
                final AlertDialog alertDialog = builder.create();
                View view = View.inflate(getApplicationContext(), R.layout.dialog_layout, null);
                alertDialog.setView(view);
                alertDialog.show();
//                alertDialog.getWindow().setLayout();      //设置窗口的宽高
                final EditText enterName = view.findViewById(R.id.enterName);
                view.findViewById(R.id.sureTv).setOnClickListener(v -> {       //插入数据
                    String name = enterName.getText().toString();
                    ContentValues values = new ContentValues();
                    if (TextUtils.isEmpty(name)) {
                        values.put("name", "defaultName");
                    } else {
                        values.put("name", name);
                    }
                    resolver.update(uri, values, "id=?", new String[]{String.valueOf(mDatas.get(position).getId())});

                    Student teacher = mDatas.get(position);
                    teacher.setName(values.getAsString("name"));
                    mDatas.set(position, teacher);
                    notifyItemChanged(position);
                    alertDialog.cancel();
                });
                view.findViewById(R.id.cancelTv).setOnClickListener(v -> alertDialog.cancel());
            }
        }
    }
}
