package cn.datawin.util;

import com.mongodb.DBObject;
import org.bson.types.ObjectId;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("unused")
public class ReflectUtil {

    /* 首字母大写 */
    public static String toTitleCase(String name) {
        return Character.toTitleCase(name.charAt(0)) + name.substring(1);
    }

    /* 获取setXxx的方法名称 */
    public static String toSetMethod(String name) {
        return "set" + toTitleCase(name);
    }

    /* 获取getXxx的方法名称 */
    public static String toGetMethod(String name) {
        return "get" + toTitleCase(name);
    }

    /**
     * method.setAccessible(true); 执行方法
     * method.invoke(obj, params...) 返回方法的执行结果, void 返回null, obj为实例[静态方法可为NULL], params 为参数,无参可为0[NULL]
     */
    public static Object invokeMethod(final Object object, final Method method, Object... params) {
        try {
            convert(method, params);
            return method.invoke(object, params);
        } catch (Exception e) {
            LogUtil.info(method.toString());
            LogUtil.error(e);
        }
        return null;
    }

    public static Object[] convert(final Method method, Object... params) {
        Class<?>[] _class = method.getParameterTypes(); //查找 方法形参类型
        if (_class.length != params.length) {
            return params;
        }
        for (int i = 0; i < params.length; i++) {
            params[i] = covert(_class[i], params[i]);
        }
        return params;
    }

