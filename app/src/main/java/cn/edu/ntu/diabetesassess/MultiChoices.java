package cn.edu.ntu.diabetesassess;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LSQ on 15.12.23.
 */
public class MultiChoices extends Fragment{
    private ListView mListView;
    private List<ListItem> questionlist;
    private String[] questions;
    private String[] references;
    private Resources resources=null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView=inflater.inflate(R.layout.multichoices,null);
        mListView = (ListView)mView.findViewById(R.id.listview);
        resources=getResources();
        questions=resources.getStringArray(R.array.question);//问题数组
        references=resources.getStringArray(R.array.reference);//参考文献数组
        questionlist = getList();

        MyAdapter adapter = new MyAdapter(getActivity(), questionlist,references);
        mListView.setAdapter(adapter);
        mListView.setDivider(null);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               //无效
            }
        });
        return mView;
    }
    private List<ListItem> getList() {
        List<ListItem> list = new ArrayList<>();
        int[] answerss={R.array.answer1,R.array.answer2,R.array.answer3,R.array.answer4,//此处未使用二位数组，注意在扩展列表时要手动添加
                R.array.answer5,R.array.answer6,R.array.answer7,R.array.answer8,
                R.array.answer9,R.array.answer10,R.array.answer11,R.array.answer12,
                R.array.answer13,R.array.answer14,R.array.answer15,R.array.answer16,
                R.array.answer17,R.array.answer18,R.array.answer19};
        int[] orientations=resources.getIntArray(R.array.orientation);//这里控制每道题的选项排列方向

        for (int i = 0; i < answerss.length; i++) {
            ListItem listItem = new ListItem();

            listItem.setQuestion(questions[i]);
            listItem.setQuestionNum(i);
            listItem.setOrientation(orientations[i]);
            String[] str=resources.getStringArray(answerss[i]);
            for(int j=0;j<5;j++){
                if(str[j].equals("void")){
                }
                listItem.setAnswers(j,str[j]);//在values中设置问题答案，为void表示该选项不显示
                //Log.e(j+" 答案是  -----------》",str[j]);
            }
            list.add(listItem);
        }
        return list;
    }

    public boolean checkAnswer(){//检查是否完整解答所有问题
        int i=0;
        for(i=0;i<questionlist.size();i++){
            if(questionlist.get(i).getAnswerId() == 0)
                break;
        }
        if(i==questionlist.size()) {
            return true;
        }else {
            return false;
        }
    }
}
