package cn.edu.ntu.diabetesassess;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.*;

/**
 * Created by LSQ on 2015/10/3.
 */
public class MyAdapter extends BaseAdapter {
    private static String TAG="MyAdapter";
    Data data;
    List<ListItem> mList=null;
    LayoutInflater mInflater = null;
    Context context;
    String[] references;
    ClipboardManager clipboardManager;
    ContentResolver contentResolver;
    ClipData clipData;
    Resources resources;
    //int[] colors={R.color.listview_even_bac,R.color.listview_odd_bac};
    int[] backdraws={R.drawable.list_bac_even,R.drawable.list_bac_odd};

    public MyAdapter(Context context,List<ListItem> list,String[] str) {
        data=(Data)context.getApplicationContext();
        this.mInflater = LayoutInflater.from(context);
        this.mList=list;
        this.context=context;
        this.references=str;
        clipboardManager=(ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        contentResolver=context.getContentResolver();
        resources=context.getResources();

    }
    public int getCount() {
        return mList.size();
    }
    public Object getItem(int position) {
        return mList.get(position);
    }
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (true) {     //取消优化，将false改为true了
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int colornum=position%backdraws.length;
        convertView.setBackgroundDrawable(resources.getDrawable(backdraws[colornum]));//此处实现list背景交替改变
        //convertView.setBackgroundColor(colors[0]);//不知为什么设置颜色不成功

        holder.radioGroup.setId(position);                     //position并不是线性的，出现规律不明，但radiogroup.getid确实正常的
        holder.radioGroup.setOnCheckedChangeListener(null);

        holder.questions.setText(mList.get(position).getQuestion());//设置问题，以listitem类中保存的内容为准

        if(mList.get(position).getOrientation()==1){                   //设置选项的朝向
            holder.radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        }else{
            holder.radioGroup.setOrientation(LinearLayout.VERTICAL);
        }
//下面设置按钮隐藏，遇到了问题,问题设置隐藏是正常的，但按钮设置隐藏会引起混乱，选项间差异较大，就完全没必要回收viewholder了，所以本程序没有优化listview的回收功能
        String[] ans_strs=mList.get(position).getAnswers();
        Drawable alphadraw=new ColorDrawable(Color.TRANSPARENT);
        RadioButton[] rbs={holder.rb1,holder.rb2,holder.rb3,holder.rb4,holder.rb5};
        for(int i=0;i<ans_strs.length;i++){//设置答案及隐藏某些选项
            if(ans_strs[i].equals("void")){
                rbs[i].setText(" ");
                rbs[i].setButtonDrawable(alphadraw);//选项不可见不可选
                //rbs[i].setEnabled(false);//答案内容为0时不可选择,此处方法存在混乱选中问题，原因不明
            }else{
                rbs[i].setText(ans_strs[i]);
                rbs[i].setButtonDrawable(R.drawable.radiobutton_selector);
            }
        }
        switch (position+1){   //在这里设置自动选中
            case 1:
                if(data.getGender()==1){   //女1男2，按钮相同
                    mList.get(position).setAnswerId(1);
                    data.setAnswer(0,1);
                }else {
                    mList.get(position).setAnswerId(2);
                    data.setAnswer(0,2);
                }
                break;
            case 2:
                int age=data.getAge();
                if(age<=44){
                    mList.get(position).setAnswerId(1);
                    data.setAnswer(1,1);
                }else if(age<=64){
                    mList.get(position).setAnswerId(2);
                    data.setAnswer(1,2);
                }else if(age<=74){
                    mList.get(position).setAnswerId(3);
                    data.setAnswer(1,3);
                }else {
                    mList.get(position).setAnswerId(4);
                    data.setAnswer(1,4);
                }
                break;
            case 3:
                double height=(double) data.getHeight()/100;
                double weight=(double) data.getWeight()/2;
                double bmi=weight/(height*height);
                StringBuilder string=new StringBuilder();
                string.append(mList.get(position).getQuestion());
                string.append("(BMI="+String.valueOf(bmi)+")");
                holder.questions.setText(string.toString());
                if (bmi<=18.5){
                    mList.get(position).setAnswerId(1);
                    data.setAnswer(2,1);
                }else if(bmi<=24.0){
                    mList.get(position).setAnswerId(2);
                    data.setAnswer(2,2);
                }else if(bmi<=28.0){
                    mList.get(position).setAnswerId(3);
                    data.setAnswer(2,3);
                }else {
                    mList.get(position).setAnswerId(4);
                    data.setAnswer(2,4);
                }
                break;
            default:
                break;
        }
        switch(mList.get(position).getAnswerId()){
        //一个极度重要的功能：防止listview中的radiogroup混乱选中，同时具有提前选中某些项的功能
                case 1:holder.radioGroup.check(R.id.rb1);break;
                case 2:holder.radioGroup.check(R.id.rb2);break;
                case 3:holder.radioGroup.check(R.id.rb3);break;
                case 4:holder.radioGroup.check(R.id.rb4);break;
                case 5:holder.radioGroup.check(R.id.rb5);break;
                default:
                    holder.radioGroup.clearCheck();
                    break;
            }

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {//监听用户的点击行为，设置data的数组以保存答案
                ListItem temp=mList.get(group.getId());//有关temp的内容在这里不可删掉，否则滚动后之前选定的内容会消失
                //Log.e(group.getId()+" ","  hah");     //group getId（）是标准的次序
                switch (checkedId){
                    case R.id.rb1:temp.setAnswerId(1);data.setAnswer(position,1);break;
                    case R.id.rb2:temp.setAnswerId(2);data.setAnswer(position,2);break;
                    case R.id.rb3:temp.setAnswerId(3);data.setAnswer(position,3);break;
                    case R.id.rb4:temp.setAnswerId(4);data.setAnswer(position,4);break;
                    case R.id.rb5:temp.setAnswerId(5);data.setAnswer(position,5);break;
                    default:break;
                }
            }
        });//下面设置参考文献弹出框
        //holder.references.set
        holder.references.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setTitle(R.string.references)
                        .setMessage(references[position])
                        .setNegativeButton(R.string.ok,null)
                        .setPositiveButton(R.string.copy, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //实现复制到剪贴板
                                clipData=ClipData.newPlainText("reference",references[position]);
                                clipboardManager.setPrimaryClip(clipData);
                                Toast.makeText(context,R.string.copy_hint,Toast.LENGTH_LONG).show();
                            }
                        }).show();
            }
        });
        return convertView;
    }

}