    /**
     * 根据method形参转换参数,支持常用参数类型
     */
    public static Object covert(Class<?> _class, Object param) {
        if (_class == String.class) {
            return param.toString();
        } else if (_class == Integer.class || "int".equals(_class.toString())) {
            return Integer.parseInt(param.toString());
        } else if (_class == Double.class || "double".equals(_class.toString())) {
            return Double.parseDouble(param.toString());
        } else if (_class == Long.class || "long".equals(_class.toString())) {
            return Long.parseLong(param.toString());
        } else if (_class == Float.class || "float".equals(_class.toString())) {
            return Float.parseFloat(param.toString());
        } else if (_class == Boolean.class || "boolean".equals(_class.toString())) {
            return Boolean.parseBoolean(param.toString());
        } else if (_class == Date.class) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(param.toString());
            } catch (ParseException e) {
                return param;
            }
        }
        return param;
    }

    /**
     * 使其可见 private-->public
     */
    protected static void makeAccessible(final Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * 持续向上查找,直到找到某个方法
     */
    public static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {// NOSONAR
                // Method不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 根据方法名获取方法
     */
    public static Method getDeclaredMethod(Class<?> _class, String methodName) {
        Method m[] = _class.getMethods();
        Method me = null;
        for (Method aM : m) {
            /* 如果是公共方法,而且名称相同则返回,如果名称相同参数不同,按先后顺序只执行第一个方法 */// 是否可以判断参数个数相同的执行
            if (aM.getModifiers() == Modifier.PUBLIC && aM.getName().equals(methodName)) {
                me = aM;
                break;
            }
        }
        return me;
    }

    /**
     * 查找泛型类的泛型
     */
    public static Class<?> getSuperClassGenricType(final Class<?> clazz, final int index) {
        Class<?> _class = Object.class;
        Type genType = clazz.getGenericSuperclass(); // 首先查找类的类型
        if (!(genType instanceof ParameterizedType)) { // 如果不是泛型类,直接返回
            return _class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments(); // 强制转换成泛型类的类型数组
        if (params.length == 0) {
            return _class;
        }
        try {
            /* 例如 class User<E,U> params(0)返回E,params(1)返回U */
            _class = (Class<?>) params[index];
        } catch (Exception ignored) {
        }

        return _class;
    }

    /**
     * 将prop 注入到 obj(CoC setName --> name)
     */
    public static void populate(Object obj, Properties prop) {
        for (Object o : prop.keySet()) {
            String key = (String) o;
            String value = prop.getProperty(key);
            String methodName = toSetMethod(key);
            Method method = getDeclaredMethod(obj.getClass(), methodName);
            invokeMethod(obj, method, value);
        }
    }

    /**
     * map 转  bean<T>
     */
    public static <T> T mapToBean(Map<String, ?> map, Class<T> _class) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        T t;
        try {
            t = _class.newInstance();
        } catch (Exception e) {
            LogUtil.error(e);
            return null;
        }
        Iterator it = map.keySet().iterator();
        while(it.hasNext()) {
            String key = it.next().toString();
            Object value = map.get(key);
            String methodName = toSetMethod(key);
            Method method = getDeclaredMethod(_class, methodName);
            if (method == null) continue;
            invokeMethod(t, method, value);
        }
        return t;
    }

    public static Map<String, Object> beanToMap(Object bean) {
        if (bean == null) {
            return null;
        }
        BeanInfo info = null;
        Map<String, Object> map = new HashMap<>();
        try {
            info = Introspector.getBeanInfo(bean.getClass());
        } catch (IntrospectionException e) {
            LogUtil.error(e);
        }
        PropertyDescriptor[] pros = new PropertyDescriptor[0];
        if (info != null) {
            pros = info.getPropertyDescriptors();
        }
        for (PropertyDescriptor pro : pros) {
            String name = pro.getName();
            if (!"class".equals(name)) {
                Method method = pro.getReadMethod();
                try {
                    Object result = method.invoke(bean);
                    if (result != null) {
                        if (ObjectId.isValid(result.toString())) {
                            result = new ObjectId(result.toString());
                        }
                        map.put(name, result);
                    }
                } catch (Exception e) {
                    LogUtil.error(e);
                }
            }
        }
        return map;
    }

    /**
     * 从list集合中取得某个字段的list
     *
     * @param list      必须是同一个Bean
     * @param fieldName 地段名,与getXXX相对应
     * @param repeat    取得数据是否可重复;
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    @SuppressWarnings("unchecked")
    public static List<?> getFieldvaluesFromList(List list,
                                                 String fieldName, boolean repeat) throws Exception {
        if (list == null || list.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        //取得bean的class
        Class beanClass = list.get(0).getClass();
        //取得方法
        Method metd = beanClass.getMethod(toGetMethod(fieldName));// 根据字段名找到对应的get方法，null表示无参数
        if (metd != null) {
            List fieldList = new ArrayList();
            for (Object object : list) {
                Object value = metd.invoke(object);
                //不为null,repeat=false:不可重复
                if (value != null && (repeat || !fieldList.contains(value))) {
                    fieldList.add(value);
                }
            }
            return fieldList;
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * 从list集合中取得某个字段的list
     *
     * @param list      必须是同一个Bean
     * @param fieldName 地段名,与getXXX相对应
     * @param repeat    取得数据是否可重复;
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public static List<?> getFieldvaluesFromDBList(List<DBObject> list,
                                                   String fieldName, boolean repeat) throws Exception {
        if (list == null || list.size() == 0) {
            return Collections.EMPTY_LIST;
        }

        List fieldList = new ArrayList();
        for (DBObject obj : list) {
            Object value = obj.get(fieldName);
            //不为null,repeat=false:不可重复
            if (value != null && (repeat || !fieldList.contains(value))) {
                fieldList.add(value);
            }
        }

        return fieldList;
    }

    /**
     * 把list<String> 转化为List<ObjectId>
     *
     * @param list
     * @return
     */
    public static List<ObjectId> convertToObjids(List<String> list) {
        if (list == null) {
            return Collections.EMPTY_LIST;
        }

        List<ObjectId> objIdList = new ArrayList<ObjectId>();

        for (String str : list) {
            objIdList.add(new ObjectId(str));
        }
        return objIdList;
    }

    public static List<String> convertToStrs(List<ObjectId> list) {
        if (list == null) {
            return Collections.EMPTY_LIST;
        }

        List<String> strs = new ArrayList<String>();

        for (ObjectId objid : list) {
            strs.add(objid.toString());
        }
        return strs;
    }

    /**
     * test
     *
     * @param args
     */
    public static void main(String[] args) {

        long q = System.currentTimeMillis();
        aa a = new aa();
        Method m = getDeclaredMethod(a.getClass(), "getName");
        invokeMethod(a, m, "2010", "nihao");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", "panmg");
        map.put("age", 22);
        map.put("sex", 0);
        aa aaa = mapToBean(map, aa.class);
        LogUtil.info(aaa);
        LogUtil.info(System.currentTimeMillis() - q);
        LogUtil.info(beanToMap(aaa));
    }

    /**
     * 把list._id转化为id
     *
     * @param list
     */
    public static List<DBObject> parseId(List<DBObject> list) {
        for (DBObject obj : list) {
            obj.put("id", obj.get("_id").toString());
        }
        return list;
    }

    /**
     * 将List._id转List<String>
     *
     * @param list
     * @return
     */
    public static List<String> parseToStr(List<DBObject> list) {
        List<String> nlist = new ArrayList<String>();
        for (DBObject obj : list) {
            nlist.add(obj.get("_id").toString());
        }
        return nlist;
    }

    static class aa {
        private String name;
        private int age;
        private int sex;

        public void getName(long name, boolean flag) {
            LogUtil.info("this is test!" + name + "==" + flag);
        }

        @Override
        public String toString() {
            return "aa [name=" + name + ", age=" + age + ", sex=" + sex + "]";
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }
    }
}
