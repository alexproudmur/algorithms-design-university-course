package com.company;

import javax.xml.soap.Text;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            TextHelper.generateTestData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
