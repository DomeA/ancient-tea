package com.domeastudio.mappingo.servers.microservice.surveying.filter;

import com.domeastudio.mappingo.servers.microservice.surveying.config.Audience;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.dto.response.ResultMsg;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.dto.response.ResultStatusCode;
import com.domeastudio.mappingo.servers.microservice.surveying.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JWTAuthenticationFilter implements Filter {

    @Autowired
    private Audience audienceEntity;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ResultMsg resultMsg;
        HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
        String auth = httpRequest.getHeader("Authorization");
        if ((auth != null) && (auth.length() > 7))
        {
            String HeadStr = auth.substring(0, 6).toLowerCase();
            if (HeadStr.compareTo("bearer") == 0)
            {

                auth = auth.substring(7, auth.length());
                if (JwtUtil.parseJWT(auth, audienceEntity.getClientId()) != null)
                {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
        }

        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper mapper = new ObjectMapper();

        resultMsg = new ResultMsg(ResultStatusCode.INVALID_TOKEN.getErrcode(), ResultStatusCode.INVALID_TOKEN.getErrmsg(), null);
        httpResponse.getWriter().write(mapper.writeValueAsString(resultMsg));
        return;
    }

    @Override
    public void destroy() {

    }
}
