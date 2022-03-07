import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class dynamic {

    public static void main(String[] args){

        // you can also specify version of the chromebrowser you want to use
//        WebDriverManager.chromedriver().browserVersion("95").setup();

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://demo.opencart.com/admin/index.php");
        driver.manage().window().maximize();

        // Login process
        WebElement username = driver.findElement(By.id("input-username"));
        username.clear();
        username.sendKeys("demo");

        WebElement password = driver.findElement(By.id("input-password"));
        password.clear();
        password.sendKeys("demo");

        // going to Orders
        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
        driver.findElement(By.xpath("//a[normalize-space()='Sales']")).click();
        driver.findElement(By.xpath("//a[normalize-space()='Orders']")).click();

        // find total number of pages
        String total_str = driver.findElement(By.xpath("//div[@class='col-sm-6 text-right']")).getText();
        System.out.println(total_str); // Showing 1 to 20 of 12035 (602 Pages)
            // using string.substring( from, to) to get only integer value
        int totalNoPages = Integer.valueOf(total_str.substring(total_str.indexOf("(") + 1, total_str.indexOf("Pages") - 1));
        System.out.println(totalNoPages);   // (int) 602

        // looping through pages to get data
            // i < totalNoPage; should be this but 602 pages will take forever to test
        for (int i = 1; i <= 5; i++){
            WebElement active_page = driver.findElement(By.xpath("//ul[@class='pagination']//li//span"));
            System.out.println("Active Page: " + active_page.getText());

            // !! notice its findElements not findElement since we are looking for multiple entities.
            int active_page_rows = driver.findElements(By.xpath("//table[@class='table table-bordered table-hover']//tbody//tr")).size();
            System.out.println("Number of rows: " + active_page_rows);

            //Reading datas
            // NOTE: in xpath nothing starts from 0. if there are 2 table elements it means td[1] and td[2].
            for (int k = 1; k <= active_page_rows; k++){
                String order_status = driver.findElement(By.xpath("//table[@class='table table-bordered table-hover']//tbody//tr["+k+"]//td[4]")).getText();
                if (order_status.equals("Pending")){        // NOTE: for strings (not primitive types) you have to use .equals() method
                    String orderID = driver.findElement(By.xpath("//table[@class='table table-bordered table-hover']//tbody//tr["+k+"]//td[2]")).getText();
                    String customerName = driver.findElement(By.xpath("//table[@class='table table-bordered table-hover']//tbody//tr["+k+"]//td[3]")).getText();
                    System.out.println(orderID + "\t" + customerName + "\t" + order_status);
                }
            }

//            String pageno = Integer.toString(i + 1);
//            driver.findElement(By.xpath("//a[normalize-space()="+pageno+"]")).click();

            if (totalNoPages != Integer.valueOf(active_page.getText())){
                driver.findElement(By.xpath("//a[normalize-space()='>']")).click();
            } else {
                break;
            }
        }
    }

}
