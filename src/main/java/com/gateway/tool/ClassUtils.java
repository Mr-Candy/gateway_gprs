 package com.gateway.tool;

 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

 public class ClassUtils
 {
   public static Logger LOGGER = LoggerFactory.getLogger(ClassUtils.class);
 
   public static Object getBean(String className)
   {
     Class clazz = null;
     try
     {
       clazz = Class.forName(className);
     }
     catch (Exception ex)
     {
       LOGGER.info("找不到指定的类");
     }
     if (clazz != null)
     {
       try
       {
         return clazz.newInstance();
       }
       catch (Exception ex) {
         LOGGER.info("找不到指定的类");
       }
     }
     return null;
   }
 }

