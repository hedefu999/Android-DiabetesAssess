package cn.edu.ntu.diabetesassess;

import android.app.ActionBar;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends FragmentActivity {
    private static String TAG="MainActivity";
    private FragmentManager fragmentManager;
    private PersonalInfo personalInfo;
    private MultiChoices multiChoices;
    private Results results;
    private android.support.v4.app.FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar(R.layout.main_actionbar);
        setContentView(R.layout.activity_main);

        personalInfo = new PersonalInfo();
        //multiChoices = new MultiChoices();
            //注意Results类不可以在此处new初始化，会导致界面过早生成，影响data数据的显示，典型的效果是退出后再回来才能显示之前的结果

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, personalInfo);

         //add一次以后不能再次add
        //transaction.hide(multiChoices);//不做hide将会导致重影
        //transaction.hide(results);//不进行hide会导致直接显示，在使用过一次后才正常
        transaction.show(personalInfo);
        transaction.commit();

    }

    private void setActionBar(int layoutid) {
        ActionBar actionBar = getActionBar();       //actionBar.setDisplayShowHomeEnabled();什么意思？
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            LayoutInflater inflater = (LayoutInflater)
                    this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(layoutid, null);                  ///viewgroup的用法是什么？
            actionBar.setCustomView(view);
        } else {
            Log.e("---------->", "get actionbar failed");
        }
    }

    public void onActionClick(View view) {
        switch (view.getId()) {
            case R.id.forward:
                if (personalInfo.Checkinfo()) {
                    transaction = fragmentManager.beginTransaction();//这里必须重写，否则挂掉
                    if(multiChoices==null){
                        multiChoices=new MultiChoices();
                        transaction.add(R.id.container, multiChoices);
                    }

                    transaction.hide(personalInfo);
                    //tranction的各种方法
                    transaction.show(multiChoices);
                    transaction.commit();
                    setActionBar(R.layout.second_actionbar);
                } else {
                    Toast.makeText(this, R.string.ban, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.back:
                transaction = fragmentManager.beginTransaction();//这里必须重写，否则挂掉
                transaction.hide(multiChoices);
                transaction.show(personalInfo);//tranction的各种方法
                transaction.commit();
                setActionBar(R.layout.main_actionbar);
                break;
            case R.id.result:
                if(multiChoices.checkAnswer()){
                    transaction = fragmentManager.beginTransaction();
                    if(results==null){
                        results =new Results();
                        transaction.add(R.id.container, results);
                    }
                    transaction.hide(multiChoices);
                    transaction.show(results);
                    transaction.commit();
                    setActionBar(R.layout.result_actionbar);
                }else {
                    Toast.makeText(this, R.string.ban1, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.result_back:
                transaction=fragmentManager.beginTransaction();
                transaction.hide(results);
                transaction.show(multiChoices);
                transaction.commit();
                setActionBar(R.layout.second_actionbar);
                break;
            default:
                break;
        }
    }


}
