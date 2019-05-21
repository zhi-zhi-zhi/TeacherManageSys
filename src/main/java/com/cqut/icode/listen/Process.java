package com.cqut.icode.listen;

import com.cqut.icode.annotation.AutoWired;
import com.cqut.icode.services.impl.TeacherServiceImpl;
import com.cqut.icode.services.impl.UserServiceImpl;
import com.cqut.icode.servlet.TeacherServlet;
import com.cqut.icode.servlet.UserServlet;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @author 谭强
 * @date 2019/5/21
 */
class Process {
    private JSONObject object;

    void setObject(JSONObject object) {
        this.object = object;
    }

    void init() {
        process(TeacherServlet.class);
        process(UserServlet.class);
        process(TeacherServiceImpl.class);
        process(UserServiceImpl.class);
    }

    private void process(Class tClass) {
        try {
            Field[] fields = tClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(AutoWired.class)) {
                    System.out.println(object.get(field.getType().getSimpleName()));
                    Class clazz = Class.forName(object.get(field.getType().getSimpleName()).toString());
                    field.setAccessible(true);
                    field.set(null, clazz.newInstance());
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
