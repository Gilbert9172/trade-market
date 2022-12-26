# Trade - Market

## 프로젝트 진행사항

---

• `2022-11-24 ~ 현재` : Application 개발중

<br>

## 프로젝트 소개

---

운동 선수의 이적을 보다 간단하고 편리하게 도와주는 이적시장 플랫폼입니다.

<br>

## DB 스키마

---

• <a href="https://gilbert9172.github.io/project/2022/11/19/Project-DB%EC%84%A4%EA%B3%84/" target="_blank">DB 명세</a>

![trade_market drawio Medium](https://user-images.githubusercontent.com/83274792/208341500-d0a1ec50-60d3-49c8-98e2-dcdd2c879cc9.png)


<br>

## 프로젝트 인프라

---

<img width="750" alt="프로젝트구상" src="https://user-images.githubusercontent.com/83274792/208341080-d7daf44f-09c6-4afc-bcdd-6de727ba6afe.png">

• 로컬에서 push 이벤트 발생

• Jenkins에서 Hook

• 어플리케이션을 도커 이미지로 빌드 후 도커허브에 업로드

• 도커 이미지 pull