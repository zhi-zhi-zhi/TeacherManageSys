package com.cqut.icode.servlet;

//import com.cqut.icode.dao.UserDao;
import com.cqut.icode.annotation.AutoWired;
import com.cqut.icode.entities.User;
import com.cqut.icode.util.RandomLong;
import com.cqut.icode.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 谭强
 * @date 2019/5/12
 */

@WebServlet(value = "/login")
public class UserServlet extends HttpServlet {
    @AutoWired
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("\n用户登录");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean result = userService.login(new User(Long.parseLong(username), password));

        if (result) {
            resp.getWriter().write("success");
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            System.out.println("" + session.getId());
            System.out.println(session.getAttribute("username"));
        } else {
            resp.getWriter().write("username or password wrong");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("\n用户注册");
        Long userId = RandomLong.create();
        String password = req.getParameter("password");
        System.out.println("password: " + password);
        User user = new User(userId, password);
        boolean result = userService.register(user);

        if (result) {
            System.out.println("true");
            resp.getWriter().write(userId.toString());
        } else {
            resp.getWriter().write("false");
        }
    }
}
