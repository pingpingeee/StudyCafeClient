# JavaTestCalculatorClient

## 개요

### 개발 환경
- IDE: Android Studio 2022.3.1.19
- Android studio Template: Empty Views Activity
- language: Java
- Minimum SDK: Android 10.0 (API 29)
- Groovy DSL
- Windows 10

### 핵심 기술
- Network: Socket 통신

## Details

### 주요 패키지
- com.example.calculator
- com.example.mysecondproject

### 주요 클래스
- ```com.example.calculator.CalcButtonListenerE```: 새로운 Thread를 생성하는 코드를 포함합니다.
- ```com.example.calculator.CalcThread```: 서버와 통신을 위한 Thread 클래스입니다.
- ```com.example.calculator.CalculatorUI```: 수식을 저장하는 Buffer 클래스입니다.
- ```com.example.mysecondproject.MainActivity```: 화면에 보이는 GUI 요소를 관리할 수 있는 클래스입니다.

## 주의사항
- ```CalcThread``` 클래스의 TODO 주석을 반드시 확인하고 자신의 개발 환경에 맞게 수정합니다.
