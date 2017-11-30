package com.domeastudio.mappingo.server.microservice.gisprocess.rest;

import com.domeastudio.mappingo.server.microservice.gisprocess.sdk.tools.GeometryFormateHelper;
import com.domeastudio.mappingo.server.microservice.gisprocess.dto.Message2Client;
import com.domeastudio.mappingo.server.microservice.gisprocess.utils.ErrorCode;
import com.vividsolutions.jts.geom.Geometry;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by domea on 17-5-29.
 */
@Api("gis 空间处理服务")
@RestController
@RequestMapping
public class GISProcessAPI {

    @Autowired
    private Message2Client message2Client;

    @ApiOperation(value = "地理对象转换成WKT字符串", notes = "", httpMethod = "POST")
    @ApiImplicitParam(name = "geometry", value = "geometry", required = true, dataTypeClass = Geometry.class)
    @ApiResponse(code = 200, message = "JSONObject", response = Message2Client.class)
    @RequestMapping(value = "/geo2wkt",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_PLAIN_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Message2Client geo2WKT(@RequestBody Geometry geometry) {
        try {
            String wkt = GeometryFormateHelper.getWKT(geometry);
            message2Client.setCode("200");
            message2Client.setMessage(wkt);
        } catch (RuntimeException e) {
            message2Client.setCode(ErrorCode.conversionFaile.getKey());
            message2Client.setMessage(ErrorCode.conversionFaile.getValue());
        }
        return message2Client;
    }
}
