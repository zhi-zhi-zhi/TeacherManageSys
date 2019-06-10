package com.cqut.icode.servlet;

import com.cqut.icode.annotation.AutoWired;
import com.cqut.icode.entities.Girl;
import com.cqut.icode.entities.Page;
import com.cqut.icode.entities.Teacher;
import com.cqut.icode.entities.base.BaseEntity;
import com.cqut.icode.services.EntityService;
import com.google.gson.Gson;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 谭强
 * @date 2019/6/10
 */
@WebServlet(value = "/entity")
public class EntityServlet extends HttpServlet {
    @AutoWired
    private static EntityService service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n获取数据");

        Map<String, Object> result = new HashMap<>(16);

        String url = req.getParameter("entity");
        Class clazz = getClass(req);

        if (req.getParameter("limit") == null) {
            Page page = new Page(1, 0);
            result.put("rows", service.listEntities(page, clazz));
            result.put("total", page.getTotal());
        } else {
            Integer limit = Integer.parseInt(req.getParameter("limit"));
            Integer offset = Integer.parseInt(req.getParameter("offset"));

            Page page = new Page(limit, offset,
                    req.getParameter("sortField"),
                    req.getParameter("sortOrder"));

            result.put("rows", service.listEntities(page, clazz));
            result.put("total", page.getTotal());
        }


        System.out.println("=========== result.toString() ==============");
        System.out.println(result.toString());
        System.out.println("=========== JSONArray ==============");
        System.out.println(JSONArray.fromObject(result).toString());
        System.out.println("=========== Gson ==============");
        System.out.println(new Gson().toJson(result));

        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().append(new Gson().toJson(result));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n插入数据");

        Class clazz = getClass(req);
        BaseEntity object = getEntity(req, clazz);
        boolean result;

        result = service.saveEntity(object);
        resp.getWriter().write(result + "");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n更新数据");

        Class clazz = getClass(req);
        BaseEntity object = getEntity(req, clazz);
        boolean result;

        result = service.updateEntity(object);
        resp.getWriter().write(result + "");

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n删除数据");
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));

        StringBuilder ids = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            ids.append(line);
        }
        String[] idss = ids.toString().split("&");

        List<Long> list = new ArrayList<>();
        for (String s : idss) {
            list.add(Long.parseLong(s.split("=")[1]));
        }
        System.out.println("ids[]: " + ids);
        System.out.println(list.toString());

        boolean result = service.removeEntities(list, getClass(req));
        resp.getWriter().write(result + "");
    }

    private Class getClass(HttpServletRequest req) {
        Class result = null;

        String url = req.getParameter("entity");
        switch (url) {
            case "teacher":
                result = Teacher.class;
                break;
            case "girl":
                result = Girl.class;
                break;
            default:
        }

        return result;
    }

    private BaseEntity getEntity(HttpServletRequest req, Class clazz) {
        BaseEntity result = null;
        try {
            result = (BaseEntity) clazz.newInstance();

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();

                String value = req.getParameter(fieldName);
                if (value != null && !"".equals(value)) {
                    field.setAccessible(true);
                    field.set(result, value);
                }
            }

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }
}
