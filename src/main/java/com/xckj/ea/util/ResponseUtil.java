package com.xckj.ea.util;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ResponseUtil {
    public static void write(HttpServletResponse response, Object o)throws Exception{
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out=response.getWriter();
        out.println(o.toString());
        out.flush();
        out.close();
    }
}
