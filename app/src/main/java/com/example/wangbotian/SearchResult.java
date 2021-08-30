package com.example.wangbotian;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hjq.xtoast.XToast;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchResult extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    ListView listView;
    Button backToSearch;
    TextView textView;
    String course;
    String searchKey;
    ArrayList<EntityItem> items_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        Intent intent = getIntent();
        int searchNum = Integer.parseInt(intent.getStringExtra("search_num"));
        String[] labels = intent.getStringArrayExtra("search_labels");
        String[] categories = intent.getStringArrayExtra("search_categories");
        course = intent.getStringExtra("search_course");
        searchKey = intent.getStringExtra("search_key");
        listView = findViewById(R.id.list_view);
        backToSearch = findViewById(R.id.textButton);
        backToSearch.setOnClickListener(this);
        textView = findViewById(R.id.txtnum);
        textView.setText("共 " + searchNum + " 条结果");

        EntityItem[] items = new EntityItem[searchNum];
        for(int i = 0; i < searchNum; i++) {
            items[i] = new EntityItem(labels[i], categories[i]);
        }
        items_list = new ArrayList<EntityItem>(Arrays.asList(items));
        EntityListAdapter adapter = new EntityListAdapter(this, items_list);
        this.listView.setDivider(null);
        this.listView.setAdapter(adapter);
        this.listView.setClickable(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textButton:
                Intent intent = new Intent();
                intent.setClass(SearchResult.this, SearchActivity.class);
                intent.putExtra("search_key", searchKey);
                intent.putExtra("search_course", course);
                this.startActivity(intent);
                this.finish();
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int position, long id){
        String result_label = items_list.get(position).getLabel();
        Intent intent = new Intent();
        intent.setClass(SearchResult.this, EntityActivity.class);
        intent.putExtra("label", result_label);
        intent.putExtra("course", course);
        intent.putExtra("search_key", searchKey);
        this.startActivity(intent);
        this.finish();
        // 返回的时候也要传回来searchKey来恢复本页面的结果
    }
}
