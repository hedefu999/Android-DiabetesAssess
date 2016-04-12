package cn.edu.ntu.diabetesassess;

/**
 * Created by LSQ on 2015/9/30.
 */
public class ListItem {
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public String[] getAnswers() {
        return answers;
    }
    public void setAnswers(int i,String str) {
        answers[i]=str;
    }

    private String question; //问题内容
    private int answerId = 0; //设置哪个选项提前选中
    private int questionNum=0;//可选项是0-5个
    private String[] answers={"1","1","1","1","1"};//选项的内容，设置为0表示隐藏
    private int orientation=0;//朝向,默认为0-布局文件中的垂直方向portrait，1-horizontal

}
