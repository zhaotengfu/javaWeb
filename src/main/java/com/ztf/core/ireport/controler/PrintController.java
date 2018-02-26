package com.ztf.core.ireport.controler;

import com.ztf.core.ireport.entity.ChartDataSource;
import com.ztf.core.ireport.entity.JavaBeanPerson;
import com.ztf.core.ireport.util.JasperHelper;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ztf
 * @version 1.0
 *          <p>
 *          2018-2-6
 *          </P>
 */
@Controller
@RequestMapping("/print")
public class PrintController {

    @RequestMapping(params = "report")
    public String report(Model model,String bblx, HttpServletRequest request, HttpServletResponse response) {
        List<JavaBeanPerson> list = JavaBeanPerson.getList();
        List<Map<String,Object>> listMap = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("CATEGORY","注销登记");
        map.put("SERIES","注销登记");
        map.put("VALUE","1");

        listMap.add(map);
        map = new HashMap<>();
        map.put("CATEGORY","注册登记");
        map.put("SERIES","注册登记");
        map.put("VALUE","5");
        listMap.add(map);
        map = new HashMap<>();
        map.put("CATEGORY","初次申请");
        map.put("SERIES","初次申请");
        map.put("VALUE","2");
        listMap.add(map);
        try {
            // 报表数据源
            JRDataSource jrDataSource = null;
            String path = null;
            if("tx".equals(bblx)){
                 path = request.getSession().getServletContext().getRealPath("/WEB-INF/jasper/spring_report2.jasper");
                // 报表数据源
                jrDataSource = new ChartDataSource(listMap,-1);
            }else{
                 path = request.getSession().getServletContext().getRealPath("/WEB-INF/jasper/spring_report.jasper");
                jrDataSource = new JRBeanCollectionDataSource(list);

            }

            File file = new File(path);
            JasperHelper.showHtml(file.getPath(),request,response,new HashMap(),jrDataSource);
            return null;
        } catch (JRException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 动态指定报表模板url
        model.addAttribute("url", "/WEB-INF/jasper/spring_report.jasper");
        model.addAttribute("format", "html"); // 报表格式
        model.addAttribute("requestObject", request);
        return "reportView"; // 对应jasper-views.xml中的bean id
    }
}
