# coupon

이벤트 쿠폰 발급 기능 관련 고민 해볼 것들
-

https://rift-pigeon-2af.notion.site/65ba3c8036144cce92856d78e711fc69

<br/>
<br/>
<br/>




실행방법
-
#1  terminal 에서 프로젝트 루트 디렉토리에 위치한 다음 아래 명령어를 실행하여 docker 내에서 MySQL을 설치합니다.

    docker-compose -p reward-db up -d

<br/>
<br/>

#2  아래 sql 문을 실행시켜 주세요 :)

    INSERT INTO event (title, quantity, created_date) VALUES ("선착순 이벤트", 10, NOW());
    
    
    
