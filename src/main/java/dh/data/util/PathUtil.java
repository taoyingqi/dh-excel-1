package dh.data.util;

/**
 * Created by MT-T450 on 2017/6/7.
 */
public class PathUtil {
    public static String getClassPath() {
        String path = new Object() {
            public String getPath() {
                return this.getClass().getResource("/").getPath();
            }
        }.getPath();
        return path;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("类路径:" + PathUtil.getClassPath());
    }
}
