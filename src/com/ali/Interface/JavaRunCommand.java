package com.ali.Interface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class JavaRunCommand {
   // private static final String CMD = 
     //   "netsh int ip set address name = \"Local Area Connection\" source = static addr = 192.168.222.3 mask = 255.255.255.0";
    public void run(String CMD) {

        try {
            // Run "netsh" Windows command
            Process process = Runtime.getRuntime().exec(CMD);

            // Get input streams
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // Read command standard output
            String s;
            //System.out.println("Standard output: ");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // Read command errors
            //System.out.println("Standard error: ");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
    
    public void run(String CMD,PrintWriter bw)
    {
        try {
            // Run "netsh" Windows command
            Process process = Runtime.getRuntime().exec(CMD);

            // Get input streams
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // Read command standard output
            String s;
            //System.out.println("Standard output: ");
            while ((s = stdInput.readLine()) != null) {
                bw.write(s);
                bw.write("\n");
            }

            // Read command errors
            //System.out.println("Standard error: ");
            while ((s = stdError.readLine()) != null) {
                bw.write(s+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    	
    }
}