package com.example.demo.controllers;

import com.example.demo.models.Cheque;
import com.example.demo.repo.ChequeRepository;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private ChequeRepository chequeRepository;

    @GetMapping()
    public String bookMain() {
        return "home";
    }

    @GetMapping("test")
    public ResponseEntity<Resource> test() throws ParseException {
        String filename = "temp/test.xls";

        Map<String, Integer> chequeData = new TreeMap<>(new HashMap<>());

        for (Cheque cheque : chequeRepository.findAll()) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(cheque.date);
            if (chequeData.containsKey(date)) {
                chequeData.compute(date, (k, v) -> v + cheque.amount);
            } else {
                chequeData.put(date, cheque.amount);
            }
        }

        if (chequeData.size() > 0) {
            Date firstDate = new SimpleDateFormat("yyyy-MM-dd").parse(chequeData.keySet().stream().toArray()[0].toString());
            Date lastDate = new SimpleDateFormat("yyyy-MM-dd").parse(chequeData.keySet().stream().toArray()[chequeData.size() - 1].toString());

            while (firstDate.before(lastDate)) {
                Calendar c = Calendar.getInstance();
                c.setTime(firstDate);
                c.add(Calendar.DATE, 1);
                firstDate = c.getTime();
                String date = new SimpleDateFormat("yyyy-MM-dd").format(firstDate);
                if (!chequeData.containsKey(date)) {
                    chequeData.put(date, 0);
                }
            }
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Отчёт по дням");

        HSSFRow rowhead = sheet.createRow((short) 0);
        rowhead.createCell(0).setCellValue("Дата");
        rowhead.createCell(0).setCellValue("Сумма");

        int rowNumber = 0;

        for (Map.Entry<String, Integer> entry : chequeData.entrySet()) {
            rowNumber += 1;
            HSSFRow row = sheet.createRow((short) rowNumber);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
        }

        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }

        Path path = Paths.get("temp/test.xls");
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
