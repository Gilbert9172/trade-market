# Trade - Market

## 프로젝트 진행사항

---

• `2022-11-24 ~ 2022-12-26` : Application 개발 일시중지<br>
• `2022-12-26 ~ 2022-12-29` : 프로젝트 인프라 구축<br>

<br>

## 프로젝트 소개

---

• <a href="http://52.78.159.127/api/swagger-ui/index.html" target="_blank">Trade-Market Swagger</a>

운동 선수의 이적을 보다 간단하고 편리하게 도와주는 이적시장 플랫폼입니다.

<br>

## DB 스키마

---

• <a href="https://gilbert9172.github.io/project/2022/11/19/Project-DB%EC%84%A4%EA%B3%84/" target="_blank">DB 명세</a>

![trade_market drawio Medium](https://user-images.githubusercontent.com/83274792/208341500-d0a1ec50-60d3-49c8-98e2-dcdd2c879cc9.png)

<br>

## 프로젝트 인프라

---

![aws구조](https://user-images.githubusercontent.com/83274792/210045675-c1e4d964-9f7c-4f9f-9d4d-680a9c616dd0.jpg)

• nginx host:container port - `80:80`

• application port - `:8080`

• redis port - `ec2-local :6973`

• rdb port - `rdb-local :3306`

<br>

## 프로젝트 CI/CD Work-Flow

---

• <a href="https://gilbert9172.github.io/project/2022/12/29/Project-CICD/" target="_blank">프로젝트 CI/CD Work-Flow 구축과정</a>

![workflow](https://user-images.githubusercontent.com/83274792/210045228-a2842071-f34a-406f-9a00-17ce30c0df4d.jpg)

### [ Local ]
- master 브랜치에 push 또는 pull request

<br>

### [ Git Action ]
- build gradle (gradle validation, test code ...)
- build docker images
- push docker images to Docker-Hub
- Docker Compose up

<br>

### [ EC2 ]
- pull docker images from DockerHub
- Docker Compose up