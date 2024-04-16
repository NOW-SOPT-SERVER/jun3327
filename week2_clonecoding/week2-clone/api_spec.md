#### 1. 물건 팔기 등록 API
- 사용자가 팔고 싶어하는 판매글의 제목, 거래 방식, 가격, 가격 제안 여부, 자세한 설명, 거래 희망 장소를 사용자로부터 입력받고, DB에 저장한다.
#### Request

##### Path Parameter

##### Request Header
```
POST /api/v1/home/post/sell HTTP1.1
Host: localhost:8080
```
##### Request Body
| 이름       | 타입      | Description |
|----------|---------|-------------|
| title    | String  | 판매글 제목      |
| method   | String  | 거래 방식       |
| proposal | boolean | 가격 제안 여부    |
| price    | double  | 가격          |
| text     | String  | 물건 자세한 설명   |
| location | String  | 거래 장소       |
| memberId | int     | 작성자 id 값    |
-----------------------------------
#### Response

##### Response Body
| status | 201 Created |
|--------|-------------|

```json

```