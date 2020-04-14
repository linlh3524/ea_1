package com.xckj.ea.util;

import com.alibaba.fastjson.JSONObject;
import com.xckj.ea.common.ResponseCode;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import sun.misc.IOUtils;

import java.io.IOException;
@Component
public  class JsonFileAccess {


    public JSONObject getjsonFromFile(Resource resource) throws IOException {
        String jsonStr=new String(IOUtils.readFully(resource.getInputStream(),-1,true));
        JSONObject json=JSONObject.parseObject(jsonStr);

        return json;

    }
}
