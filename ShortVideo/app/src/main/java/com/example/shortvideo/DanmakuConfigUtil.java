package com.example.shortvideo;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;

public class DanmakuConfigUtil {

    public static DanmakuContext getDanmakuContext(){

        //设置显示最大行数
        Map<Integer, Integer> maxLines = new HashMap<>(16) ;
        maxLines.put(BaseDanmaku.TYPE_SCROLL_RL, 5) ;

        //设置是否显示重叠
        Map<Integer, Boolean> overMap = new HashMap<>(16) ;
        overMap.put(BaseDanmaku.TYPE_SCROLL_RL, true) ;
        overMap.put(BaseDanmaku.TYPE_FIX_TOP,true) ;

        //实例化弹幕上下文
        DanmakuContext mDmkContext = DanmakuContext.create() ;
        mDmkContext
                .setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3)
                .setDuplicateMergingEnabled(false)  //不可重复合并
                .setScrollSpeedFactor(1.2f)   //设置滚动速度因子
                .setScaleTextSize(1.2f)    //弹幕字体缩放
                .setMaximumLines(maxLines)   //设置最大滚动行
                .preventOverlapping(overMap).setDanmakuMargin(40) ;

        return mDmkContext ;
    }

    /**
     * [生成默认解析]
     * @type {[type]}
     */
    public static BaseDanmakuParser getDefaultDanmakuParser(){
        return new BaseDanmakuParser() {
            @Override
            protected IDanmakus parse() {
                return new Danmakus();
            }
        } ;
    }

    /**
     * 获取一条弹幕
     * @param mDmkContext
     * @param time
     * @param content
     * @return
     */
    public static BaseDanmaku getOneDanmu(Context context, DanmakuContext mDmkContext, long time, String content){
        //创建一条从右侧开始滚动的弹幕
        BaseDanmaku danmaku = mDmkContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if(danmaku == null || !danmaku.isPrepared()){
            return null ;
        }
        danmaku.text = content ;
        danmaku.padding = 5 ;
        danmaku.priority = 0 ;
        danmaku.isLive = true ;
        danmaku.setTime(time+1000);
        danmaku.textSize = sp2px(context,18f) ;
        danmaku.textColor = Color.RED ;
        danmaku.borderColor = Color.GREEN ;

        return danmaku ;
    }

    /**
     * sp转px的方法。
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
