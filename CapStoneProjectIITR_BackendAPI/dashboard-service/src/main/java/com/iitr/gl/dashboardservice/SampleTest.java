package com.iitr.gl.dashboardservice;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;


public class SampleTest {

    public static void main(String args[]){

        //SampleTest.execPHP();
        String guid = "czICuCoITLyVfdDdX8WIgA==";
        byte[] decoded = Base64.getDecoder().decode(guid);
        System.out.println(toHex(decoded).toLowerCase());
    }

    public static final String toHex(byte[] data) {
        final StringBuffer sb = new StringBuffer(data.length * 2);
        for (int i = 0; i < data.length; i++) {
            sb.append(DIGITS[(data[i] >>> 4) & 0x0F]);
            sb.append(DIGITS[data[i] & 0x0F]);
        }
        return sb.toString();
    }

    private static final char[] DIGITS
            = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};


    public static void execPHP() {

        try {
            String[] command = {"python", "test.py"};
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);
            Process exec = pb.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            String text = null;
            while ((text = br.readLine()) != null) {
                System.out.println(text);
            }

            System.out.println("Process exited with " + exec.waitFor());
        } catch (IOException | InterruptedException exp) {
            exp.printStackTrace();
        }

    }
}

