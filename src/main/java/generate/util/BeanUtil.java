package generate.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.*;

@Slf4j
public class BeanUtil {
    /**
     * 将Map内的key与Bean中属性相同的内容复制到BEAN中
     *
     * @param bean       Object
     * @param properties Map
     * @throws IllegalAccessException
     */
    public static void copyMap2Bean(Object bean, Map<String, Object> properties, String... ignoreProperties) {
        // Do nothing unless both arguments have been specified
        if ((bean == null) || (properties == null)) {
            return;
        }
        // Loop through the property name/value pairs to be set
        Iterator<Map.Entry<String, Object>> entrys = properties.entrySet().iterator();
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
        while (entrys.hasNext()) {
            Map.Entry<String, Object> entry = entrys.next();
            String name = entry.getKey();
            // Identify the property name and value(s) to be assigned
            if (StringUtils.isBlank(name) || (CollectionUtils.isNotEmpty(ignoreList) && ignoreList.contains(name))) {
                continue;
            }
            Object value = entry.getValue();
            try {
                Class<?> clazz = PropertyUtils.getPropertyType(bean, name);
                if (null == clazz) {
                    continue;
                }
                String className = clazz.getName();
                if (className.equalsIgnoreCase("java.sql.Timestamp")) {
                    if (value == null || value.equals("")) {
                        continue;
                    }
                }
                BeanUtils.copyProperty(bean, name, value);
            } catch (NoSuchMethodException e) {
                continue;
            } catch (Exception e) {
                continue;
            }
        }
    }

    /**
     * 对象拷贝 数据对象空值不拷贝到目标对象
     *
     * @throws NoSuchMethodException copy
     */
    public static void copyBeanNotNull2Bean(Object databean, Object tobean, String... ignoreProperties) {
        if (databean instanceof Map) {
            copyMap2Bean(tobean, (Map) databean, ignoreProperties);
        } else {
            PropertyDescriptor origDescriptors[] = PropertyUtils
                    .getPropertyDescriptors(databean);
            List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if ("class".equals(name)) {
                    continue; // No point in trying to set an object's class
                }
                if (PropertyUtils.isReadable(databean, name) && PropertyUtils.isWriteable(tobean, name) && (CollectionUtils.isEmpty(ignoreList) || !ignoreList.contains(name))) {
                    try {
                        Object value = PropertyUtils.getSimpleProperty(databean,
                                name);
                        if (value == null) {
                            continue;
                        }
                        if (value instanceof String) {
                            String sValue = ((String) value).trim();
                            if (StringUtils.isBlank(sValue)) {
                                continue;
                            }
                        }
                        Class fromType = origDescriptors[i].getPropertyType();
                        Class toType = PropertyUtils.getPropertyType(tobean, name);
//                        //枚举转int
//                        if(EnumIntegerAble.class.isAssignableFrom(fromType) && (Integer.class.equals(toType) || int.class.equals(toType))){
//                            BeanUtils.copyProperty(tobean, name, ((EnumIntegerAble)value).getCode());
//                        }else if(EnumIntegerAble.class.isAssignableFrom(toType) && (Integer.class.equals(fromType) || int.class.equals(fromType))){
//                            //int 转枚举
//                            BeanUtils.copyProperty(tobean, name, EnumIntegerAble.codeOf((Integer)value,toType));
//                        }else{
//                            BeanUtils.copyProperty(tobean, name, value);
//                        }
                        BeanUtils.copyProperty(tobean, name, value);
                    } catch (IllegalArgumentException ie) {
                        throw new RuntimeException(ie);
                    } catch (Exception e) {
                        log.warn("copyBeanNotNull2Bean error", e);
                    }
                }
            }
        }
    }

    /**
     * 对象拷贝 数据对象空值不拷贝到目标对象
     *
     * @throws NoSuchMethodException copy
     */
    public static Map<String, Object> copyBeanNotNull2Map(Object databean, String... ignoreProperties) {
        PropertyDescriptor origDescriptors[] = PropertyUtils
                .getPropertyDescriptors(databean);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
        Map<String, Object> properties = new HashMap<String, Object>();
        for (int i = 0; i < origDescriptors.length; i++) {
            String name = origDescriptors[i].getName();
            if ("class".equals(name)) {
                continue; // No point in trying to set an object's class
            }
            if (PropertyUtils.isReadable(databean, name) && (CollectionUtils.isEmpty(ignoreList) || !ignoreList.contains(name))) {
                try {
                    Object value = PropertyUtils.getSimpleProperty(databean,
                            name);
                    if (value == null) {
                        continue;
                    }
                    if (value instanceof String) {
                        String sValue = ((String) value).trim();
                        if (StringUtils.isBlank(sValue)) {
                            continue;
                        }
                    }
                    properties.put(name,value);
                } catch (IllegalArgumentException ie) {
                    throw new RuntimeException(ie);
                } catch (Exception e) {
                    log.warn("copyBeanNotNull2Map error", e);
                }
            }
        }
        return properties;
    }

    /**
     * 对象拷贝 数据对象空值不拷贝到目标对象
     *
     * @throws NoSuchMethodException copy
     */
    public static Properties copyBeanNotNull2Property(Object databean, String... ignoreProperties) {
        PropertyDescriptor origDescriptors[] = PropertyUtils
                .getPropertyDescriptors(databean);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
        Properties properties = new Properties();
        for (int i = 0; i < origDescriptors.length; i++) {
            String name = origDescriptors[i].getName();
            if ("class".equals(name)) {
                continue; // No point in trying to set an object's class
            }
            if (PropertyUtils.isReadable(databean, name) && (CollectionUtils.isEmpty(ignoreList) || !ignoreList.contains(name))) {
                try {
                    Object value = PropertyUtils.getSimpleProperty(databean,
                            name);
                    if (value == null) {
                        continue;
                    }
                    if (value instanceof String) {
                        String sValue = ((String) value).trim();
                        if (StringUtils.isBlank(sValue)) {
                            continue;
                        }
                    }
                    properties.setProperty(name,String.valueOf(value));
                } catch (IllegalArgumentException ie) {
                    throw new RuntimeException(ie);
                } catch (Exception e) {
                    log.warn("copyBeanNotNull2Map error", e);
                }
            }
        }
        return properties;
    }
}
