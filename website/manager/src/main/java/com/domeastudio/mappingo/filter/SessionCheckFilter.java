package com.domeastudio.mappingo.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionCheckFilter implements Filter {
    private String excludedPage;
    private String[] excludedPages;
    private String homePage;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludedPage = filterConfig.getInitParameter("excludedPages");
        homePage=filterConfig.getInitParameter("homePage");
        if (excludedPage != null && excludedPage.length() > 0){
            excludedPages = excludedPage.split(";");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 获取 resquest、response、session
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        String userId = (String) session.getAttribute("userid");
        String token = (String) session.getAttribute("token");
        String role = (String) session.getAttribute("role");
        System.out.println("load file name:"+request.getServletPath());
        System.out.println("userid:"+userId);
        System.out.println("token:"+token);
        System.out.println("role:"+role);

        Boolean f=false;
        for (String page:excludedPages) {
            if (request.getServletPath().contains(page)
                    ||(userId != null&&!userId.equals("null")
                    &&token!=null&&!token.equals("null")&&
                    role!=null&&role.equals("null"))){
                f=true;
                filterChain.doFilter(request,response);
            }
        }
        if(!f){
            String path=request.getContextPath();
            //request.getRequestDispatcher(path+homePage).forward(request,response);
            response.sendRedirect(path+homePage);
        }
    }

    @Override
    public void destroy() {

    }
}
