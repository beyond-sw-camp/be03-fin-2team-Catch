# CATCH
# 지속적인 고객 관리를 위한 시스템

# 프로젝트 개요
## 프로젝트 소개
CATCH 프로젝트는 CRM마케팅 전략을 기반으로 백화점에서 마케터들이 고객들을 대상으로 관리, 또는 이벤트를 제공 할 수 있는 서비스이다. 고객 정보를 토대로 성별, 나이, 멤버쉽 등 각각의 기준으로 통계 및 그래프를 제공하여 회사가 고객들의 소비 패턴들을 분석하고 마케팅 전략을 계획 할 수 있게 도움을 준다. 뿐만 아니라 마케팅 전략에 맞춰 이벤트를 제공 할 수 있게 회원들에게 이메일을 통한 광고를 전송하거나 멤버쉽 등급에 따라 쿠폰을 발급하는 등의 서비스를 구현해 회사가 고객 관리를 지속적으로 할 수 있게 한다.

## CRM 마케팅
CRM 마케팅이란, Customer Relationship Management의 약자로 고객 관계 관리라는 뜻이다. 기존 고객과의 관계를 중시하여 ‘고객이 어떻게 하면 더 많이 서비스를 이용하고 비용을 지불 할까?’ 에 대해 고민하는 전략이다. 고객데이터의 세분화를 실시하여 신규고객획득, 우수고객 유지, 고객가치증진, 잠재고객 활성화, 평생고객화와 같은 사이클을 통하여 고객을 적극적으로 관리하고 유도한다. 기존 마케팅이 단발적인 마케팅 전술이라면 CRM은 고객과의 지속적인 관계를 유지하면서 '한 번 고객은 평생고객'이 될수 있는 기회를 만들며, 평생고객화를 통해 고객의 가치를 극대화하는 것이다.

## 서비스 타켓 선정 
이번 프로젝트에서 서비스의 대상을 백화점으로 선정했다. 그 이유는  가장 먼저 많은 고객들을 대상으로 서비스를 제공하고 있는 업계이기 때문이다. 해당 프로젝트는 많은 고객들의 데이터를 분석하고 제공해주기 때문에 많은 이용자 수를 가지고 있는 곳이 필요했다. 뿐만 아니라 백화점은 유통 및 서비스 업계에 속해 있는데 해당 업계는 많은 고객들을 분석하고 이벤트를 기획을 하는 것이 회사매출에 가장 중요한 부분을 차지하고 있기 때문에 대상을 백화점으로 선정했다.

## CRM 마케팅 사례
<p align="center">
<img src=".github/Image/마켓 컬리.png" height="40%" width="40%">
<img src=".github/Image/알리익스프레스.png" height="40%" width="40%">
</p>

**마켓컬리**
  - 마켓컬리에서는 SMS 문자메시지를 통해 CRM을 하고있다. 유사한 성향을 가진 고객 집단을 분류해 맞춤형 할인 쿠폰 등 개인화 된 혜택을 제공하고 있다.
    
**알리 익스프레스**
  - 알리 익스프레스에서는 Email를 통해 CRM을 하고 있다. 특정 기간에만 진행하는 할인 행사를 인기있는 상품들을 위주로 보여줘 관심을 갖게 하여 행사에 참여하게끔 한다.

</br>
</br>

# 개발 환경
## 공통
버전 관리 및 협업: GitHub, Git, GitHub Project, Notion
데이터베이스: MariaDB
커뮤니케이션: Slack

## 프론트엔드
개발 환경: Visual Studio Code
프로그래밍 언어: HTML, CSS, JavaScript, Vue.js

## 백엔드
개발 환경: IntelliJ IDEA
프로그래밍 언어: Java 11
빌드 도구: Gradle
프레임워크: Spring, Spring Boot
보안: Spring Security, JSON Web Tokens (JWT)

</br>
</br>

