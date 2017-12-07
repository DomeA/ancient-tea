package com.domeastudio.mappingo.servers.microservice.surveying.filter;

import com.domeastudio.mappingo.servers.microservice.surveying.dto.response.ClientMessage;
import com.domeastudio.mappingo.servers.microservice.surveying.dto.response.ResultStatusCode;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.TuserEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services.TUserService;
import com.domeastudio.mappingo.servers.microservice.surveying.util.DateUtil;
import com.domeastudio.mappingo.servers.microservice.surveying.util.JsonStringUtil;
import com.domeastudio.mappingo.servers.microservice.surveying.util.JwtUtil;
import com.domeastudio.mappingo.servers.microservice.surveying.util.security.BASE64Helper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;


public class JWTAuthenticationFilter implements Filter {
    @Autowired
    private TUserService tUserService;


    @Override
    public void init(FilterConfig filterConfig){
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ClientMessage clientMessage;
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String auth = httpRequest.getHeader("Authorization");
        TuserEntity tuserEntity = null;
        if ((auth != null) && (auth.length() > 7)) {
            String HeadStr = auth.substring(0, 6).toLowerCase();
            if (HeadStr.compareTo("bearer") == 0) {

                auth = auth.substring(7, auth.length());
                String[] authArry = auth.split("\\.");
                try {
                    String str = new String(BASE64Helper.decryptBASE64(authArry[1]));
                    Map<String, String> user = JsonStringUtil.toMap(str);
                    tuserEntity = tUserService.findUserOne(user.get("userid"));
                    Integer sub = DateUtil.getDateSpace(tuserEntity.getRegistTime(), DateUtil.dateToString("yyyy-MM-dd", new Date(), "MEDIUM"));
                    if (sub <= tuserEntity.getAuthorTime() && tuserEntity != null && JwtUtil.parseJWT(auth, tuserEntity.getToken()) != null) {
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper mapper = new ObjectMapper();

        clientMessage = new ClientMessage(ResultStatusCode.INVALID_TOKEN.getCode(), ResultStatusCode.INVALID_TOKEN.getMsg(), null);
        httpResponse.getWriter().write(mapper.writeValueAsString(clientMessage));
        return;
    }

    @Override
    public void destroy() {

    }
}
