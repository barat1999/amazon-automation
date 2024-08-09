## Amazon.com Web Automation Task

This project contains automated test cases for the Amazon.com website, focusing on web-based functionalities using Selenium and TestNG.

## Pre-requisites

If you don't have the following pre-requisites, follow the steps under the Test Execution in Docker Container section:

1. Java 8 or higher
2. Maven 7.4.2 or higher
3. Chrome/Firefox browser close to the latest version

### Test Execution in Docker Container

To run the tests in a Docker container, follow the steps below. If you prefer running tests locally, install the pre-requisites mentioned above and skip this section.

1. Open a terminal and navigate to the project directory.
2. Use this command to build the docker image , if you're running the testcase in firefox browser 
`docker build -f Dockerfile-firefox -t <image-name> .` 
3. Use this command to build the docker image , if you're running the testcase in chrome browser 
`docker build -f Dockerfile-chrome -t <image-name> .`
4. Run `docker run -d --name <container-name> <image-name>  ` to start the container
5. Run `docker exec -it <container-name> /bin/bash` to enter the container
6. Run `mvn clean test -DBrowser=<Chrome or Firefox> -DBrowserMode=“Headless” ` to execute the tests

## Run/Debug Configuration for IntelliJ

1. Set `Amazon.xml` as test suite / select a specific test class in Run/Debug Configuration
2. Open the Edit Configuration
3. Open `Listeners` tab and enable `Use default reporters`
4. Input the following command in VM options:

```
-ea 
-DBrowser=
-DBrowserMode=

```

6. Save configuration and run test

Example:

```
-ea
 -DBrowser="Chrome" 
 -DBrowserMode="nonHeadless"
 
```

## Command Line Execution

1. Open a terminal & navigate to the project directory
2. Run the following command:

```

mvn clean test -DBrowser="Chrome" -DBrowserMode="nonHeadless"

```

## Test Report

Once execution completed, the reports can be found in the `target/extent-reports/index.html` file.

## GitHub Actions

To run the tests in CI/CD, use the following
project: https://coda.teamcity.com/buildConfiguration/AutomationTesting_FraudApiAutomation_Test

1. Click `Run` to execute the tests
2. Enter the necessary parameters in the `Parameters` field 
3. Click `Run Build` to start the tests
4. Once the tests are completed, the test report can be found under the `Test Report` or `Artifacts` tab

## Selenium, TestNG, and Maven Overview
This project utilizes Selenium for browser automation, TestNG for test case management, and Maven for build and dependency management. Below is a brief overview:

- **Selenium:** A powerful tool for automating web browsers, Selenium allows you to interact with web elements, perform actions, and verify results as part of your automated test cases.


- **TestNG:** TestNG is a testing framework inspired by JUnit and NUnit, designed to simplify a broad range of testing needs, from unit testing to integration testing. It provides features like parallel execution, dependency testing, and test configuration.


- **Maven:** Maven is a build automation tool used primarily for Java projects. It helps manage project dependencies, build processes, and project documentation, ensuring consistent builds across environments.

### Reference Links for Learning
If you are new to these technologies or want to deepen your understanding, here are some useful resources:

###Selenium WebDriver:

1. [Official Documentation](https://www.selenium.dev/documentation/)
2. [Selenium WebDriver Tutorial](https://www.guru99.com/selenium-tutorial.html)
3. [Selenium with Java](https://www.toolsqa.com/selenium-webdriver/selenium-webdriver-tutorial/)


###TestNG:

1. [Official TestNG Documentation](https://testng.org/doc/documentation-main.html)
2. [TestNG Tutorial for Beginners](https://www.guru99.com/all-about-testng-and-selenium.html)
3. [TestNG Annotations](https://testng.org/doc/documentation-main.html#annotations)


###Maven:

1. [Maven: The Complete Reference](https://books.sonatype.com/mvnref-book/reference/)
2. [Maven Tutorial](https://www.tutorialspoint.com/maven/index.html)
3. [Maven in 5 Minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)


These resources should provide you with a solid foundation to get started or advance your skills in test automation with Selenium, TestNG, and Maven.
