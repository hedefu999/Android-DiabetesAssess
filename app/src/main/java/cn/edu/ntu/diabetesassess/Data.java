package cn.edu.ntu.diabetesassess;

import android.app.Application;

/**
 * Created by LSQ on 16.1.21.
 */
public class Data extends Application{
    private int gender;
    private int age;
    private int height;
    private int weight;

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    private int average=111;
    private int[] answer;
    private double[] rr1={1.12,0.89};
    private double[] rr2={0.072,0.64,2.04,1.96,0.08,0.60,3.61,1.06};//前男后女
    private double[] rr3={1.0,1.0,2.61,2.55};
    private double[] rr4={1.0,1.41,2.51,3.96};
    private double[] rr5={1.59,0.97,0.6};
    private double[] rr6={1.0,2.17,1.0,2.28};//前半组用于男性，后半组用于女性
    private double[] rr7={1.0,1.05,1.64,1.19};
    private double[] rr8={1.0,0.94,1.05,1.38};
    private double[] rr9={1.09,1.0,1.28};
    private double[] rr10={1.0,0.69};
    private double[] rr11={1.0,1.551};
    private double[] rr12={0.87,0.7,0.69,0.72,1.04};
    private double[] rr13={1.0,0.69};
    private double[] rr14={1.0,0.89,1.0,0.86};//
    private double[] rr15={1.0,1.04,1.13,1.15,1.32};
    private double[] rr16={1.0,0.81,0.91};
    private double[] rr17={1.0,0.72,0.70};
    private double[] rr18={1.0,0.91,0.76};
    private double[] rr19={1.0,0.87,0.77};
    double[][] rrs={rr1,rr2,rr3,rr4,rr5,rr6,rr7,rr8,rr9,rr10,rr11,rr12,rr13,rr14,rr15,rr16,rr17,rr18,rr19};


    @Override
    public void onCreate() {
        answer=new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};//数组长度固定
        super.onCreate();
    }
    //geter,seter方法

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int id,int value) {
        answer[id]=value;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


}