# 요구사항 분석
## 사용자(회원)
|요청사항 명|요구사항 상세 설명|
|---|:---|
|**회원 가입 (일반 회원)**|회원은 이메일 인증을 통해 회원 가입을 할 수 있다.|
|**회원 가입 (소셜 회원)**|회원은 [구글] 소셜 수단으로 회원 가입 할 수 있다.|
|**회원 가입시 기본 입력**|정보	회원 가입 진행 시 이름, 이메일, 비밀번호, 생년월일, 주소를 입력해야 한다.|
|**개인정보 선택적 수집 및 이용에 대한 동의**|회원은 온라인회원 마케팅 수신동의(이메일, SMS)를 동의, 거부 할 수 있다.|
|**로그인 (일반 회원)** |일반 회원은 이메일, 비밀번호로 로그인 할 수 있다.|
|**로그인 (소셜 회원)**|소셜 회원은 [구글] 소셜 수단으로 로그인 할 수 있다.|
|**비밀번호 재설정 (일반 회원)**|일반 회원은 이메일 인증을 통해 비밀번호를 재설정 할 수 있다.|
|**마이 페이지**|회원은 마이페이지를 통해 개인 정보를 조회, 수정을 할 수 있다.|
|**개인 정보 수정**|회원은 주소, 마케팅 수신 동의를 수정 할 수 있다.|
|**개인 정보 조회**|자신의 아이디, 생년월일, 이메일, 주소, 마케팅 수신 동의를 조회할 수 있다.|
|**1:1 문의**|회원은 1:1 문의를 할 수 있다.|
|**개인의 쿠폰 상세 조회**|회원은 본인이 갖고 있는 쿠폰의 종류, 사용기간을 조회할 수 있다.|
|**개인의 포인트(적립금) 상세 조회**|회원은 본인이 갖고 있는 포인트(적립금), 사용기간을 조회할 수 있다.|
|**쿠폰 등록**|회원은 쿠폰 코드로 쿠폰을 등록 할 수 있다.|
|**개인의 문의 상세 조회**|회원은 본인의 문의 내용, 해당 문의의 답변을 조회할 수 있다.|
|**회원 탈퇴**|회원은 해당 서비스를 탈퇴할 수 있다.|
|**최근에 본 상품 목록**|회원은 본인이 최근에 본 상품의 목록을 확인할 수 있다.|
|**관심 상품**|회원은 상품에 좋아요를 눌러서 관심 상품에 등록할 수 있다.|
|**관심 상품 목록**|회원은 본인이 관심있는 상품의 목록을 조회할 수 있다.|

## 웹
|요청사항 명|요구사항 상세 설명|
|---|:---|
|**로그인 후 화면 (메인)**|로그인 후 회원 개인의 등급, 쿠폰, 포인트(적립금)을 조회할 수 있다.|
|**로그인 후 화면 (배너)**|로그인 후 관리자가 등록한 배너가 웹 메인에 노출되어야 한다.|

## 이벤트
|요청사항 명|요구사항 상세 설명|
|---|:---|
|**선착순 쿠폰**|선착순 쿠폰 이벤트|


## 사용자 등급
|요청사항 명|요구사항 상세 설명|
|---|:---|
|**사용자 등급 갱신**|사용자는 자신의 등급의 변동이 있을경우 실시간으로 확인해야한다.|

## 관리자
|요청사항 명|요구사항 상세 설명|
|---|:---|
|**회원 정보 조회**|관리자는 전체 또는 특정 회원의 정보를 입력해 조회할 수 있다.|
|**회원 정보 수정**|관리자는 회원의 정보를 수정 및 삭제 할 수 있다.|

## 마케터
|요청사항 명|요구사항 상세 설명|
|---|:---|
|**성별을 통한 회원 분류**|성별에 따른 회원 분포 확인할 수 있다.|
|**나이를 통한 회원 분류**|연령별 회원 분포를 확인할 수 있다.|
|**등급을 통한 회원 분류**|등급별 회원 분포 확인할 수 있다.|
|**광고성 메일**|마케터는 메일에 내용을 작성할 수 있고, 해당 내용을 메일로 보낼수 있다.|
|**쿠폰 관리**|마케터는 쿠폰 생성이 가능하다.|
|**회원 포인트 관리**|회원 포인트(적립금) 조회 및 수정할 수 있다.|
|**신규 이벤트 생성 및 이벤트 수정**|이벤트 생성 및 삭제 관리를 할 수 있다.|


