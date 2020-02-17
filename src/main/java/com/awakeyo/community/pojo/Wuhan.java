package com.awakeyo.community.pojo;



public class Wuhan extends China {
    /**
     * @author 李启浩
     * @description 武汉对抗疫情算法 武汉加油！
     * @date 2020-02-16 20:05
     */
    void FightOutbreak() {
        while (noSuccess) {
            keepDoing();
            if (success)
                improve();
        }
    }





    private void improve() {
    }


    boolean noSuccess;
    boolean success;
    void keepDoing(){
        return;
    }
}
