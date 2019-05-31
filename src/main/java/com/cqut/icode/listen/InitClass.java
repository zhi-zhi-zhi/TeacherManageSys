package com.cqut.icode.listen;

import net.sf.json.JSONObject;

import javax.servlet.annotation.WebListener;
import java.io.*;
import java.util.Objects;

/**
 * @author 谭强
 * @date 2019/5/20
 */
@WebListener
public class InitClass {
    static {
        BufferedReader bf;
        try {
            // 读取配置文件
            String pathUrl = Objects.requireNonNull(
                    InitClass.class.getClassLoader().getResource("Config.json")).getPath();

            bf = new BufferedReader(new FileReader(new File(pathUrl)));

            StringBuilder jsonStr = new StringBuilder();
            String temp;

            while ((temp = bf.readLine()) != null) {
                jsonStr.append(temp);
            }

            JSONObject object = JSONObject.fromObject(jsonStr.toString());

            // 注入配置文件
            Inject inject = new Inject();
            inject.setObject(object);
            inject.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
