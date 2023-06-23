# Дипломный проект профессии «Тестировщик»

## Документация проекта:
 1. *[План автоматизации](Documentation/Plan.md)*
 2. *[Отчет по итогам тестирования](Documentation/Report.md)*
 3. *[Отчет по итогам автоматизированного тестирования](Documentation/Summary.md)*


## Инструкция по запуску авто-тестов:
1. Клонируйте репозиторий командой git clone https://github.com/Ir-inka/qa54-diploma.git
2. Запустите контейнер в терминале IntelliJ IDEA командой: **docker-compose up** 
3. Запустите SUT командой: 
   * Для базы MySQL: **java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar ./artifacts/aqa-shop.jar**
   * Для базы Postgresql: **java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar ./artifacts/aqa-shop.jar**
4. Проверить, что сайт открылся на сайте: **http://localhost:8080/**
5. Открыть в терминале IntelliJ IDEA новую вкладку
6. Запустить авто - тесты:
   * Для базы MySQL: **./gradlew clean test -Durl=jdbc:mysql://localhost:3306/app -Duser=app -Dpassword=pass**
   * Для базы Postgresql: **./gradlew clean test -Durl=jdbc:postgresql://localhost:5432/app -Duser=app -Dpassword=pass**
7. Для просмотра отчётов ввести команду: **./gradlew allureServe**
8. После завершения прогонов всех авто - тестов останавливаем сервисы командами:
   * В терминале, в котором выполнялся отчёт Allure ввести Ctrl + C, ввести Y, нажать Enter
   * В терминале, в котором запущен SUT ввести Ctrl + С
   * В терминале, в котором запущены контейнеры ввести Ctrl + C, после остановки контейнеров, ввести команду **docker-compose down**




        
        
      
