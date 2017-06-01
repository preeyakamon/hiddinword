package apprtc.preeyakamon.hiddinword;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserList extends ActionBarActivity {

    List<JSONObject> data;
    private ListView mListview;
    private EditText etSearch;
    private List<Map<String, String>> originalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        getUserList();

    }

    public void getUserList() {
        MyOpenHelper db = new MyOpenHelper(UserList.this);
        data = db.getUserList(UserList.this);
        setupListView(data);
    }

    public void setupListView(List<JSONObject> list) {
        mListview = (ListView) findViewById(R.id.listview);
        originalData = new ArrayList<>();
        try {
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> item = new HashMap<>();
                item.put("title", "ID " + list.get(i).getString("_id") + " : " + list.get(i).getString("Name"));
                item.put("detail", "username: " + list.get(i).getString("User"));
                originalData.add(item);
            }
            SimpleAdapter adapter = new SimpleAdapter(
                    UserList.this,
                    originalData,
                    android.R.layout.simple_list_item_2,
                    new String[]{"title", "detail"},
                    new int[]{android.R.id.text1, android.R.id.text2}
            );
            if (mListview != null) {
                mListview.setAdapter(adapter);
                mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        confirmDialog(i);
                    }
                });
            }
        } catch (Exception e) {

        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void confirmDialog(final int i) {
        final JSONObject item = data.get(i);
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.custom_remove_confirm, null);
        new AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("remove", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            final EditText etPass = (EditText) view.findViewById(R.id.etPass);
                            final String inputPass = etPass.getText().toString();
                            final String realPass = item.getString("Password");
                            if (inputPass.equalsIgnoreCase(realPass)) {
                                MyOpenHelper db = new MyOpenHelper(UserList.this);
                                boolean resp = db.removeUser(item.getString("_id"), item.getString("User"));
                                Log.d("StatisticLog", "remove: " + resp);
                                if (resp) {
                                    Toast.makeText(UserList.this, "ลบบัญชีผู้ใช้เรียบร้อย", Toast.LENGTH_LONG).show();
                                    getUserList();
                                } else {
                                    Toast.makeText(UserList.this, "เกิดข้อผิดพลาด ไม่สามารถลบข้อมูลได้", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(UserList.this, "รหัสผิดพลาด", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();

    }
}
