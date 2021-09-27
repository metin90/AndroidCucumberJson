package GeneralFiles;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class EncryptUtils {
    public static final String DEFAULT_ENCODING = "UTF-8";
    static String key = "nokia test automation key";

    public static String base64encode(String text) {
        try {
            return Base64.getEncoder().encodeToString(text.getBytes(DEFAULT_ENCODING));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }//base64encode

    public static String base64decode(String text) {
        try {
            return new String(Base64.getDecoder().decode(text), DEFAULT_ENCODING);
        } catch (IOException e) {
            return null;
        }
    }//base64decode

//    public static void main(String[] args){
//        String txt = "";
//        txt = xorMessage(txt, key);
//        String encoded = base64encode(txt);
//        System.out.println(" is encoded to: " + encoded);
//        txt = base64decode(encoded);
//        System.out.print(xorMessage(txt, key));
//    }

    public static String getRealData(String data){
        String txt=base64decode(data);
        data=xorMessage(txt, key);
        return data;
    }

    public static String xorMessage(String message, String key) {
        try {
            if (message == null || key == null) return null;

            char[] keys = key.toCharArray();
            char[] mesg = message.toCharArray();

            int ml = mesg.length;
            int kl = keys.length;
            char[] newmsg = new char[ml];

            for (int i = 0; i < ml; i++) {
                newmsg[i] = (char)(mesg[i] ^ keys[i % kl]);
            }//for i

            return new String(newmsg);
        } catch (Exception e) {
            return null;
        }
    }//xorMessage
}//class