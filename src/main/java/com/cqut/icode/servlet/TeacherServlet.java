package com.cqut.icode.servlet;


import com.cqut.icode.factory.CreateId;
import com.cqut.icode.services.TeacherService;
import com.cqut.icode.services.impl.TeacherServiceImpl;
import com.cqut.icode.entities.Teacher;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 谭强
 * @date 2019/5/9
 */

@WebServlet(value = "/teacher")
public class TeacherServlet extends HttpServlet {
    private static TeacherService teacherService = new TeacherServiceImpl();

    /**
     * 请求所有表单信息
     * @param req  ·
     * @param resp ·
     * @throws ServletException ·
     * @throws IOException ·
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n获取数据");
        List<Map<String, Object>> teachers = teacherService.listTeachers();
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().append(JSONArray.fromObject(teachers).toString());
    }

    /**
     * 因为原生 put 解决不了传送中文数据的问题，采用 post
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Teacher teacher = getTeacher(req);
        boolean result = false;
        if (req.getParameterMap().containsKey("id")) {
            System.out.println("\n更新数据");
            teacher.setId(Long.parseLong(req.getParameter("id")));
            result = teacherService.updateTeacher(teacher);
        } else {
            System.out.println("\n插入数据");
//            teacher.setId(CreateId.createUsername());
            result = teacherService.saveTeacher(teacher);
        }
        resp.getWriter().write(result + "");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n删除数据");
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));

        String ids = "";
        String line = "";
        while ((line = br.readLine()) != null) {
            ids += line;
        }
        String[] idss = ids.split("&");

        List<Long> list = new ArrayList<>();
        for (String s : idss) {
            list.add(Long.parseLong(s.split("=")[1]));
        }
        System.out.println("ids[]: " + ids);
        System.out.println(list.toString());

        boolean result = teacherService.removeTeachers(list);
        resp.getWriter().write(result + "");
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    private Teacher getTeacher(HttpServletRequest req) {
        String name = req.getParameter("name");
        String gender = req.getParameter("gender");
        Integer age = Integer.parseInt(req.getParameter("age"));
        String academy = req.getParameter("academy");
        String dept = req.getParameter("dept");
        Float salary = Float.parseFloat(req.getParameter("salary"));
        return new Teacher(name, gender, age, academy, dept, salary);
    }
}
