package BaseFiles;

public class Environment {

    public static BaseEnvironment prepareBrowser() {
        String _browser = TestBase.PLATFORMNAME.toLowerCase();
        byte b_Value = -5;

        if(_browser.equalsIgnoreCase("android")){
            b_Value=0;
        }else if (_browser.equalsIgnoreCase("ios")){
            b_Value=1;
        }else{
            b_Value=6;
        }

        switch(b_Value) {
            case 0:
                return new Android();
            case 1:
                return new IOS();
            case 2:
                // return new IE();
            case 3:
            default:
                return new Android();
        }


    }
}
