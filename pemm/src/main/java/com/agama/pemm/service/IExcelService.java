package com.agama.pemm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.agama.pemm.domain.ThStatus;
import com.agama.pemm.domain.UpsStatus;


public interface IExcelService {
	public void exportUpsStatus(String title, String[] headers,List<UpsStatus> dataset,String creator, HttpServletRequest request,HttpServletResponse response);

	public void exportThStatus(String title, String[] headers,
			List<ThStatus> list, String string, HttpServletRequest request,
			HttpServletResponse response);

}
