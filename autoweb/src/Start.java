import org.openqa.selenium.*;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.Set;

public class Start {

    private static WebDriver bro;
    private static JavascriptExecutor broJs;
    private static final String START_TIME = "2018-01-01";
    //脚本
    private static final String CLICK = "arguments[0].click()";
    private static final String LOGIN_XPATH = "//form[@name = 'loginform']/tbody/tr/td[3]/div";
    private static final String NO_CHANGE_PASS_XPATH = "//td[@align = 'left']/input[2]";
    private static final String WORK_TAB_MANG_XPATH = "//img[@alt = '工单管理']";
    private static final String SEARCH_XPATH = "//tr[@title = '综合查询']/td[1]/img";
    private static final String NETWORK_SEARCH_XPATH = "//tr[@title = '网络层障碍工单查询']/td[3]/img";
    private static final String START_TIME_XPATH = "//input[@id = 'i_vBeginTime']";
    private static final String SEARCH_BUTTON_XPATH = "//input[@name = 'searchbutton']";
    private static final String TIME_LABEL_XPATH = "//input[@id = 'mainSn']";
    private static final String JOIN_BILL_XPATH = "//a[@title = '点击进入查看']";
    private static final String EDIT_BILL_JS = "setTimeout(function(){document.getElementsByTagName('button')[1].click()},100)";
    private static final String EDIT_STEP_JS = "setTimeout(function(){document.getElementsByTagName('button')[2].click()},100)";
    private static final String GO_BACK_JS = "setTimeout(function(){document.getElementsByTagName('button')[4].click()},100)";
    private static final String EDIT_STEP_QUIT_XPACH = "//div[@align = 'center']/input[3]";

    public static void main(String[] args) {
        String url = "http://172.18.11.56/";
        String path = "C:\\Program Files\\Internet Explorer\\IEDriverServer.exe";
        String username = "18904420062";
        System.setProperty("webdriver.ie.driver", path);

        bro = new InternetExplorerDriver();
        broJs = ((JavascriptExecutor) bro);
        login(url, username);
        currentWorker();
        System.out.println("Count :");
        String number = "20180900059944";
        for (int i = 0; i < 3; i++) {
            System.out.println("number :"+i);
            searchNumber(number);
            gotoEditBill();
            gotoEditStep();
            goBack();
        }

    }

    private static void login(String url, String username) {
        //设置隐性等待时间
        //bro.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
        bro.get(url);
        bro.findElement(By.name("userid")).sendKeys(username);
        bro.findElement(By.name("passWord")).click();
        WebElement loginButton = bro.findElement(By.xpath(LOGIN_XPATH));
        broJs.executeScript(CLICK, loginButton);
        sleep(2);
        WebElement offChange = bro.findElement(By.xpath(NO_CHANGE_PASS_XPATH));
        broJs.executeScript(CLICK, offChange);
        sleep(5);
        System.out.println("login , currentPage :" + bro.getCurrentUrl());
    }

    private static void currentWorker() {
        System.out.println("综合查询");
        bro.switchTo().defaultContent();
        bro.switchTo().frame("mainFrame");
        sleep(3);
        bro.switchTo().frame("navigation");
        sleep(3);
        WebElement gongdan = bro.findElement(By.xpath(WORK_TAB_MANG_XPATH));
        broJs.executeScript(CLICK, gongdan);
        sleep(2);
        bro.switchTo().defaultContent();
        bro.switchTo().frame("mainFrame");
        bro.switchTo().frame("left");
        WebElement zonghe = bro.findElement(By.xpath(SEARCH_XPATH));
        broJs.executeScript(CLICK, zonghe);
        sleep(2);
    }

    private static void searchNumber(String id) {
        try {
            System.out.println("网络层障碍工单查询 :" + id);
            bro.switchTo().defaultContent();
            bro.switchTo().frame("mainFrame");
            bro.switchTo().frame("left");
            WebElement wangluoGD = bro.findElement(By.xpath(NETWORK_SEARCH_XPATH));
            broJs.executeScript(CLICK, wangluoGD);
            sleep(2);
            changeFrame();
            WebElement startTime = bro.findElement(By.xpath(START_TIME_XPATH));//定位开始时间
            broJs.executeScript("arguments[0].removeAttribute('readOnly')", startTime);//修改时间属性为可变更
            startTime.clear();
            startTime.sendKeys(START_TIME);
            WebElement sendNumber = bro.findElement(By.xpath(TIME_LABEL_XPATH));//输入时间
            sendNumber.sendKeys(id);
            WebElement search = bro.findElement(By.xpath(SEARCH_BUTTON_XPATH));//查询按钮
            broJs.executeScript(CLICK, search);
            sleep(2);
            WebElement lookOver = bro.findElement(By.xpath(JOIN_BILL_XPATH));//查看工单
            broJs.executeScript(CLICK, lookOver);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void sleep(long timeLong) {
        timeLong = timeLong * 1000;
        try {
            Thread.sleep(timeLong);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void gotoEditBill() {
        try {
            changeFrame();
            sleep(3);
            String beginClickWindow = bro.getWindowHandle();//当前窗口
            //点击进入
            System.out.println("修改工单");
            broJs.executeScript(EDIT_BILL_JS);
            sleep(3);
            bro.switchTo().alert();
            Set<String> handlers = bro.getWindowHandles();
            for (String winHandler : handlers) {
                if (!winHandler.equals(beginClickWindow)) {
                    bro.switchTo().window(winHandler);
                    //编辑工单
                    editBill();

                    WebElement quit = bro.findElement(By.name("saveButton"));
                    broJs.executeScript(CLICK, quit);
                    sleep(2);
                    Alert alert = bro.switchTo().alert();
                    alert.accept();
                }
            }
            bro.switchTo().window(beginClickWindow);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static void gotoEditStep() {
        try {
            changeFrame();
            sleep(3);
            String beginClickWindow = bro.getWindowHandle();
            //点击进入
            System.out.println("修改步骤");
            broJs.executeScript(EDIT_STEP_JS);
            sleep(3);
            bro.switchTo().alert();
            Set<String> handlers = bro.getWindowHandles();
            for (String winHandler : handlers) {
                if (!winHandler.equals(beginClickWindow)) {
                    bro.switchTo().window(winHandler);
                    //quit
                    WebElement quit = bro.findElement(By.xpath(EDIT_STEP_QUIT_XPACH));
                    broJs.executeScript(CLICK, quit);
                    sleep(2);

                }
            }
            bro.switchTo().window(beginClickWindow);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void goBack() {
        changeFrame();
        sleep(3);
        System.out.println("返回");
        broJs.executeScript(GO_BACK_JS);
    }

    private static void editBill() {
        try {
            WebElement location2 = bro.findElement(By.name("waveGuide"));
            location2.clear();
            sleep(1);
            WebElement location3 = bro.findElement(By.name("linecode"));
            location3.clear();
            sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static void changeFrame() {
        bro.switchTo().defaultContent();
        bro.switchTo().frame("mainFrame");
        bro.switchTo().frame("worktable");
        bro.switchTo().frame("workarea");
    }

}
