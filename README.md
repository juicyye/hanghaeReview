## 🛠️ 개요
- 상품에 대한 리뷰를 작성할 수 있는 서비스입니다.
- 유저는 하나의 상품에 대해 하나의 리뷰만 작성 가능하고 1~5점 사이의 점수와 내용을 남길 수 있습니다.
- 리뷰는 가장 최근에 작성된 리뷰 순서대로 조회 가능합니다.

## 📋 주요 기능
### 상품 관리
- 상품에는 점수와 리뷰 수가 있습니다.
- 리뷰 수와 점수에 맞는 평균 점수를 조회할 수 있다
### 리뷰
- 하나의 상품에 여러 개의 리뷰를 작성할 수 있습니다
- 리뷰를 작성할 때 점수와 리뷰 내용을 작성합니다
- 상품에 대한 리뷰들을 오름차순으로 조회할 수 있습니다.
- 커서페이지네이션 기반으로 정렬되어 있습니다
### 이미지
- 하나의 리뷰에는 하나의 이미지를 첨부할 수 있습니다.

## ⚠️ 예외 상황 및 유효성 검사
- 리뷰 내용: 리뷰 내용은 한글, 영문, 숫자 2~50사이가 아니라면 에러를 반환합니다
- 점수는 0~5 이내 숫자가 아니라면 에러를 반환합니다
- 유저아이디는 필수입니다

## 🧾 기능 목록
- 더미 상품 10개 생성 기능
- 사용자 요청 시 상품에 대한 리뷰 생성 기능
- 리뷰 이미지 생성 기능
- 상품에 대한 리뷰 조회 및 정렬 기능
- 리뷰 이미지 조회 기능
- 에러상황 발생 시 에러 메시지 반환 기능
- 리뷰가 생성되면 리뷰에 대한 점수와 총 리뷰수 증가 기능

## 🔍 기술적 의사결정
### 아키텍처
- 헥사고날 아키텍처를 도입하여 외부 세계와 도메인 로직을 분리했습니다.
- Port와 Adapter를 통해 도메인 로직이 외부 환경에 종속되지 않도록 설계했습니다.
- 이 접근은 유지보수성과 테스트 용이성을 크게 향상시켰습니다.
### 상품 리뷰
- 리뷰가 생성되면 스프링 이벤트를 통해 상품의 리뷰수와 점수를 올리는 이벤트를 발행했습니다
- 리뷰가 커밋될 때 리뷰 수와 점수를 올려야하고, 리뷰 생성과 직접적인 연관이 없다고 판단했습니다.
### 리뷰 이미지
- 처음에는 이미지도 이벤트 발행으로 처리했지만, 리뷰와 직접적인 관련이 있다고 판단하여 직접 호출하여 저장하는 쪽으로 변경했습니다.
- 리뷰에 대한 이미지를 불러올 때도 **리뷰**에 대한 이미지이므로 비즈니스 로직이라고 판단하여 서비스에서 호출해 도메인에 넣어주는 형식입니다.
- 컨트롤러에서 할까 생각했지만 제가 생각하는 컨트롤러는 사용자 요청과 응답을 하는 레이어라고 생각했고 리뷰에 대한 이미지파일을 넣어주는 작업은 비즈니스 로직이라고 판단했습니다.
### 동시성 문제
- 자바 Lock, 낙관적 락, 비관적 락을 고려했습니다.
- 헥사고날 아키텍처에서는 낙관적 락 사용이 어려워 비관적 락으로 동시성 문제를 해결했습니다.
- 맨 처음 이벤트가 발행되면 평균점수를 상품에 저장하는 로직이었으나 지금은 총 점수를 저장하도록 바꿨습니다.
- 이 블로그에는 첫번째 방법인 평균점수를 상품에 저장하는 로직으로 테스트한 것입니다.
- 오늘아침에 바꾸게 되어서 테스트는 진행했으나 블로그에 작성하지는 못했습니다.
- 👉 [동시성 문제 해결 과정 블로그](https://velog.io/@paramkim/%EB%8F%99%EC%8B%9C%EC%84%B1-%ED%95%B4%EA%B2%B0-%EA%B3%BC%EC%A0%95)

## ERD
<img width="800" alt="hangaeReview" src="https://github.com/user-attachments/assets/a700520b-d74b-4e8c-9b5e-6fca56dcea1f" />




