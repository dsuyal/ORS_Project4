package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.StockBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.StockModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;
	

	

	@WebServlet(name = "StockListCtl", urlPatterns = { "/ctl/StockListCtl" })
	
	public class StocklListCtl extends BaseCtl {
	
		@Override
		protected void preload(HttpServletRequest request) {
			StockModel model = new StockModel();

			HashMap map = new HashMap();
			map.put("Market", "Market");
			map.put("Limit", "Limit");
			

			request.setAttribute("prolist", map);
		}

		@Override
		protected BaseBean populateBean(HttpServletRequest request) {         

			StockBean bean = new StockBean();

			bean.setId(DataUtility.getLong(request.getParameter("id")));
			bean.setPurchasePrice(DataUtility.getLong(request.getParameter("purchasePrice")));
			bean.setPurchaseDate(DataUtility.getDate(request.getParameter("purchaseDate")));

			System.out.println("quantity ===== > " + request.getParameter("Quantity"));

			bean.setQuantity(DataUtility.getInt(request.getParameter("Quantity")));

			System.out.println("quantity bean ===== > " + bean.getQuantity());

			bean.setOrderType(DataUtility.getString(request.getParameter("orderType")));

			return bean;
		}

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			List list = null;
			List nextList = null;

			int pageNo = 1;
			int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
			StockBean bean = (StockBean) populateBean(request);
			String op = DataUtility.getString(request.getParameter("operation"));
			System.out.println(">>>>>>>>>>>>>>>helooo" + bean.getId());

			StockModel model = new StockModel();

			try {
				list = model.search(bean, pageNo, pageSize);
				System.out.println("list" + list);

				nextList = model.search(bean, pageNo + 1, pageSize);

				request.setAttribute("nextlist", nextList.size());

				ServletUtility.setList(list, request);
				ServletUtility.setPageNo(pageNo, request);
				ServletUtility.setPageSize(pageSize, request);
				// ServletUtility.setBean(bean, request);

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}

			ServletUtility.forward(getView(), request, response);
		}

		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			System.out.println("StockListCtl doPost Start");

			List list;
			List nextList = null;

			int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
			int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
			pageNo = (pageNo == 0) ? 1 : pageNo;
			pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

			String op = DataUtility.getString(request.getParameter("operation"));
			StockBean bean = (StockBean) populateBean(request);

			String[] ids = request.getParameterValues("ids");
			StockModel model = new StockModel();

			if (OP_SEARCH.equalsIgnoreCase(op)) {
				pageNo = 1;
			} else if (OP_NEXT.equalsIgnoreCase(op)) {
				pageNo++;
			} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
				pageNo--;

			} else if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.STOCK_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.STOCK_LIST_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					StockBean deletebean = new StockBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						try {
							model.delete(deletebean);
						} catch (ApplicationException e) {

							ServletUtility.handleException(e, request, response);
							return;
						}

						ServletUtility.setSuccessMessage("Stock is Deleted Successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
			try {

				list = model.search(bean, pageNo, pageSize);

				nextList = model.search(bean, pageNo + 1, pageSize);

				request.setAttribute("nextlist", nextList.size());

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}
			if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setBean(bean, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.forward(getView(), request, response);

		}

		@Override
		protected String getView() {
			return ORSView.STOCK_LIST_VIEW;
		}

	}

