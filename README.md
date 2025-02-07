
<img src="https://github.com/user-attachments/assets/f8e41402-397d-43b8-b2cf-5426a575514e" width="300"/>

# EMAIL-SERVICE

목차
-----------------------------------
1. URL & SERVICE 기능 명세
2. ERD
3. SWAGGER API
4. 이메일 서비스 담당 인원

URL & SERVICE
------------------------------------

![image](https://github.com/user-attachments/assets/0c8743d4-d65a-400f-9230-845e8deacb4f)

Email
---------
+ 인증코드 발송
  +  사용자가 회원가입 시 이메일 인증코드 요청하면 인증코드 생성하고 요청한 이메일로 인증코드 이메일을 발송해줍니다.
  +  전송된 인증 코드는 서버에 저장되고 3분뒤에 만료됩니다.
+ 인증코드 검증
  +  사용자가 입력한 인증코드를 검증해줍니다.
  +  인증 코드와 만료시간을 확인하고 검증해줍니다.
  +  검증 시 틀리거나 만료되었을 시 예외 발생 후 알려줍니다.
+ 이메일 발송
  + 특정 템플릿을 사용하여 이메일을 보낼 시 사용되는 역할
  + 커맨드 객체로 이메일을 구성
  + General을 사용하여 템플릿 지정 가능

ADMIN EMAIL
-------------------
+ 로그 조회 (단일)
  +  로그 조회시 단일로 조회할 수 있게해줍니다.

+ 로그 조회 (목록)
  +  로그 조회시 목록으로 조회할 수 있게해줍니다.
  +  특정 로그 검색시 키워드를 통해 상세검색할 수 있습니다.

ERD 엔티티 명세
-----------------------------

![제목 없음](https://github.com/user-attachments/assets/2b8fcf53-93c9-432a-9858-6ea52a421fa7)

엔티티
---------------------------------
+ SEQ : 이메일 로그 번호(기본키)
+ CreatedAt: 이메일 발송 시간.
  + 사용자가 이메일 요청시 발송된 시간을 저장합니다.
   
+ DeletedAt: 이메일 로그 삭제 시간.
  + 이메일 로그가 삭제된 시간을 기록합니다.
+ _to: 수신자
  + 이메일을 요청한 수신자를 저장합니다.
+ 이메일 제목
  + 발송된 이메일의 제목을 저장합니다.
+  이메일 내용
    + 발송된 이메일의 내용을 저장합니다. 

SWAGGER API
------------------------------
http://cis-email-service.koreait.xyz/apidocs.html
![image](https://github.com/user-attachments/assets/8f3eb17a-04b9-4f60-b707-2dfbc27b9de9)


이메일 서비스 담당 및 역할
------------------------------
차태일: 이메일 전송 로그 수집 및 조회 서비스 / 이메일 발송 서비스 /이메일 인증코드 발송 및 검증 서비스
