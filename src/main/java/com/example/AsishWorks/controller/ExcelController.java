package com.example.AsishWorks.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.example.AsishWorks.service.ExcelService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@RequestMapping("excel")
public class ExcelController {

		
		//     IMPORT DATA FROM EXCEL
//		@GetMapping("/import")
//		public String importFromExcel() throws IOException
//		{
//			ExcelService excelService=new ExcelService();
//			List<ClientDetail> clientList = excelService.importClientDetail();
//			for(ClientDetail client : clientList)
//			    System.out.println(client);
//			return "import Successfully";
//		}

	@GetMapping("/drawImage")
	public void drawImage(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=DrawImage.xlsx";
		response.setHeader(headerKey, headerValue);
		ExcelService excelExporter=new ExcelService();
		excelExporter.drawImage(response);
	}

	}
