# 🎫 Event Reservation System with JDBC + Thymeleaf
간단한 이벤트 예약 관리 웹 애플리케이션 
Spring Boot와 JDBC, Thymeleaf를 사용하여 구현되었으며, 이벤트 등록/수정/삭제 및 예약 기능을 제공합니다.

---

## 🚀 Features

- ✅ 이벤트 등록 / 수정 / 삭제
- ✅ 이벤트 별 예약 등록
- ✅ 예약 수정 / 삭제
- ✅ 전체 예약 목록 조회
- ✅ 입력값 유효성 검사
- ✅ Thymeleaf 기반 동적 HTML 렌더링

---

## ⚙️ Tech Stack

| 기술            | 설명                         |
|-----------------|------------------------------|
| Spring Boot     | 백엔드 프레임워크            |
| JDBC            | DB 연결 및 쿼리 수행         |
| Thymeleaf       | 템플릿 기반 뷰 엔진          |
| HTML / CSS      | 프론트엔드 UI 구성            |
| Lombok          | DTO / Entity 간단화          |
| Jakarta Validation | 유효성 검증                 |

---

## 📂 프로젝트 구조

```plaintext
📁 com.my131.event_reservation_with_jdbc_thymeleaf
┣ 📁 controller
┃ ┣ 📄 EventController.java
┃ ┣ 📄 IndexController.java
┃ ┗ 📄 ReservationController.java
┃
┣ 📁 dto
┃ ┣ 📄 EventDto.java
┃ ┗ 📄 ReservationDto.java
┃
┣ 📁 model
┃ ┣ 📄 Event.java
┃ ┗ 📄 Reservation.java
┃
┣ 📁 repository
┃ ┣ 📄 EventRepository.java
┃ ┗ 📄 ReservationRepository.java
┃
┣ 📁 service
┃ ┣ 📄 EventService.java
┃ ┗ 📄 ReservationService.java
┃
┗ 📄 EventReservationWithJdbcThymeleafApplication.java
