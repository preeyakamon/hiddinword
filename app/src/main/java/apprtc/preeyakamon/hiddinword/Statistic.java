package apprtc.preeyakamon.hiddinword;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistic extends ActionBarActivity {

    private ListView mListview;
    private EditText etSearch;
    private List<Map<String, String>> originalData;
    List<JSONObject> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        getStatisticList();

        // Binding Widget.
        etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    getStatisticList();
                } else {
                    List<Map<String, String>> mapList = new ArrayList<>();
                    try {
                        for (int j = 0; j < data.size(); j++) {
                            JSONObject item = data.get(j);
                            String name = item.getString("_id");
                            String value = item.getString("dateTime");
                            if (value.indexOf(String.valueOf(charSequence)) > -1 || name.indexOf(String.valueOf(charSequence)) > -1) {
                                Map<String, String> map = new HashMap<>();
                                map.put("title", item.getString("_id") + " แต้มที่ได้ " + item.getString("score"));
                                map.put("date", "เมื่อวันที่ " + item.getString("dateTime"));
                                mapList.add(map);
                            }
                        }
                        setupSearchListView(mapList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void getStatisticList() {
        MyOpenHelper db = new MyOpenHelper(Statistic.this);
        data = db.getStatisticList(Statistic.this);
        Log.d("StatisticLog", "getStatList: " + data.size());
        setupListView(data);
    }

    public void setupListView(List<JSONObject> list) {
        mListview = (ListView) findViewById(R.id.listview);
        originalData = new ArrayList<>();
        try {
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> item = new HashMap<>();
                item.put("title", list.get(i).getString("_id") + " แต้มที่ได้ " + list.get(i).getString("score"));
                item.put("date", "เมื่อวันที่ " + list.get(i).getString("dateTime"));
                originalData.add(item);
            }
            SimpleAdapter adapter = new SimpleAdapter(
                    Statistic.this,
                    originalData,
                    android.R.layout.simple_list_item_2,
                    new String[]{"title", "date"},
                    new int[]{android.R.id.text1, android.R.id.text2}
            );
            if (mListview != null) mListview.setAdapter(adapter);
        } catch (Exception e) {

        }
    }

    public void setupSearchListView(List<Map<String, String>> list) {
        SimpleAdapter adapter = new SimpleAdapter(
                Statistic.this,
                list,
                android.R.layout.simple_list_item_2,
                new String[]{"title", "date"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        if (mListview != null) mListview.setAdapter(adapter);
    }
}
