package com.rays.pro4.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.TradeHistoryBean;
import com.rays.pro4.Model.TradeHistoryModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;




@WebServlet(name = "TradeHistoryCtl", urlPatterns = { "/ctl/TradeHistoryCtl" })

public class TradeHistoryCtl extends BaseCtl {
	
			
			@Override
			protected void preload(HttpServletRequest request) {

				HashMap map = new HashMap();
				map.put("All", "All");
				map.put("Sell", "Sell");
				map.put("Buy", "Buy");
				
				
				request.setAttribute("map", map);
			}
			
			@Override
			protected boolean validate(HttpServletRequest request) {
				System.out.println("Trade History ctl Validate");

				boolean pass = true;

				if (DataValidator.isNull(request.getParameter("userId"))) {
					request.setAttribute("userId", PropertyReader.getValue("error.require", "userId"));
					pass = false;
				}
				if (DataValidator.isNull(request.getParameter("startDate"))) {
					request.setAttribute("startDate", PropertyReader.getValue("error.require", "startDate"));
					pass = false;
				
				}
				if (DataValidator.isNull(request.getParameter("endDate"))) {
					request.setAttribute("endDate", PropertyReader.getValue("error.require", "endDate"));
					pass = false;
				}
				if (DataValidator.isNull(request.getParameter("transactionType"))) {
					request.setAttribute("transactionType", PropertyReader.getValue("error.require", "transactionType"));
					pass = false;
				}
				
				System.out.println("Validate ended and value of pass is = "+pass);

				return pass;

			}
			@Override
			protected BaseBean populateBean(HttpServletRequest request) {
				
				TradeHistoryBean bean = new TradeHistoryBean();
				
				bean.setId(DataUtility.getLong(request.getParameter("id")));

				bean.setUserId(DataUtility.getString(request.getParameter("userId")));

				bean.setStartDate(DataUtility.getDate(request.getParameter("startDate")));

				System.out.println("dateeesss" + request.getParameter("startDate"));

				bean.setEndDate(DataUtility.getDate(request.getParameter("endDate")));
				
				System.out.println("date" + bean.getEndDate());

				bean.setTransactionType(DataUtility.getString(request.getParameter("transactionType")));
				
				return bean;
			}
			
			@Override
			protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				
				String op = DataUtility.getString(req.getParameter("operation"));

				TradeHistoryModel model = new TradeHistoryModel();

				long id = DataUtility.getLong(req.getParameter("id"));
				System.out.println("id = "+id);
				System.out.println("Transaction History Edit Id >= " + id);

				if (id != 0 && id > 0) {

					System.out.println("in id > 0  condition " + id);
					
					TradeHistoryBean bean;

					try {
						bean = model.findByPK(id);
						ServletUtility.setBean(bean, req);
					
					} catch (Exception e) {

						e.printStackTrace();
					}
				}

				ServletUtility.forward(getView(), req, resp);

			}
				//super.doGet(req, resp);
			
			@Override
			protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				
				System.out.println("Trade History ctl Do Post");

				String op = DataUtility.getString(req.getParameter("operation"));

				long id = DataUtility.getLong(req.getParameter("id"));

				System.out.println(">>>><<<<>><<><<><<><>**********" + id   +  op);

				TradeHistoryModel model = new TradeHistoryModel();

				if (OP_CANCEL.equalsIgnoreCase(op)) {

					ServletUtility.redirect(ORSView.TRADEHISTORY_LIST_CTL, req, resp);

				}
				if (OP_RESET.equalsIgnoreCase(op)) {

					ServletUtility.redirect(ORSView.TRADEHISTORY_CTL, req, resp);

				}
				if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

					TradeHistoryBean bean = (TradeHistoryBean) populateBean(req);

					if (id > 0) {

						try {
							model.update(bean);
							ServletUtility.setBean(bean, req);
							ServletUtility.setSuccessMessage("Stock is successfully Updated", req);
				               System.out.println("update chal rhi h");
				      

							ServletUtility.forward(getView(), req, resp);
						} catch (Exception e) {
							System.out.println("stock not update");
							e.printStackTrace();
						}

					} else {

						try {
							long pk = model.add(bean);
							ServletUtility.setSuccessMessage("Stock is successfully Added", req);

							bean.setId(pk);
							ServletUtility.forward(getView(), req, resp);
						} catch (Exception e) {
							System.out.println("stock not added");
							e.printStackTrace();
						}

					}

				}
			}
				//super.doPost(req, resp);
				
			
			

			@Override
			protected String getView() {
				// TODO Auto-generated method stub
				return ORSView.TRADEHISTORY_VIEW;
			}
			
			

		}


	

	
	