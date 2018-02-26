package com.ztf.core.ireport.controler;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class CustomReportView extends JasperReportsMultiFormatView  {
	private JasperReport report;

	public CustomReportView() {
		super();
	}

	protected JasperPrint fillReport(Map<String, Object> model) throws Exception {
		if (model.containsKey("url")) {
			setUrl(String.valueOf(model.get("url")));
			this.report = loadReport();

		}
		return super.fillReport(model);
	}
	@Override
	protected void renderReport(JasperPrint populatedReport,
								Map<String, Object> model, HttpServletResponse response)
			throws Exception {
		if (model.containsKey("requestObject")) {
			HttpServletRequest request = (HttpServletRequest) model.get("requestObject");
			request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, populatedReport);
		}
		super.renderReport(populatedReport, model, response);
	}
	protected JasperReport getReport() {
		return this.report;
	}
}
