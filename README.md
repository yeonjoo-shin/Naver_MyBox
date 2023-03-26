# Naver_MyBox
넘블 MyBox 챌린지


## 환경설정
JAVA 17 \
SpringBoot 2.6.3 \
Gradle 7.2 \
mysql 8 \
swagger 3.0.0


## 아키텍쳐 
**Multi Module** 
- api : controller,service 등의 서비스 로직이 들어있는 모듈
- core : 도메인 정보, 공통 정보가 들어 있는 모듈
- mysql : RDBMS 정보가 들어 있는 모듈
- external : feign,redis 등 외부라이브러리 설정 모듈 

**Layered architecture**

## 유저 관리 
Spring Security + JWT을 활용한 Token 방식 적용

## 파일 관리
로컬 파일 서버 + Netflix feign 사용

## CICD

## TDD

## Storage

## nGrinder

## APM
scouter 사용 예정
