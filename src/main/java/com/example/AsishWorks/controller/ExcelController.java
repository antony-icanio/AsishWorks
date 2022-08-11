package com.example.AsishWorks.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import com.example.AsishWorks.service.ExcelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


// Links

//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

//https://www.javatpoint.com/apache-poi-cell-multiple-styles

//https://simplesolution.dev/java-code-examples/apache-poi-vertical-horizontal-excel-cell-alignment/

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