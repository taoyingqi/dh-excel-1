package dh.data.dao;

import dh.data.model.Mid;

import java.util.List;

/**
 * Created by MT-T450 on 2017/6/7.
 */
public class MidDao {
    private final static String file = "/dist/181个航班中间表 0411.xlsx";

    public static boolean save(Mid mid) {
        return true;
    }

    public static boolean saveList(List<Mid> list) {
        return true;
    }

    public static List<Mid> getAll() {
        List<Mid> list = null;
        return list;
    }

    public static List<Mid> getList(int start, int end) {
        List<Mid> list = null;
        return list;
    }

    public static Mid get(int index) {
        Mid mid = new Mid();
        return mid;
    }
}
