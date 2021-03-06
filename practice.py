from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
import time

# initial setup.
# all of these should be included
serv = Service("C:\SeleniumDrivers\chromedriver.exe")
op = webdriver.ChromeOptions()
op.add_experimental_option('excludeSwitches', ['enable-logging'])
driver = webdriver.Chrome(service=serv, options=op)


# get method to open up url
driver.get("https://www.techwithtim.net/")
driver.implicitly_wait(10)
print(driver.title)

search = driver.find_element(By.NAME, 's')
driver.implicitly_wait(10)

search.send_keys('test')
search.send_keys(Keys.RETURN)
driver.implicitly_wait(10)

'''
find first element with id "main"
ex) <div id="main">...</div>
'''
main = driver.find_element(By.ID, 'main')
# print(main.text)

'''
find any html tag with "article" in id main
ex) <article id="123"> ...... </article>
'''
articles = main.find_elements(By.TAG_NAME, "article")
for article in articles:
    posted_time = article.find_element(By.TAG_NAME, "time")
    print(posted_time.text)

print("SCRIPT FINISHED")
# time.sleep(10)
# driver.quit()
