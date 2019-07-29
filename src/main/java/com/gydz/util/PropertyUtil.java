package com.gydz.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * properties�ļ���ȡ������
 */
public class PropertyUtil {
	
    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
    
    private static Properties props;
    
    static{
        loadProps(); 
    }

    synchronized static private void loadProps(){
        logger.info("");
        props = new Properties();
        InputStream in = null;
        try {
        	//ͨ������������л�ȡproperties�ļ���
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("db.properties");
            //ͨ������л�ȡproperties�ļ���
            //in = PropertyUtil.class.getResourceAsStream("/db.properties");
            props.load(in);
        } catch (FileNotFoundException e) {
            logger.error("jdbc.properties");
        } catch (IOException e) {
            logger.error("");
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("jdbc.properties");
            }
        }
        logger.info("...........");
        logger.info("properties" + props);
    }

    public static String getProperty(String key){
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }
    
    public static void main(String[] args) {
    	System.out.println("biz_schemaName:"+PropertyUtil.getProperty("biz_schemaName"));
	}
}
