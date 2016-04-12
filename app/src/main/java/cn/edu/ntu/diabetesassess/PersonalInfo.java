package cn.edu.ntu.diabetesassess;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by LSQ on 15.12.23.
 */
public class PersonalInfo extends Fragment {
    public int getInt_gender() {
        return int_gender;
    }

    public String getStr_height() {
        return this.str_height;
    }

    public String getStr_weight() {
        return str_weight;
    }

    public String getStr_age() {
        return str_age;
    }

    private int int_gender=0;
    private String str_height;
    private String str_weight;
    private String str_age;
    private Data data;

    public boolean isCheckinfo() {
        return this.checkinfo;
    }

    public void setCheckinfo() {
        this.checkinfo = Checkinfo();
    }

    private boolean checkinfo;

    private RadioGroup radioGroup;
    private RadioButton radioButton_male;
    private RadioButton radioButton_female;
    private EditText et_age;
    private EditText average;
    private EditText et_height;
    private EditText et_weight;
    private Button button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        data=(Data)getActivity().getApplication();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {    //hehe,删掉container，override会有惊喜
        View view=inflater.inflate(R.layout.personalinfo,null);

        radioGroup=(RadioGroup)view.findViewById(R.id.radiogroup_gender);
        radioButton_female=(RadioButton)view.findViewById(R.id.female);
        radioButton_male=(RadioButton)view.findViewById(R.id.male);
        et_age =(EditText)view.findViewById(R.id.info_age);
        et_height =(EditText)view.findViewById(R.id.info_height);
        et_weight =(EditText)view.findViewById(R.id.info_weight);
        radioButton_female.setButtonDrawable(R.drawable.radiobutton_selector);
        radioButton_male.setButtonDrawable(R.drawable.radiobutton_selector);
        average=(EditText)view.findViewById(R.id.average_et);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.female:
                        int_gender=1;
                        break;
                    case R.id.male:
                        int_gender=2;
                        break;
                    default:
                        break;
                }
                data.setGender(int_gender);
            }
        });
        return view;

    }
    public boolean Checkinfo(){
        str_height=et_height.getText().toString();
        str_weight=et_weight.getText().toString();
        str_age=et_age.getText().toString();
        String average_str=average.getText().toString();
        if((int_gender!=0)&&(!(str_age.equals("")))&&
                (!(str_weight.equals("")))&&(!(str_height.equals("")))){
            data.setAge(Integer.parseInt(str_age));
            data.setHeight(Integer.parseInt(str_height));
            data.setWeight(Integer.parseInt(str_weight));
            data.setAverage(average_str.equals("")?data.getAverage():Integer.parseInt(average_str));
            return true;
        }else {
            return false;
        }
    }

}
