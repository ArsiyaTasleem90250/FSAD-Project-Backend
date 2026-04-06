@echo off
REM Start Backend - Spring Boot on port 2020

echo.
echo ========================================
echo Starting Spring Boot Backend
echo ========================================
echo Port: 2020
echo Database: studentdb (localhost:3306)
echo.

cd /d C:\Users\arshi\OneDrive\Desktop\FSAD-PROJECT-40\Backend

echo Cleaning and building...
call mvnw clean install -q

echo.
echo Starting server...
call mvnw spring-boot:run

pause
