package GeneralFiles;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;


public class FileMethods {

    public static ArrayList readFileNamesInZip(String zipFilePath) {
        ArrayList<String> list=new ArrayList<>();
        try {
            java.util.zip.ZipFile zipFile = new java.util.zip.ZipFile(zipFilePath);

            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();
                list.add(name);
//                long compressedSize = entry.getCompressedSize();
//                long normalSize = entry.getSize();
//                String type = entry.isDirectory() ? "DIR" : "FILE";
//                System.out.println(name);
//                System.out.format("\t %s - %d - %d\n", type, compressedSize, normalSize);
            }

            zipFile.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return list;
    }

    public static void unzip(String zipFilePath,String destination,String password){

        try {
            ZipFile zipFile = new ZipFile(zipFilePath);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password.toCharArray());
            }
            zipFile.extractAll(destination);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    public static String readATxtFile(String txtFilePath){
        String content="";
        try {
            File myObj = new File(txtFilePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                content+=data+"\n";
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return content;
    }


}
