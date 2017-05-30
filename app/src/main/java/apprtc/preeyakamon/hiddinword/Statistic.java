package apprtc.preeyakamon.hiddinword;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistic extends ActionBarActivity {

    private ListView mListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        getStatisticList();
    }

    public void getStatisticList() {
        MyOpenHelper db = new MyOpenHelper(Statistic.this);
        List<JSONObject> data = db.getStatisticList(Statistic.this);
        Log.d("StatisticLog", "getStatList: " + data.size());
        setupListView(data);
    }

    public void setupListView(List<JSONObject> list) {
        mListview = (ListView) findViewById(R.id.listview);
        List<Map<String, String>> data = new ArrayList<>();
        try {
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> item = new HashMap<>();
                item.put("title", "แต้มที่ได้ " + list.get(i).getString("score"));
                item.put("date", list.get(i).getString("dateTime"));
                data.add(item);
            }
            SimpleAdapter adapter = new SimpleAdapter(
                    Statistic.this,
                    data,
                    android.R.layout.simple_list_item_2,
                    new String[]{"title", "date"},
                    new int[]{android.R.id.text1, android.R.id.text2}
                    );
            if (mListview != null) mListview.setAdapter(adapter);
        } catch (Exception e) {

        }
    }
}
