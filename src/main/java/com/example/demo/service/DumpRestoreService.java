package com.example.demo.service;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.data.repository.CrudRepository;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import au.com.bytecode.opencsv.CSVWriter;

public class DumpRestoreService {

    private final List<CrudRepository> repositories = new ArrayList<>();
    private final List<Class> models = new ArrayList<>();

    public void addRepository(CrudRepository cr) {
        this.repositories.add(cr);

        var objects = cr.findAll();

        if (objects.iterator().hasNext()) {
            this.models.add(objects.iterator().next().getClass());
        } else {
            this.models.add(Class.class);
        }
    }

    public void dump(String fileName) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException {
        List<String> files = new ArrayList<>();

        for (int i = 0; i < repositories.size(); i++) {

            var repository = repositories.get(i);
            var model = models.get(i);
            Method cvsMethod = model.getMethod("cvs");
            String srcFile = "temp/" + model.getName();


            writeModelsToCvs(repository.findAll(), cvsMethod, srcFile);
            files.add(srcFile);

        }

        archiveEntry(files, fileName);
    }

    private void writeModelsToCvs(Iterable<Object> models, Method cvsMethod, String srcFile) throws IOException, InvocationTargetException, IllegalAccessException {
        CSVWriter writer = new CSVWriter(new FileWriter(srcFile));
        for (Object modelData : models) {
            String[] data = (String[]) cvsMethod.invoke(modelData);

            writer.writeNext(data);
        }
        writer.close();
    }

    public void restore(File file) throws NoSuchMethodException {
        for (int i = 0; i < repositories.size(); i++) {
            var repository = repositories.get(i);
            var model = models.get(i);
            if (model == Class.class) {
                continue;
            }
            Method cvsToModelMethod = model.getMethod("cvsToModel", List.class);

            try (var zf = new ZipFile(file)) {
                Enumeration<? extends ZipEntry> zipEntries = zf.entries();
                zipEntries.asIterator().forEachRemaining(entry -> {
                    if (entry.getName().equals(model.getName())) {

                        Reader fileReader = zipEntryToReader(zf, entry);

                        try (CSVReader reader = new CSVReader(fileReader)) {

                            List<Object> models = cvsToModels(reader, cvsToModelMethod);

                            repository.deleteAll();
                            repository.saveAll(models);
                        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            } catch (IOException | SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    private Reader zipEntryToReader(ZipFile zf, ZipEntry entry) {
        Reader fileReader;
        try {
            fileReader = new InputStreamReader(zf.getInputStream(entry));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileReader;
    }

    private List<Object> cvsToModels(CSVReader reader, Method cvsToModelMethod) throws IOException, InvocationTargetException, IllegalAccessException {
        List<Object> models = new ArrayList<>();

        String[] lineInArray;
        while ((lineInArray = reader.readNext()) != null) {
            models.add(cvsToModelMethod.invoke(null, Arrays.asList(lineInArray)));
        }

        return models;
    }

    private void archiveEntry(List<String> files, String fileName) throws IOException {

        File f = new File("temp/" + fileName);

        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(f));

        for (String srcFile : files) {
            writingFileToArchive(zipOut, srcFile);
        }

        zipOut.close();
    }

    private void writingFileToArchive(ZipOutputStream zipOut, String srcFile) throws IOException {
        File fileToZip = new File(srcFile);
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        zipOut.putNextEntry(zipEntry);

        byte[] bytes = new byte[8168];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}