package com.example.demo.service;


import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DumpRestoreService {

    public static void dump(String fileName) throws IOException, InterruptedException {

        String[] config = loadConfig();

        String executeCmd = String.format("pg_dump -h %s -p %s -d %s -U %s -f %s -Fc -v -W", config[0], config[1], config[2], config[3], fileName);

        DumpRestoreService.exec(executeCmd, config[4]);
    }

    public static void restore(String fileName) throws IOException, InterruptedException {

        String[] config = loadConfig();

        String executeCmd = String.format("pg_restore -h %s -p %s -d %s -U %s -W -c %s", config[0], config[1], config[2], config[3], fileName);

        DumpRestoreService.exec(executeCmd, config[4]);
    }

    private static String[] loadConfig() {
        Pattern p = Pattern.compile("//([\\d.]+):(\\d+)/(\\w+)", Pattern.MULTILINE);
        Matcher m = p.matcher(ConfigLoader.load("spring.datasource.url"));
        m.find();
        String host = m.group(1);
        String port = m.group(2);
        String dbname = m.group(3);

        String dbUser = ConfigLoader.load("spring.datasource.username");
        String dbPass = ConfigLoader.load("spring.datasource.password");

        return new String[]{
                host,
                port,
                dbname,
                dbUser,
                dbPass
        };
    }


    private static void exec(String command, String password) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);

        OutputStream stdin = process.getOutputStream();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

        writer.write(password);
        writer.newLine();
        writer.flush();
        writer.close();
        process.waitFor();

    }


}