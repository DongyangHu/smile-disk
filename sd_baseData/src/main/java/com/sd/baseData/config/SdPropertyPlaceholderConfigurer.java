package com.sd.baseData.config;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.sd.common.encryp.EncrypConstants;
import com.sd.common.encryp.EncryptAES;



public class SdPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    
	private static Log logger = LogFactory.getLog(SdPropertyPlaceholderConfigurer.class);
	
	
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
	    Properties attrProp = new Properties();
	    try {
	        String dbUser = EncryptAES.decryptAES(props.getProperty("user"), EncrypConstants.AES_DECRYPT_KEY);
	        String dbPwd = EncryptAES.decryptAES(props.getProperty("pwd"), EncrypConstants.AES_DECRYPT_KEY);
	        attrProp.setProperty("dbUser", dbUser);
	        attrProp.setProperty("dbPwd", dbPwd);
        } catch (Exception e) {
            logger.error("init params error", e);
        }
		props.putAll(attrProp);
		super.processProperties(beanFactoryToProcess, props); 
	}
}
