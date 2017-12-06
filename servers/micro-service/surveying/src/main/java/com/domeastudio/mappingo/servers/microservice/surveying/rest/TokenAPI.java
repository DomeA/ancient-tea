package com.domeastudio.mappingo.servers.microservice.surveying.rest;

import com.domeastudio.mappingo.servers.microservice.surveying.config.Audience;
import com.domeastudio.mappingo.servers.microservice.surveying.dto.request.Login;
import com.domeastudio.mappingo.servers.microservice.surveying.dto.request.Register;
import com.domeastudio.mappingo.servers.microservice.surveying.dto.response.AccessToken;
import com.domeastudio.mappingo.servers.microservice.surveying.dto.response.ClientMessage;
import com.domeastudio.mappingo.servers.microservice.surveying.dto.response.ResultStatusCode;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.TuserEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services.TUserService;
import com.domeastudio.mappingo.servers.microservice.surveying.util.DateUtil;
import com.domeastudio.mappingo.servers.microservice.surveying.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/oauth")
public class TokenAPI {
    @Autowired
    TUserService tUserService;

    @Autowired
    private Audience audienceEntity;

    @RequestMapping(value = "/token", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ClientMessage getAccessToken(@RequestBody Login loginPara) {
        ClientMessage clientMessage;
        try {
            //验证码校验在后面章节添加
            //验证用户名密码
            TuserEntity user = tUserService.login(loginPara.getUserName(), loginPara.getPassword());
            if (user == null) {
                clientMessage = new ClientMessage(ResultStatusCode.INVALID_USERNAME_OR_PASSWORD.getCode(),
                        ResultStatusCode.INVALID_USERNAME_OR_PASSWORD.getMsg(), null);
                return clientMessage;
            }
            if (loginPara.getClientId() == null
                    || (loginPara.getClientId().compareTo(user.getToken()) != 0)) {
                clientMessage = new ClientMessage(ResultStatusCode.INVALID_CLIENTID.getCode(),
                        ResultStatusCode.INVALID_CLIENTID.getMsg(), null);
                return clientMessage;
            }
            Integer sub = DateUtil.getDateSpace(user.getRegistTime(), DateUtil.dateToString("yyyy-MM-dd", new Date(), "MEDIUM"));
            if (sub > user.getAuthorTime()) {
                clientMessage = new ClientMessage(ResultStatusCode.INVALID_TIME.getCode(),
                        ResultStatusCode.INVALID_TIME.getMsg(), null);
                return clientMessage;
            }
            List<String> roleByName = tUserService.findRoleByName(user);
            StringBuilder stringBuilder = new StringBuilder();
            if (roleByName.size() > 0) {
                for (String role : roleByName) {
                    stringBuilder.append(role);
                }
            }
            //拼装accessToken
            String accessToken = JwtUtil.createJWT(loginPara.getUserName(), user.getUid(),
                    stringBuilder.toString(), user.getToken(), audienceEntity.getName(),
                    audienceEntity.getExpiresSecond() * 1000, user.getToken());

            //返回accessToken
            AccessToken accessTokenEntity = new AccessToken();
            accessTokenEntity.setAccess_token(accessToken);
            accessTokenEntity.setExpires_in(audienceEntity.getExpiresSecond());
            accessTokenEntity.setToken_type("bearer");
            clientMessage = new ClientMessage(ResultStatusCode.OK.getCode(),
                    ResultStatusCode.OK.getMsg(), accessTokenEntity);
            return clientMessage;

        } catch (Exception ex) {
            clientMessage = new ClientMessage(ResultStatusCode.SYSTEM_ERR.getCode(),
                    ResultStatusCode.SYSTEM_ERR.getMsg(), null);
            return clientMessage;
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ClientMessage addUSer(@RequestBody Register register) {
        ClientMessage clientMessage;
        Boolean f = tUserService.createUser(register.getName(), register.getPwd(), register.getEmail(), register.getPhone(), register.getTerm());
        System.out.println("用户：" + register.getName() + (f ? "成功！" : "已经存在"));
        if (f) {
            TuserEntity tuserEntity = tUserService.findUserByName(register.getName());
            clientMessage = new ClientMessage(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), tuserEntity.getToken());
        } else {
            clientMessage = new ClientMessage(ResultStatusCode.INVALID_USERNAME.getCode(), ResultStatusCode.INVALID_USERNAME.getMsg(), "用户名已经存在");
        }
        return clientMessage;
    }
}
