package com.example.demo.controllers;

import com.example.demo.repo.*;
import com.example.demo.service.DumpRestoreService;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


@Controller
@RequestMapping("dump")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class DumpRestoreController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CancellationRepository cancellationRepository;

    @Autowired
    private ChequeRepository chequeRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntranceRepository entranceRepository;

    @Autowired
    private MedicalPreparationsRepository medicalPreparationsRepository;

    @Autowired
    private PaidTreatmentRepository paidTreatmentRepository;

    @Autowired
    private PaidTreatmentPreparationRepository paidTreatmentPreparationRepository;

    @Autowired
    private ShelvingRepository shelvingRepository;

    @Autowired
    private TypePreparationRepository typePreparationRepository;

    @Autowired
    private TypeTreatmentRepository typeTreatmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @GetMapping()
    public String dumpMain() {
        return "dump";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> getFile() throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        DumpRestoreService dumpRestoreService = getDumpRestoreService();

        dumpRestoreService.dump("dump.stoun");

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
    public String upload(@RequestParam("file") MultipartFile file) throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        File convFile = new File("temp/" + Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();

        DumpRestoreService dumpRestoreService = getDumpRestoreService();

        dumpRestoreService.restore(convFile);
        return "redirect:/";
    }

    private DumpRestoreService getDumpRestoreService() {
        DumpRestoreService dumpRestoreService = new DumpRestoreService();
        dumpRestoreService.addRepository(paidTreatmentPreparationRepository);
        dumpRestoreService.addRepository(entranceRepository);
        dumpRestoreService.addRepository(cancellationRepository);
        dumpRestoreService.addRepository(paidTreatmentRepository);
        dumpRestoreService.addRepository(medicalPreparationsRepository);
        dumpRestoreService.addRepository(chequeRepository);
        dumpRestoreService.addRepository(userRoleRepository);
        dumpRestoreService.addRepository(shelvingRepository);
        dumpRestoreService.addRepository(employeeRepository);
        dumpRestoreService.addRepository(clientRepository);
        dumpRestoreService.addRepository(applicationRepository);
        dumpRestoreService.addRepository(warehouseRepository);
        dumpRestoreService.addRepository(userRepository);
        dumpRestoreService.addRepository(typeTreatmentRepository);
        dumpRestoreService.addRepository(typePreparationRepository);

        return dumpRestoreService;
    }
}
