package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextHelper {
    private static final Path dataPath = Paths.get("data_area.txt");
    private static final String INDEX_PATH = "index_area.txt";
    private static final String DATA_PATH = "data_area";
    private static final String FILE_EXTENSION = ".txt";

    private TextHelper() {}

    public static String getTestText() throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get("text_data.txt"))) {
            return lines
                    .collect(Collectors.joining(System.lineSeparator()));
        }
    }

    public static String getBlockContent(int blockNumber) throws IOException {
        Path path = Paths.get(DATA_PATH + blockNumber + FILE_EXTENSION);

        if (!Files.exists(path)) {
            Files.createFile(path);
            return "";
        }

        try (Stream<String> lines = Files.lines(path)) {
            return lines
                    .filter(x -> !x.isEmpty())
                    .collect(Collectors.joining(System.lineSeparator()));
        }
    }

    public static int getBlockNumber(int hash) throws IOException {
        try (FileInputStream fis = new FileInputStream(INDEX_PATH);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {
            String line;
            int blockNumber = 0;

            while ((line = br.readLine()) != null) {
                if (Integer.parseInt(line.split(" ")[0]) < hash) {
                    blockNumber++;
                }
            }
            return blockNumber;
        }
    }

    public static void writeBlock(int blockNumber, String[] content) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(DATA_PATH + blockNumber + FILE_EXTENSION);
             OutputStreamWriter osw = new OutputStreamWriter(fos);
             BufferedWriter bw = new BufferedWriter(osw)) {
            for (String str : content) {
                bw.write(str);
                bw.write(System.lineSeparator());
            }
        }
    }

    public static void generateTestData() throws IOException {
        try (FileOutputStream fos = new FileOutputStream("text_data.txt");
             OutputStreamWriter osw = new OutputStreamWriter(fos);
             BufferedWriter bw = new BufferedWriter(osw)) {
            for (int i = 1; i < 10000; i++) {
                bw.write(i + " ");
            }
        }
    }
}
