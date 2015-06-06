package com.example.administrator.soldierbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class soldierbook extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener {

    private Button btn_new,btn_search;
    private EditText et_search;
    private ListView listView;
    private ArrayList<soldier> mProducts;
    private Intent intent;
    private Bundle bundle;
    private long long_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findviews();
        newlist();

    }

    private void findviews(){
        btn_new = (Button)findViewById(R.id.btn_new);
        btn_search = (Button)findViewById(R.id.btn_search);
        et_search = (EditText)findViewById(R.id.et_search);
        listView = (ListView)findViewById(R.id.listView);
        btn_new.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        listView.setOnItemClickListener(this);

        //長按監聽
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DBhelper dbhelper = new DBhelper(soldierbook.this);
                int lid = dbhelper.getSoldier(position).getID();    //讀取當下位置的id
                String time = dbhelper.getSoldier(position).getTime();
                long_id = lid;
                DelDialog(time);
                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_soldierbook, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.btn_new:
                jumpnew();
               break;
           case R.id.btn_search:
               search(et_search.getText().toString());
               break;
       }
    }
    public void jumpnew(){      //跳轉新增介面
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.setClass(getApplication(),thingbook.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,0);
    }

    public void search(String stime){               //搜尋
        DBhelper dbhelper = new DBhelper(this);

        //讀取出搜尋到的全部資料
        String thing = dbhelper.searchtime(stime).getThing();
        String time = dbhelper.searchtime(stime).getTime();
        int ID = dbhelper.searchtime(stime).getID();

        et_search.setText("");
        //用視窗顯示出搜尋資料
        searchDialog(thing,time);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {

            Bundle bundle = data.getExtras();

        }
    }

    //list按鍵監聽
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        DBhelper dbhelper = new DBhelper(this);
        String thing = dbhelper.getSoldier(position).getThing();
        String time = dbhelper.getSoldier(position).getTime();
        int ID = dbhelper.getSoldier(position).getID();   //取出當下位置id

        Dialog(thing,time,ID);
    }

    //跳出顯示訊息視窗
    private void Dialog(final String thing,final String time,final int ID){


        AlertDialog.Builder buider = new AlertDialog.Builder(this);
        buider.setTitle(time);
        buider.setMessage(thing);
        //編輯鍵(跳轉thingbook)
        DialogInterface.OnClickListener ss = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                DBhelper dbhelper = new DBhelper(soldierbook.this);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("ID",ID);
                bundle.putString("thing", thing);
                bundle.putString("time",time);
                intent.setClass(getApplication(),thingbook.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,0);
            }
        };
        DialogInterface.OnClickListener aa = new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };
        buider.setNegativeButton("編輯", ss);
        buider.setPositiveButton("確定",aa);
        buider.show();
    }
    //刪除視窗
    private void DelDialog(String time) {


        AlertDialog.Builder delbuider = new AlertDialog.Builder(this);
//        String yy = String.valueOf(long_id);
        delbuider.setMessage("是否刪除"+time);
        DialogInterface.OnClickListener dd = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                DBhelper dbhelper = new DBhelper(soldierbook.this);
                dbhelper.delProduct(long_id);
                newlist();
            }
        };
        delbuider.setNeutralButton("確定",dd);
        delbuider.show();
    }
    private void searchDialog(String thing,String time) {


        AlertDialog.Builder searchbuider = new AlertDialog.Builder(this);
//        String yy = String.valueOf(long_id);
        searchbuider.setTitle(time);
        searchbuider.setMessage(thing);
        DialogInterface.OnClickListener dd = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        };
        searchbuider.setNeutralButton("確定",dd);
        searchbuider.show();
    }
    //更新list
    private void newlist(){
        DBhelper dbhelper = new DBhelper(this);
        mProducts = dbhelper.getAllProduct();
        ListAdapter adapter = new ListAdapter(this, mProducts);
        listView.setAdapter(adapter);
    }
}
