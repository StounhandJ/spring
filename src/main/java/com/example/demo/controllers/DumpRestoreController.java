package com.example.demo.controllers;

import com.example.demo.service.DumpRestoreService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
@RequestMapping("dump")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class DumpRestoreController {


    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String dumpMain() {
        return "dump";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Resource> getFile() throws IOException, InterruptedException {

        DumpRestoreService.dump("temp/dump.stoun");

        Path path = Paths.get("temp/dump.stoun");
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

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        File convFile = new File(String.format("temp/%d.stoun", System.currentTimeMillis() / 1000L));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();

        DumpRestoreService.restore(convFile.getPath());
        return "redirect:/";
    }
}
