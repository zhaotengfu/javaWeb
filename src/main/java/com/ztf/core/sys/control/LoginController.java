package com.ztf.core.sys.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ztf
 * @version 1.0
 *          <p>
 *          2018-2-5
 *          </P>
 */
@Controller
public class LoginController {
    /**
     * 默认首页控制器
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response){
        System.out.println("123131313132");
        return "index";
    }
}
