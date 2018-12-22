package com.lichaobao.springsecurityjwt.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lichaobao
 * @date 2018/12/22
 * @QQ 1527563274
 */
public class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static String objectToJson(Object data){
        try{
            String string = MAPPER.writeValueAsString(data);
            LOGGER.info("transform result {}",string);
            return string;
        }catch (JsonProcessingException e){
            e.printStackTrace();
            LOGGER.info("transform json to string error :{}",e.getMessage());
        }
        return null;
    }
    public static <T> T jsonToPojo(String jsonData,Class<T> beanType){
        try{
            T t = MAPPER.readValue(jsonData,beanType);
            LOGGER.info("transform result {}",t);
            return t;
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("transform string to json error :{}",e.getMessage());
        }
        return null;
    }
}
