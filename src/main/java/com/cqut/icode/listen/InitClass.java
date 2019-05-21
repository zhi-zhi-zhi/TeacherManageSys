package com.cqut.icode.listen;

import com.cqut.icode.annotation.AutoWired;
import com.cqut.icode.services.impl.TeacherServiceImpl;
import com.cqut.icode.services.impl.UserServiceImpl;
import com.cqut.icode.servlet.TeacherServlet;
import com.cqut.icode.servlet.UserServlet;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebListener;
import java.io.*;
import java.lang.reflect.Field;
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

            // 注入对象
            Process process = new Process();
            process.setObject(object);
            process.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
