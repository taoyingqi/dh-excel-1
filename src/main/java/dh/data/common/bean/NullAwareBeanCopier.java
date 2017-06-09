package dh.data.common.bean;

import net.sf.cglib.core.ReflectUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public class NullAwareBeanCopier {
    private PropertyDescriptor[] source;
    private PropertyDescriptor[] dest;
    private Map<String, PropertyDescriptor> sourceMap = new HashMap<>();

    public NullAwareBeanCopier(Class<?> sourceClass, Class<?> destClass) throws Exception {
        this.source = ReflectUtils.getBeanGetters(sourceClass);


        this.dest = ReflectUtils.getBeanSetters(destClass);
        for (PropertyDescriptor fromPropery : this.source) {
            String name = fromPropery.getName();
            if (!name.equals("class")) {
                this.sourceMap.putIfAbsent(name, fromPropery);
            }
        }
    }

    //@SuppressWarnings("all")
    public void copy(Object from, Object to) throws Exception {
        Exception totalEx = new Exception();
        boolean hasEx = false;
        for (PropertyDescriptor toProperty : this.dest) {
            PropertyDescriptor fromProperty = this.sourceMap.get(toProperty.getName());
            if (fromProperty != null) {
                Object value = fromProperty.getReadMethod().invoke(from, new Object[0]);
                if (value != null) {
                    Method writeMethod = toProperty.getWriteMethod();
                    try {
                        writeMethod.invoke(to, new Object[]{value});
                    } catch (Exception ex) {
                        hasEx = true;
                        totalEx.addSuppressed(new ReflectException(writeMethod.getName(), ex));
                    }
                }
            }
        }
        if (hasEx) {
            throw totalEx;
        }
    }
}
