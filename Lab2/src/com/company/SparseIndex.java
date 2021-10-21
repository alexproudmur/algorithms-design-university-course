package com.company;


import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class SparseIndex {
    public static final int VOLUME = 2000;
    private static boolean flag = false;
    private static int count = 0;

    public static void main(String[] args) {
        SparseIndex sparseIndex = new SparseIndex();

        String str = "2000";
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
        int k = (int) Math.floor(Math.log(entries.length) / Math.log(2));
        int i = (int) Math.floor(Math.pow(2, k));
        int Ki = Integer.parseInt(entries[i-1].split(" ")[0]);



        if (hash > Ki && entries.length > Math.pow(2, k)) {
            int l = (int) Math.floor(Math.log(entries.length - Math.pow(2, k) + 1) / Math.log(2));
            i = entries.length + 1 - (int) Math.floor(Math.pow(2, l));
            int delta = (int) Math.floor(Math.pow(2, l-1));
            return searchRecursive(hash, entries, i, delta);
        } else if (hash < Ki) {
            int delta = (int) Math.floor(Math.pow(2, k-1));
            return searchRecursive(hash, entries, i, delta);
        }
        return true;
        //TODO extra block
    }

    private static boolean searchRecursive(int hash, String[] entries, int i, int delta) {
        int Ki = Integer.parseInt(entries[i-1].split(" ")[0]);
        System.out.println(i);
        if (delta == 0) {
            count++;
        }
        if (count == entries.length) {
            count = 0;
            return false;
        }

        if (hash > Ki) {
            i = i + (delta / 2 + 1);
            delta = delta / 2;
            return searchRecursive(hash, entries, i, delta);
        } else if (hash < Ki) {
            i = i - (delta / 2 + 1);
            delta = delta / 2;
            return searchRecursive(hash, entries, i, delta);
        }
        return true;

        //TODO extra block
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
