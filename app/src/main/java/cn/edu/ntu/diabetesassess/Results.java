package cn.edu.ntu.diabetesassess;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by LSQ on 16.1.21.
 */
public class Results extends Fragment{
    private static String TAG="Results";
    private TextView textView;
    private Data data;
    private double sum=0.0;
    private int risk_scores=0;
    private static int average=0;
    private double result;
    private int[] answers;
    private StringBuilder stringBuilder;
    private String[] result_des;
    private TextView resultDes;
    private Resources resources;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        data=(Data)(getActivity().getApplication());
        stringBuilder=new StringBuilder();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.result,null);
        resources=getResources();
        textView=(TextView)view.findViewById(R.id.result_textview);
        resultDes=(TextView)view.findViewById(R.id.result_des);
        result_des=resources.getStringArray(R.array.risk_level);
        answers=data.getAnswer();
        average=data.getAverage();

        if(data.getGender()==1){  //RR值因性别问题而有所区分
            data.setAnswer(1,answers[1]+4);
            data.setAnswer(5,answers[5]+2);
            data.setAnswer(13,answers[13]+2);
            answers=data.getAnswer();
        }
        for(int i=0;i<answers.length;i++){
            double[] rr=data.rrs[i];//rr数组存储每题RR值列表
            sum+=rr[answers[i]-1];//setanswer的index必须从0开始，其内容是从1开始的
            risk_scores+=rr2rp(rr[answers[i]-1]);
            stringBuilder.append(getString(R.string.di)+(i+1)+getString(R.string.tixuanzele)+answers[i]+
                    getString(R.string.gexuanxiagrr)+rr[answers[i]-1]+getString(R.string.rpduiying)+rr2rp(rr[answers[i]-1])+"\n");
        }
        result=(double)risk_scores/average;
        getResultDescription();
        stringBuilder.append(getString(R.string.sum)+String.valueOf(sum)+"\n"+"risk scores="+risk_scores
        +"\n"+"average="+average+"\n"+"result="+result);
        textView.setText(stringBuilder.toString());
        return view;
    }

    private int rr2rp(double rr){       //将RR值转换为risk points
        if(0.9<=rr&&rr<=1.1){
            return 0;
        }else if(0.7<=rr&&rr<=1.5){
            return 5;
        }else if(0.4<=rr&&rr<=3.0){
            return 10;
        }else if (0.2<=rr&&rr<=7.0){
            return 25;
        }else{
            return 50;
        }
    }
    private void getResultDescription(){
        if(result<0.01){
            resultDes.setText(result_des[0]);
            resultDes.setBackgroundColor(resources.getColor(R.color.verymuchbelow));
        }else if (result<0.5){
            resultDes.setText(result_des[1]);
            resultDes.setBackgroundColor(resources.getColor(R.color.muchbelow));
        }else if (result<0.9){
            resultDes.setText(result_des[2]);
            resultDes.setBackgroundColor(resources.getColor(R.color.below));
        }else if (result<1.1){
            resultDes.setText(result_des[3]);
            resultDes.setBackgroundColor(resources.getColor(R.color.average));
        }else if (result<2.0){
            resultDes.setText(result_des[4]);
            resultDes.setBackgroundColor(resources.getColor(R.color.above));
        }else if (result<5.0){
            resultDes.setText(result_des[5]);
            resultDes.setBackgroundColor(resources.getColor(R.color.muchabove));
        }else {
            resultDes.setText(result_des[6]);
            resultDes.setBackgroundColor(resources.getColor(R.color.verymuchabove));
        }
    }
}
