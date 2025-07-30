package com.rays.pro4.controller;

import java.io.IOException; 
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.TransportationBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;

import com.rays.pro4.Model.TransportationModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

//TODO: Auto-generated Javadoc

@WebServlet(name = "TransportationCtl", urlPatterns = { "/ctl/TransportationCtl" })
public class TransportationCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(TransportationCtl.class);


	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");
		log.debug("TransportationCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("discription"))) {
			request.setAttribute("discription", PropertyReader.getValue("error.require", "Discription"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("discription"))) {
			request.setAttribute("discription", "Discription contains alphabet only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mode"))) {
			request.setAttribute("mode", PropertyReader.getValue("error.require", "Mode"));
			pass = false;
		} 
		
		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "Date"));
			pass = false;
		} /*
			 * else if (!DataValidator.isDate(request.getParameter("date"))) {
			 * request.setAttribute("date", PropertyReader.getValue("error.date", "Date"));
			 * pass = false; }
			 */
		
	

		if (DataValidator.isNull(request.getParameter("cost"))) {
			request.setAttribute("cost", PropertyReader.getValue("error.require", "Cost"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("cost"))) {
			request.setAttribute("cost", PropertyReader.getValue("error.integer", "Cost"));
			pass = false;
		}


		log.debug("TransportationCtl Method validate Ended");

		return pass;
	}
	
	@Override
	protected void preload(HttpServletRequest request) {
		
		
			Map<Integer, String> map = new HashMap();

			map.put(1, "Airway");
			map.put(2, "Railway");
			map.put(3, "Road");
			
			request.setAttribute("cate", map);
		
		
		
	}

	
	

	
	protected BaseBean populateBean(HttpServletRequest request) {
		System.out.println(" uctl Base bean P bean");
		log.debug("TransportationCtl Method populatebean Started");

		TransportationBean bean = new TransportationBean();

		bean.setId(DataUtility.getLong(request.getParameter("id"))); 
		
		bean.setDiscription(DataUtility.getString(request.getParameter("discription")));

		bean.setMode(DataUtility.getInt(request.getParameter("mode"))); 
		
		System.out.println(request.getParameter("date"));
		
		bean.setDate(DataUtility.getDate(request.getParameter("date")));

		bean.setCost(DataUtility.getLong(request.getParameter("cost")));

	

		log.debug("TransportationCtl Method populatebean Ended");

		return bean;

	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("TransportationCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		TransportationModel model = new TransportationModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println("Transportation Edit Id >= " + id);
		if (id != 0 && id > 0) {
			System.out.println("in id > 0  condition");
			TransportationBean bean;
			try {
				bean = model.findByPK(id);
				System.out.println(bean);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("TransportationCtl Method doGet Ended");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("uctl Do Post");

		log.debug("TransportationCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));


		TransportationModel model = new TransportationModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			TransportationBean bean = (TransportationBean) populateBean(request);
			System.out.println(" U ctl DoPost 11111111");

			try {
				if (id > 0) {

					// System.out.println("hi i am in dopost update");
					
					model.update(bean);
					ServletUtility.setBean(bean, request);
					System.out.println(" U ctl DoPost 222222");
					ServletUtility.setSuccessMessage("Transportation is successfully Updated", request);

				} else {
					System.out.println(" U ctl DoPost 33333");
					long pk = model.add(bean);
					
					bean = model.findByPK(pk);
					
					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("Transportation is successfully Added", request);
			
				}
				

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				System.out.println(" U ctl D post 4444444");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println(" U ctl D p 5555555");

			TransportationBean bean = (TransportationBean) populateBean(request);
			try {
				model.delete(bean);
				System.out.println(" u ctl D Post  6666666");
				ServletUtility.redirect(ORSView.TRANSPORTATION_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.TRANSPORTATION_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("TransportationCtl Method doPostEnded");
	}

	@Override
	protected String getView() {
		return ORSView.TRANSPORTATION_VIEW;
	}

}