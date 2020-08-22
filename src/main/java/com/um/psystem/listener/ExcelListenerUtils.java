package com.um.psystem.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Author: zzj
 * @Date: 2020/8/22
 */
public class ExcelListenerUtils {
    /**
     * 指定阈值
     * @param consumer
     * @param threshold
     * @param <T>
     * @return
     */
    public static <T> AnalysisEventListener<T> getListener(Consumer<List<T>> consumer, int threshold) {
        return new AnalysisEventListener<T>() {
            private LinkedList<T> linkedList = new LinkedList<T>();

            @Override
            public void invoke(T t, AnalysisContext analysisContext) {
                linkedList.add(t);
                if (linkedList.size() == threshold){
                    System.out.println("读取到达阈值:开始执行自定义方法");
                    consumer.accept(linkedList);
                    linkedList.clear();
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                if (linkedList.size() > 0){
                    System.out.println("处理剩余数据:开始执行自定义方法");
                    consumer.accept(linkedList);
                }
            }
        };
    }

    /**
     * 不指定阈值，阈值默认为10
     * @param consumer
     * @param <T>
     * @return
     */
    public static <T> AnalysisEventListener<T> getListener(Consumer<List<T>> consumer){
        return getListener(consumer, 10);
    }
}
