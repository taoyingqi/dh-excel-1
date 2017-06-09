package dh.data.common.bean;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Beans {
    private static ConcurrentMap<String, BeanCopier> beanCopiers = new ConcurrentHashMap<>();
    private static ConcurrentMap<String, NullAwareBeanCopier> nullBeanCopiers = new ConcurrentHashMap<>();

    class simpleConverter implements Converter {
        simpleConverter() {
        }

        public Object convert(Object value, Class target, Object context) {
            return null;
        }
    }

    public static void copy(Object from, Object to) throws Exception {
        BeanCopier copier = _createCopier(from, to, false);
        copier.copy(from, to, null);
    }

    public static <T> T copyBean(Object from, Class<T> toClass) throws Exception {
        T to = toClass.newInstance();
        copy(from, to);
        return to;
    }

    public static <T1, T2> List<T2> copyCollection(Collection<T1> fromCollection, Class<T2> toClass) throws Exception {
        List<T2> toList = new ArrayList();
        for (T1 from : fromCollection) {
            T2 to = toClass.newInstance();
            copy(from, to);
            toList.add(to);
        }
        return toList;
    }

    public static final void copyNullAware(Object from, Object to) throws Exception {
        NullAwareBeanCopier copier = _createNullAwareCopier(from, to);
        copier.copy(from, to);
    }

    public static <T> T copyBeanNullAware(Object from, Class<T> toClass) throws Exception {
        T to = toClass.newInstance();
        copyNullAware(from, to);
        return to;
    }

    public static <T1, T2> List<T2> copyCollectionNullAware(Collection<T1> fromCollection, Class<T2> toClass) throws Exception {
        List<T2> toList = new ArrayList();
        for (T1 from : fromCollection) {
            T2 to = toClass.newInstance();
            copy(from, to);
            toList.add(to);
        }
        return toList;
    }

    private static BeanCopier _createCopier(Object from, Object to, boolean useConverter) throws Exception {
        String fromName = from.getClass().getSimpleName();
        String toName = to.getClass().getSimpleName();
        String converterFlag = String.valueOf(useConverter);
        String key = fromName + "->" + toName + "|" + converterFlag;
        if (beanCopiers.containsKey(key)) {
            return (BeanCopier) beanCopiers.get(key);
        }
        beanCopiers.putIfAbsent(key, BeanCopier.create(from.getClass(), to.getClass(), useConverter));
        return (BeanCopier) beanCopiers.get(key);
    }

    private static NullAwareBeanCopier _createNullAwareCopier(Object from, Object to) throws Exception {
        Class<?> fromClass = from.getClass();
        Class<?> toClass = to.getClass();
        String key = fromClass.getSimpleName() + "->" + toClass.getSimpleName();
        if (nullBeanCopiers.containsKey(key)) {
            return (NullAwareBeanCopier) nullBeanCopiers.get(key);
        }
        nullBeanCopiers.putIfAbsent(key, new NullAwareBeanCopier(fromClass, toClass));
        return (NullAwareBeanCopier) nullBeanCopiers.get(key);
    }

}
