package com.cqut.icode.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 谭强
 * @date 2019/5/13
 */

@WebFilter(value = "/*")
public class LoginFilter implements Filter {
    /**
     * 需要过滤的静态资源
     */
    private static List<String> urls = new ArrayList<>();

    static {
        urls.add("login");
        urls.add("js");
        urls.add("css");
        urls.add("json");
        urls.add("html");
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("\n拦截器");
        HttpServletRequest req = ((HttpServletRequest) request);
        HttpServletResponse resp = (HttpServletResponse) response;

        // 本着节省的原则，若无session存在则不创建
        HttpSession session = req.getSession(false);
        String urlPattern = req.getServletPath();
        String path = req.getRequestURL().toString();
        System.out.println("url pattern: "+urlPattern);
        System.out.println("path: " + path);
        for (String url : urls) {
            if (url.equals(urlPattern) || path.contains(url)) {
                // 放行
                System.out.println("放行");
                chain.doFilter(request, response);
                return;
            }
        }

        if (session != null && (session.getAttribute("username")) != null) {
            System.out.println("用户已登陆");
            System.out.println("session id: " + session.getId());
            System.out.println("username: " + session.getAttribute("username"));

            // chain.doFilter将请求转发给过滤器链下一个filter
            // 如果没有filter那就是请求的资源，比如servlet
            chain.doFilter(request, response);
        } else {
            System.out.println("拦截");
            System.out.println("````````````````````````");
            // ajax访问请求的头部信息中X-requested-with 属性的值是XMLHttpRequest
            if ("XMLHttpRequest".equals(req.getHeader("X-Requested-with"))) {
                // 表明要重定向
                System.out.println("ajax请求，通过写入response客户端实现重定向");
                resp.setHeader("REDIRECT", "REDIRECT");
                resp.setHeader("urlLocation", "login.jsp");
            } else {
                // ajax只是利用脚本访问对应url获取数据
                // 除了获取返回数据外不能做其他操作，例如下面的重定向就不会生效
                System.out.println("非ajax请求，可直接重定向");
                resp.sendRedirect("/jsp/login.jsp");
            }
        }
    }

    @Override
    public void destroy() {

    }
}
