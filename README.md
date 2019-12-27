# SuperJunior_server
server program

#### 데이터베이스 구성

---

- clients

|userid|pwd|groupid|
|---|---|---|
|varbinary(50)|varbinary(50)|varchar(20)|

- todo_list

|userid(FORIEGN KEY)|mand_schedule(필수일정)|chal_schedule(도전일정)|
|---|---|---|
|varbinary(50)|varchar(200)|varchar(200)|
