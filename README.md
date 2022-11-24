# coupon



![image](https://user-images.githubusercontent.com/46472768/203800689-35ad0566-9540-4578-b0a3-0e18573a73cc.png)




#1  terminal 에서 프로젝트 루트 디렉토리에 위치한 다음 아래 명령어를 실행하여 docker 내에서 MySQL을 설치합니다.

    docker-compose -p reward-db up -d



#2  아래 sql 문을 실행시켜 주세요 :)

    INSERT INTO event (title, quantity, created_date) VALUES ("선착순 이벤트", 10, NOW());
