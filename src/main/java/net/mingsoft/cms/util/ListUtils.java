package net.mingsoft.cms.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {


    public static <T> List<List<T>> splistList(List<T> list, int subNum) {
        List<List<T>> tNewList = new ArrayList<List<T>>();
        int priIndex = 0;
        int lastPriIndex = 0;
        int insertTimes = list.size()/subNum;
        List<T> subList = new ArrayList<>();
        for (int i = 0;i <= insertTimes;i++) {
            priIndex = subNum*i;
            lastPriIndex = priIndex + subNum;
            if (i == insertTimes) {
                subList = list.subList(priIndex,list.size());
            } else {
                subList = list.subList(priIndex,lastPriIndex);
            }
            if (subList.size() > 0) {
                tNewList.add(subList);
            }
        }
        return tNewList;
    }




}
