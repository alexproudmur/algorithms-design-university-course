package com.company;


import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class SparseIndex {
    public static final int VOLUME = 2000;
    private static boolean flag = false;
    private static int count = 0;

    public static void main(String[] args) {
        SparseIndex sparseIndex = new SparseIndex();

        String str = "2001";
        try {
            System.out.println(SparseIndex.search(str));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean addRecord(String data) {
        int hash = getHash(data);

        try {
            int blockNumber = TextHelper.getBlockNumber(hash);
            String blockContent = TextHelper.getBlockContent(blockNumber);
            int i = 0;

            if (blockContent.isEmpty()) {
                String[] newContent = new String[1];
                newContent[0] = hash + " " + data;
                TextHelper.writeBlock(blockNumber, newContent);
                return true;
            }

            Set<Entry> set = getEntrySet(blockContent.split(System.lineSeparator()));
            System.out.println(set.size());
            if (set.size() >= 2000) {
                blockNumber = 9;
                blockContent = TextHelper.getBlockContent(blockNumber);
                set = getEntrySet(blockContent.split(System.lineSeparator()));
                set.add(new Entry(hash, data));
                String[] content = new String[set.size()];

                for (Entry entry : set) {
                    content[i++] = entry.toString();
                }
                TextHelper.writeBlock(blockNumber, content);
                return true;
            }

            set.add(new Entry(hash, data));
            String[] content = new String[VOLUME];

            for (Entry entry : set) {
                content[i++] = entry.toString();
            }
            for (int j = i; j < content.length; j++) {
                content[j] = System.lineSeparator();
            }
            TextHelper.writeBlock(blockNumber, content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean search(String data) throws IOException {
        int hash = getHash(data);
        int blockNumber = TextHelper.getBlockNumber(hash);
        String[] entries = TextHelper.getBlockContent(blockNumber).split(System.lineSeparator());
        int[] hashes = new int[entries.length];
        int i = 0;

        for (String str : entries) {
            String[] pair = str.split(" ");
            if (pair.length == 2) {
                int hashValue = Integer.parseInt(pair[0]);
                hashes[i] = hashValue;
                i++;
            } else {
                throw new IllegalArgumentException("Not a pair in the file");
            }
        }
        System.out.println(Arrays.toString(hashes));
        boolean result = SharrSearch.search(hash, hashes);
        if (result) {
            return true;
        }

        entries = TextHelper.getBlockContent(9).split(System.lineSeparator());
        hashes = new int[entries.length];
        i = 0;

        for (String str : entries) {
            String[] pair = str.split(" ");
            if (pair.length == 2) {
                int hashValue = Integer.parseInt(pair[0]);
                hashes[i] = hashValue;
                i++;
            } else {
                throw new IllegalArgumentException("Not a pair in the file");
            }
        }
        return SharrSearch.search(hash, hashes);
    }



    private static int getHash(String data) {
        return data.hashCode();
    }

    private static Set<Entry> getEntrySet(String[] content) {
        Set<Entry> set = new TreeSet<>();
        for (String str : content) {
            String[] pair = str.split(" ");
            if (pair.length == 2)
                set.add(new Entry(Integer.parseInt(pair[0]), pair[1]));
        }
        return set;
    }

    private static class Entry implements Comparable<Entry> {
        int hash;
        String data;

        public Entry(int hash, String data) {
            this.hash = hash;
            this.data = data;
        }

        @Override
        public String toString() {
            return hash + " " + data;
        }

        @Override
        public int compareTo(Entry o) {
            return hash - o.hash;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry entry = (Entry) o;
            return hash == entry.hash;
        }

        @Override
        public int hashCode() {
            return Objects.hash(hash, data);
        }
    }

}
