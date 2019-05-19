package com.cqut.icode.servlet;

//import com.cqut.icode.dao.UserDao;
import com.cqut.icode.entities.User;
import com.cqut.icode.factory.CreateId;
import com.cqut.icode.services.UserService;
import com.cqut.icode.services.impl.UserServiceImpl;

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
    private static UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n用户登录");
        String userId = req.getParameter("username");
        String password = req.getParameter("password");

        boolean result = userService.login(new User(Long.parseLong(userId), password));

        if (result) {
            resp.getWriter().write("success");
            HttpSession session = req.getSession();
            session.setAttribute("userId", userId);
            System.out.println("" + session.getId());
            System.out.println(session.getAttribute("userId"));
        } else {
            resp.getWriter().write("username or password wrong");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n用户注册");
        Long userId = CreateId.createUsername();
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