## 상담사
|요청사항 명|요구사항 상세 설명|
|---|:---|
|**1:1 문의에 대한 답변**|회원이 남긴 1 : 1 문의에 대해 답변을 남길 수 있다.|

## 알람
|요청사항 명|요구사항 상세 설명|
|---|:---|
|**알람**|기한 만료 예정인 쿠폰 및 이벤트에 대한 정보를 확인할 수 있다.|

## 쿠폰
|요청사항 명|요구사항 상세 설명|
|---|:---|
|**발급된 쿠폰**|발급된 쿠폰은 수정 및 삭제가 불가능 하다.|
|**미발급 쿠폰**|미발급된 쿠폰은 수정 및 삭제가 가능하다.|

</br>

#  [📅 WBS](https://docs.google.com/spreadsheets/d/19pO974fKGKGOW3XLY8PIu9Kd5hFQFiLN8cf3-CNAI-o/edit?usp=sharing)
</br>

# [📈 ERD](https://www.erdcloud.com/d/4hBsL2CcK8LnCFWJL)
</br>

# [🖥 화면설계서](https://www.figma.com/file/J6p1dopme7dCpIH5zSnxHc/Catch?type=design&node-id=0%3A1&mode=design&t=OH7rpExEUxgtSPbR-1)
</br>

# [🎬API 명세 및 테스트 결과서](https://documenter.getpostman.com/view/32406846/2sA35LTyRZ)
</br>

# ⛏ 아키텍처 설계도
<img src=".github/Image/Catch-아키텍처.png" height="100%" width="100%">
</br>


# UI/UX 단위 결과서

<details>
<summary> <h2> 로그인 & 로그아웃 </h3> </summary>

<h3> 고객: 로그인 </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/2be05d62-8230-4f3c-bc67-be698040c72c" height="100%" width="100%">
</br>

<h3> 고객: 로그아웃 </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/ef05e457-a500-4431-8489-0a14a18e49aa" height="100%" width="100%">
</br>

</details>


<details>
<summary> <h2> 고객: 문의 게시글 </h3> </summary>

<h3> 고객: 마이페이지 → 나의 게시글 보기 </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/84c1298f-3d77-4b10-99a4-224c32c5fad3" height="100%" width="100%">
</br>

<h3> 고객: 문의글 작성하기 </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/6e5d7d10-514a-4a93-95b1-d7ca532f8015" height="100%" width="100%">
</br>

<h3> 고객: 문의글 수정하기 </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/44cec826-9ad5-4579-b8b5-f8a814677c04" height="100%" width="100%">
</br>

<h3> 고객: 문의글 삭제하기 </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/f06d9790-3231-47f7-a60a-7208ffdbdd86" height="100%" width="100%">
</br>

</details>



<details>
<summary> <h2> 관리자: 문의게시글 </h3> </summary>

<h3> 관리자: 대시보드(메인화면) </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/1578f984-b3ae-4f70-b7c1-7d9671c53f78" height="100%" width="100%">
</br>

<h3> 관리자: 문의게시글 리스트(검색) </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/63e91407-1932-40ab-a79f-c7d4de7f60b3" height="100%" width="100%">
</br>

<h3> 관리자: 게시글 상세보기 및 답변 달기 </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/0450ac7d-be9b-4410-a58c-6591c03a8ff3" height="100%" width="100%">
</br>

<h3> 관리자: 게시글 답변 수정 </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/cedd0d4b-27d6-4ed4-ae10-351c12d0c2a2" height="100%" width="100%">
</br>

<h3> 관리자: 게시글 답변 삭제 </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/ab27b64b-33fc-4b06-81e4-71d90157972a" height="100%" width="100%">
</br>

</details>

<details>
<summary> <h2> 쿠폰 </h3> </summary>

<h3> 관리자: 쿠폰리스트 및 검색 </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/75c840d5-aac0-4482-a728-4a0d2158ef39" height="100%" width="100%">
</br>

<h3> 관리자: 쿠폰생성 </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/7c767138-d67c-4fc2-9a41-e043dc0a1769" height="100%" width="100%">
</br>

