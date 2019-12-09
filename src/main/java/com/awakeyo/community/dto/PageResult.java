package com.awakeyo.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author awakeyoyoyo
 * @className PageResult
 * @description TODO
 * @date 2019-12-05 20:51
 */
@Data
public class PageResult<T>{
    //是否显示前一页按钮
    private boolean showPrevious;
    //是否显示第一页按钮
    private boolean showFirstPage;
    //是否显示下一页按钮
    private boolean showNext;
    //是否显示最后一页按钮
    private boolean showEndPage;
    //当前页数
    private Integer pageNo;
    //结果集
    private List<T> reslts;
    //所有页数
    private Integer pageCount;
    //
    private List<Integer> pages=new ArrayList<>();

    public void init(Integer pageCount, Integer pageNo) {
        this.pageCount=pageCount;
        //init
        this.pageNo=pageNo;
        pages.add(pageNo);
        for (int i=1;i<=3;i++){
            if (pageNo-i>0){
                pages.add(0,pageNo-i);
            }
            if (pageNo+i<=pageCount){
                pages.add(pageNo+i);
            }
        }

        //是否展示前一页
        showPrevious=pageNo==1?false:true;
        //是否展示后一页
        showNext=pageNo==pageCount?false:true;

        //是否展示第一页
        if (pages.contains(1)){
            showFirstPage=true;
        }else {
            showFirstPage=false;
        }
        //是否展示最后一页
        if (pages.contains(pageCount)){
            showEndPage=true;
        }else {
            showEndPage=false;
        }

    }

}
