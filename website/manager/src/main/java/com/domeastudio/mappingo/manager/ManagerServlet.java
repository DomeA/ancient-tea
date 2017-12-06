package com.domeastudio.mappingo.manager;


import com.domeastudio.mappingo.util.JsonStringUtil;
import com.domeastudio.mappingo.util.security.BASE64Helper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(name="managerServlet",urlPatterns="/login.do",
        loadOnStartup=1)
public class ManagerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session=req.getSession();
        String path=req.getContextPath();

        String auth = req.getHeader("Authorization");
        if ((auth != null) && (auth.length() > 7)) {
            String HeadStr = auth.substring(0, 6).toLowerCase();
            if (HeadStr.compareTo("bearer") == 0) {
                auth = auth.substring(7, auth.length());
                String[] authArry = auth.split("\\.");
                try {
                    String str = new String(BASE64Helper.decryptBASE64(authArry[1]));
                    Map<String, String> user = JsonStringUtil.toMap(str);
                    if(user.get("role").compareTo("ROLE_SYSADMIN")==0){
                        session.setAttribute("token", auth);
                        session.setAttribute("role",user.get("role"));
                        session.setAttribute("userid", user.get("userid"));
                        resp.setHeader("Authorization", "bearer " + auth);
                        resp.sendRedirect(path + "/manager.jsp");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        resp.sendRedirect(path+"/index.html");
    }
}