<h3> 고객: 쿠폰수령 </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/4ff26c38-cee9-433f-98b7-383bde22e4b7" height="100%" width="100%">
</br>

<h3> 고객: 수령 가능 쿠폰 및 내 쿠폰 보기 </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/a51ddba4-7726-4330-acff-a8e200815574" height="100%" width="100%">
</br>

<h3> 쿠폰 발행 및 알람 </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/3471778f-dfda-492c-9d1a-aa5d15584099" height="100%" width="100%">
</br>

</details>


<details>
<summary> <h2> 관리자: 고객리스트 </h3> </summary>

<h3> 관리자: 고객리스트 검색 </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/1d8f70d6-def3-44a9-8605-5a13d3394f74" height="100%" width="100%">
</br>

<h3> 관리자: 고객 상세보기, 메모, 활성화, 비활성화 </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/45dbf2c9-46cc-4b5f-992d-3cc60ba248be" height="100%" width="100%">
</br>

</details>

<details>
<summary> <h2> 관리자: 회사 계정 관리 </h3> </summary>

<h3> 관리자: 회사 계정 검색 </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/ea86d4c3-a85a-44bd-b46b-2b39162079f9" height="100%" width="100%">
</br>

<h3> 관리자: 회사 계정 생성 </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/09f58a17-c684-4751-86e8-a816a77c018f" height="100%" width="100%">
</br>

<h3> 관리자: 회사 계정 수정, 계정 활성화, 비활성화 </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/58584e76-5598-4fcd-873e-adbe4d3ba328" height="100%" width="100%">
</br>

</details>


<details>
<summary> <h2> 관리자: 이벤트 </h3> </summary>

<h3> 관리자: 이벤트 생성 </h3>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/788c2bd1-c6c5-45bf-b8eb-234d5c7c893f" height="100%" width="100%">
</br>

</details>


<details>
<summary> <h2> 접근 권한 및 페이지 없음 (404, 403) </h3> </summary>

<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/452f7462-eb5b-4c8b-a06e-90e0c5271089" height="100%" width="100%">
</br>

</details>


<details>
<summary> <h2> 관리자: 리스트 화면 페이징 처리 </h3> </summary>
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/088ec497-6511-47a9-b90b-fe4a7115a51b" height="100%" width="100%">
</br>

</details>

</br>



<h1> 📋CI/CD 계획서 </h1>

## 목표 및 범위:
- 모놀리식 CRM 서비스와 핵사고날 아키텍처로 설계된 event 모듈 및 SpringBatch로 구성된 배치서비스 들을 미니서비스아키텍처로 구성하고
  이를 안정적으로 배포해 쿠버네티스 컨테이너 오케이스트레이션을 통해 대규모 데이터 및 트래픽에 대비할 수 있도록 합니다.

## 환경 및 도구:
- CI/CD를 위해 사용할 환경과 도구
    - CI 도구: GithubAction, 
    - CD 도구: Docker, Kubernetes

## 저장소:
- 코드를 저장할 저장소와 브랜치 전략
    - 저장소: GitHub
    - 브랜치 전략: 각 Feature 브랜치를 dev에 머지하고, dev 서버를 통해 AWS에 배포한 후 안정적인 서비스가 가능할 때 main 브랜치에 머지 후 버전관리합니다.

## 빌드 및 자동화 스크립트:
- CI/CD 파이프라인을 구축하기 위한 빌드 스크립트와 자동화 스크립트
    - 빌드 스크립트: Gradle
    - 자동화 스크립트: GitHub Action Script

## 배포 전략:
-  쿠버네티스를 이용한 롤링 배포

### 단계:

1. **GitHub 저장소 체크아웃**:
  - GitHub 저장소를 체크아웃합니다.

2. **kubectl 설치**:
  - kubectl을 설치합니다.

3. **AWS 자격 증명 구성**:
  - AWS 자격 증명을 구성합니다.

4. **클러스터 정보 업데이트**:
  - 클러스터 정보를 업데이트합니다.

5. **Amazon ECR 로그인**:
  - Amazon ECR에 로그인합니다.

