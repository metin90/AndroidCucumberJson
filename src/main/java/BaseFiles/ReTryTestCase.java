package BaseFiles;


import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class ReTryTestCase implements IRetryAnalyzer {

    private int count = 0;

    public ReTryTestCase() {
    }

    public synchronized boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if (this.count < TestBase.RETRY) {
                ++this.count;
                result.setStatus(2);
                result.setParameters(new String[]{"reTry"});

                return true;
            }

            result.setStatus(2);
        } else {
            result.setStatus(1);
        }

        return false;
    }
}
