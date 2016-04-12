package cn.edu.ntu.diabetesassess;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by LSQ on 2015/9/30.
 */
public class ViewHolder {
    TextView questions;
    TextView references;
    RadioGroup radioGroup;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    RadioButton rb4;
    RadioButton rb5;

    ViewHolder(View convertView) {
        questions = (TextView) convertView.findViewById(R.id.questions);
        references=(TextView)convertView.findViewById(R.id.references);
        radioGroup = (RadioGroup) convertView.findViewById(R.id.radiogroup);
        rb1 = (RadioButton) convertView.findViewById(R.id.rb1);
        rb2 = (RadioButton) convertView.findViewById(R.id.rb2);
        rb3 = (RadioButton) convertView.findViewById(R.id.rb3);
        rb4 = (RadioButton) convertView.findViewById(R.id.rb4);
        rb5 = (RadioButton) convertView.findViewById(R.id.rb5);
    }
}