6. **Docker 이미지 빌드 및 푸시**:
  - CRM 서비스 및 배치 서비스용 Docker 이미지를 빌드하고 Amazon ECR로 푸시합니다.

7. **Kubernetes에 서비스 적용 및 배포 재시작**:
  - CRM 서비스 및 이벤트 서비스와 배치 서비스를 Kubernetes에 적용하고 배포를 재시작합니다.

## 권한 및 보안:
- CI/CD 파이프라인의 접근 제어 및 보안 정책을 기술합니다.
    - 접근 제어: IAM
    - 보안 정책: 쿠버네티스 시크릿을 이용한 데이터 암호화, 보안 그룹 설정


## 문서화 :
- CI/CD 계획서 
    - 문서화: GitHub Readme 작성


</br>

<details>
<summary> <h1> 배포 과정 </h1> </summary>

## S3 버킷에 dist 파일 업로드
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/22e3419c-5fbd-4f34-940c-f2969dc253af" height="80%" width="80%">

<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/fa1de006-2083-48a1-ba58-e41f68ca5e5d" height="80%" width="80%">

</br>

## 버킷 정책 편집
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/f479ab42-a2f5-48af-bfcd-e676132daae4" height="80%" width="80%">

</br>

## Cloud Front 생성
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/2fee512d-0609-4765-85f5-5ea502d7c8af" height="80%" width="80%">

</br>

## Cloud Front에 적용할 SSL 인증서 생성
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/9358e912-7c6d-46fc-90cd-28c3b4221e3a" height="80%" width="80%">

<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/763dbcc4-3d03-4766-818e-bf0de7eda16a" height="80%" width="80%">

</br>

## Route53에서 호스팅 영역 생성
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/ad5ac0db-d054-4869-a399-614b12121977" height="80%" width="80%">

</br>

## 프론트 레코드 생성
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/5512f162-3e1a-4074-9f00-8b7905579895" height="80%" width="80%">

</br>

## CloudFront의 DNS 세팅
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/221d4d74-7952-4666-8bfb-a66d3f5dd87b" height="80%" width="80%">

</br>

## EKS 클러스터 생성
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/530fdbe7-065a-447f-aa5c-a4b7a62c773b" height="80%" width="80%">

</br>

## 클러스터 서비스 역할 EKS로 설정
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/088be74e-dc23-40cc-a73c-e5024f67a4c6" height="80%" width="80%">

<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/d6304e70-97fa-49b1-9387-4705640a2d35" height="80%" width="80%">

</br>

## 클러스터 이름 생성
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/a0f687f7-29ff-44f3-973a-f41ab4b185be" height="80%" width="80%">

<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/3a57cdc6-0b3e-4aad-a56a-ff9793635c20" height="80%" width="80%">

</br>

## EKS 보안그룹 설정
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/e3f8df2e-f885-466d-8854-40d945c25b8e" height="80%" width="80%">

</br>

## EKS 생성
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/1d366f8d-5618-4173-badb-8050c7f5e88a" height="80%" width="80%">

</br>

## 노드그룹 생성
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/0cf3ee88-8089-49f4-9f7e-ae0b9f2ee969" height="80%" width="80%">

</br>

## 노드그룹 IAM 역할 설정
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/7a948627-1280-4c59-bdca-10d8f2f0fd9c" height="80%" width="80%">

</br>

## IAM 역할 EC2 부여
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/cf738812-40e9-464b-b2a4-c346e864da7c" height="80%" width="80%">

</br>

## IAM 역할 EKS 권한 부여 
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/e0dadefd-6cca-418d-82dc-7d3865fd593a" height="80%" width="80%">

</br>

## 노드그룹 스펙 설정
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/5815c561-8569-4bf4-b1f6-fa2fbe5a5d87" height="80%" width="80%">

</br>

## 노드그룹 크기 설정
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/9bfd65fa-d89f-48b2-8af1-3b6f5c584e5a" height="80%" width="80%">

</br>

## 노드그룹 생성 완료
<img src="https://github.com/Catch-team/Catch-BE/assets/78871184/c3f5c062-f2b5-4315-a0cd-27299d70e80a" height="80%" width="80%">

</br>




</details>

