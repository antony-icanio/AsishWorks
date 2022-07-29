package com.example.AsishWorks.service;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;


@Service
public class ExcelService {
    private XSSFWorkbook workBook;
    private XSSFSheet sheet;

    private XSSFSheet sheet1;


    public ExcelService() {
        workBook = new XSSFWorkbook();
    }


    //  CREATE NEW CELL AND PUT VALUE INTO CELL
    public void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }


    //  HEADER LINES WRITING FOR CANDIDATE
    public void writeHeaderLine() {
        sheet = workBook.createSheet("CandidateDetail");
        Row row = sheet.createRow(0);
        CellStyle style = workBook.createCellStyle();
        XSSFFont font = workBook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        createCell(row, 0, "Candidate Detail", style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
        font.setFontHeightInPoints((short) (10));

        row = sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

      CellStyle style1 =workBook.createCellStyle();
        style1.setFillForegroundColor(IndexedColors.DARK_RED.getIndex());
        style1.setFillPattern(FillPatternType.DIAMONDS);

        createCell(row, 0, "FirstName", style1);
        createCell(row, 1, "LastName", style1);
        createCell(row, 2, "Applied Job", style1);
        createCell(row, 3, "DOB", style1);
        createCell(row, 4, "Status", style);
        createCell(row, 5, "Skill", style);
        createCell(row, 6, "Company", style);
        createCell(row, 7, "Qualification", style);
        createCell(row, 8, "Email", style);
        createCell(row, 9, "Phone", style);
        createCell(row, 10, "linkedIn", style);
        createCell(row, 11, "Address", style);
    }

    //    BODY DATA LINES WRITING CANDIDATE
//    private void writeDataLines() {
//        int rowCount = 2;
//        CellStyle style = workBook.createCellStyle();
//        XSSFFont font = workBook.createFont();
//        font.setFontHeight(14);
//        font.setColor(IndexedColors.GREEN.index);
//        style.setFont(font);
//
//        for (CandidateDetail candidate : listOfCandidate) {
//            Row row = sheet.createRow(rowCount++);
//            int columnCount = 0;
//            createCell(row, columnCount++, candidate.getFirstName(), style);
//            createCell(row, columnCount++, candidate.getLastName(), style);
//            createCell(row, columnCount++, candidate.getJob(), style);
//            createCell(row, columnCount++, candidate.getDob().toString(), style);
//            createCell(row, columnCount++, "", style);
//            createCell(row, columnCount++, candidate.getStatus(), style);
//            createCell(row, columnCount++, candidate.getSkill().toString(), style);
//            createCell(row, columnCount++, candidate.getCompany().toString(), style);
//            createCell(row, columnCount++, candidate.getQualification().toString(), style);
//            createCell(row, columnCount++, candidate.getEmail(), style);
//            createCell(row, columnCount++, candidate.getPhone(), style);
//            createCell(row, columnCount++, candidate.getLinkedIn(), style);
//            createCell(row, columnCount, candidate.getAddress().toString(), style);
//        }
//    }


    //  THIS IS MY TRAINING PURPOSE NOT PROJECT PROPERTY


    //   IMPORT DATA FROM EXCEL
//    public List<ClientDetail> importClientDetail() throws IOException {
//        List<ClientDetail> clientList = new ArrayList<>();
//        String filePath = "C:\\Users\\aasis\\Downloads\\Client_Details.xlsx";
//        FileInputStream inputStream;
//        String FName = "";
//        String LName = "";
//        String email = "";
//        Long phone = (long) 0;
//
//        try {
//            inputStream = new FileInputStream(filePath);
//            Workbook workbook = new XSSFWorkbook(inputStream);
//            Sheet firstSheet = workbook.getSheetAt(0);
//            Iterator<Row> rowIterator = firstSheet.iterator();
//            rowIterator.next();
//            rowIterator.next();
//            while (rowIterator.hasNext()) {
//                Row nextRow = rowIterator.next();
//                Iterator<Cell> cellIterator = nextRow.cellIterator();
//                while (cellIterator.hasNext()) {
//                    Cell nextCell = cellIterator.next();
//                    int columnIndex = nextCell.getColumnIndex();
//                    switch (columnIndex) {
//                        case 0:
//                            FName = (String) nextCell.getStringCellValue();
//                            break;
//                        case 1:
//                            LName = (String) nextCell.getStringCellValue();
//                            break;
//                        case 2:
//                            email = (String) nextCell.getStringCellValue();
//                            break;
//                        case 3:
//                            phone = (long) nextCell.getNumericCellValue();
//                            break;
//                    }
//                }
//                clientList.add(new ClientDetail(FName, LName, email, phone));
//            }
//            workbook.close();
//        } catch (FileNotFoundException e) {
//            System.out.println("FileNotFound");
//            e.printStackTrace();
//        }
//        return clientList;
//    }


    public void drawImage(HttpServletResponse response) throws IOException {
        Sheet sheet = workBook.createSheet("Avengers");
        Row row1 = sheet.createRow(0);
        row1.createCell(0).setCellValue("IRON-MAN in spce travel and time travel");
        Row row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue("SPIDER-MAN is student of iron man and he think he is next iron man");

        File file1 = new File("C:\\Users\\aasis\\Documents\\workspace-spring-tool-suite-4-4.13.1.RELEASE\\interviewStatus\\src\\main\\resources\\ironman.png");
        File file2 = new File("C:\\Users\\aasis\\Documents\\workspace-spring-tool-suite-4-4.13.1.RELEASE\\interviewStatus\\src\\main\\resources\\spider.png");
        InputStream inputStream1 = new FileInputStream(file1);
        InputStream inputStream2 = new FileInputStream(file2);

        byte[] inputImageBytes1 = IOUtils.toByteArray(inputStream1);
        byte[] inputImageBytes2 = IOUtils.toByteArray(inputStream2);


        int inputImagePictureID1 = workBook.addPicture(inputImageBytes1, Workbook.PICTURE_TYPE_PNG);
        int inputImagePictureID2 = workBook.addPicture(inputImageBytes2, Workbook.PICTURE_TYPE_PNG);

        XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();


        XSSFClientAnchor ironManAnchor = new XSSFClientAnchor();
        XSSFClientAnchor spiderManAnchor = new XSSFClientAnchor();

        ironManAnchor.setRow1(0);
        ironManAnchor.setRow2(1);
        ironManAnchor.setCol1(1);
        ironManAnchor.setCol2(2);

        spiderManAnchor.setRow1(1);
        spiderManAnchor.setRow2(2);
        spiderManAnchor.setCol1(1);
        spiderManAnchor.setCol2(2);


        drawing.createPicture(ironManAnchor, inputImagePictureID1);
        drawing.createPicture(spiderManAnchor, inputImagePictureID2);

        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        ServletOutputStream servletOutPutStream = response.getOutputStream();
        workBook.write(servletOutPutStream);
        workBook.close();
        servletOutPutStream.close();
    }



//https://www.javatpoint.com/apache-poi-cell-multiple-styles
//https://simplesolution.dev/java-code-examples/apache-poi-vertical-horizontal-excel-cell-alignment/
}