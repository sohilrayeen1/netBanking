Allure reporting helper

This project uses Allure TestNG adapter (dependency in pom.xml). When you run TestNG tests Maven will create results in `allure-results`.

Problems you might see
- Opening an old generated report with `allure serve` or using an explorer path may show previously generated results if the `allure-results` folder still contains data. To ensure fresh results, the `allure-results` directory should be removed before running tests.

Quick start (Windows)
1. Open a cmd.exe at the project root (where `run_allure.bat` lives).
2. Run `run_allure.bat`. This script will:
   - remove any existing `allure-results` directory,
   - run `mvn clean test` using `testng.xml`,
   - and call `allure serve allure-results` to open the newly generated report in your browser.

Requirements
- Maven on PATH
- Java SDK
- Allure commandline on PATH (install with Chocolatey: `choco install allure.commandline`) or follow official docs: https://docs.qameta.io/allure/

If you prefer not to delete previous results, you can archive them or run `allure open` on a generated report directory.
