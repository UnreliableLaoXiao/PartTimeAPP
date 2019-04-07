package com.schoolpartime.schoolpartime.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.adapter.MyLoveTypeAdapter;
import com.schoolpartime.schoolpartime.entity.UserLikeWorkType;
import com.schoolpartime.schoolpartime.entity.WorkType;
import java.util.ArrayList;
import java.util.List;


public class MyLoveTypeActivity extends Activity {

    private List<String> listStr = new ArrayList<>();
    private int userid;
    private String result;
    private View bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_love_type);
        init();
    }


    private List<UserLikeWorkType> typeList = new ArrayList<>();



    private List<WorkType> list_worktype;
    private List<Integer> integerList = new ArrayList<>();


    public boolean getIsExit(int wtid){
        boolean flag = false;
//        typeList = data.getData();
//        for (int i=0;i<typeList.size();i++){
//            if(typeList.get(i).getWtId() == wtid){
//                flag = true;
//                break;
//            }
//        }
        return flag;
    }

    private void initList() {
//        ListView listView = findViewById(R.id.list_mylove);
//
//        final MyLoveTypeAdapter adapter = new MyLoveTypeAdapter(this,list);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                MyLoveTypeAdapter.ViewCache viewCache = (MyLoveTypeAdapter.ViewCache) view.getTag();
//                if(listStr.size() <=2){
//                    if(viewCache.cb.isChecked()){//被选中状态
//                        viewCache.cb.setChecked(false);
//                        listStr.remove(list.get(position).get("name").toString());
//                        integerList.remove(getPosition(position));
//                        Log.e("int", "onItemClick: "+integerList.toString());
//                    }else//从选中状态转化为未选中
//                    {
//                        viewCache.cb.setChecked(true);
//                        listStr.add(list.get(position).get("name").toString());
//                        integerList.add(position);
//                        Log.e("int", "onItemClick: "+integerList.toString());
//                    }
//                    list.get(position).put ("boolean", viewCache.cb.isChecked());
//                    adapter.notifyDataSetChanged();
//                }else if(listStr.size() ==3 && integerList.contains(position)){
//                    if(viewCache.cb.isChecked()){//被选中状态
//                        viewCache.cb.setChecked(false);
//                        listStr.remove(list.get(position).get("name").toString());
//                        integerList.remove(getPosition(position));
//                        Log.e("int", "onItemClick: "+integerList.toString());
//                    }else//从选中状态转化为未选中
//                    {
//                        viewCache.cb.setChecked(true);
//                        listStr.add(list.get(position).get("name").toString());
//                        integerList.add(position);
//                        Log.e("int", "onItemClick: "+integerList.toString());
//                    }
//                    list.get(position).put ("boolean", viewCache.cb.isChecked());
//                    adapter.notifyDataSetChanged();
//                }else{
//                    MyToast.ToastImage(MyLoveTypeActivity.this,"最多选择三项！");
//                }
//            }
//        });
    }

    public int getPosition(int postion){
        int index = 0 ;
        for(int i=0;i<integerList.size();i++){
            if(integerList.get(i) == postion){
                index = i;
                break;
            }
        }
        return index;
    }

    String likeWorkType ="";

    private void init() {

//        bar = findViewById(R.id.progress);
//        Button button = findViewById(R.id.mylove_complete);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                if(integerList.size() == 0){
//                    Toast.makeText(MyLoveTypeActivity.this, "请选择至少一项！", Toast.LENGTH_SHORT).show();
//                }else if(integerList.size() == 1){
//                    likeWorkType = list_worktype.get(integerList.get(0)).getWtId()+"";
//                    SendMes();
//                }else{
//                    likeWorkType = list_worktype.get(integerList.get(0)).getWtId()+"";
//                    for(int i=1;i<integerList.size();i++){
//                        likeWorkType = likeWorkType + "," + list_worktype.get(integerList.get(i)).getWtId();
//                    }
//                    SendMes();
//                }
//            }
//        });
    }

    public void SendMes(){
    }

}
