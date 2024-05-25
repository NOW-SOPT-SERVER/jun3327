### 1. 물건 팔기 등록 API
- 사용자가 팔고 싶어하는 판매글의 제목, 거래 방식, 가격, 가격 제안 여부, 자세한 설명, 거래 희망 장소를 사용자로부터 입력받고, DB에 저장한다.
### Request

#### Path Parameter

#### Request Header
```
POST /api/v1/home/post/sell HTTP1.1
Host: localhost:8080
```
#### Request Body
| 이름 | 타입      | Description |
|----|---------|-------------|
| title    | String  | 판매글 제목      |
| method   | String  | 거래 방식       |
| proposal | boolean | 가격 제안 여부    |
| price    | double  | 가격          |
| text     | String  | 물건 자세한 설명   |
| location | String  | 거래 장소       |
| memberId | int     | 작성자 id 값    |
-----------------------------------
### Response

#### Response Body
| status | 201 Created |
|--------|-------------|

```json

```

### 2. 지역별 상품 목록 GET
- 사용자로부터 지역을 입력받아 그에 해당하는 상품 목록을 반환한다.
### Request

#### Query Parameter
| 이름       | 타입     | Description |
|----------|--------|-------------|
| location | String | 상품 등록 지역    |

#### Request Header

```
GET /api/v1/home/post/sell/list HTTP1.1
Host: localhost:8080
```
##### Request Body

-----------------------------------
#### Response

##### Response Body
| status | 200 Ok |
|--------|--------|

```json
{
   "status" : "200",
   "message" : "정상적으로 목록을 반환합니다",
   "data" : [
     {
       "id" : 1,
       "title" : "제목1",
       "price" : 1000,
       "location" : "지역",
       "likeCount" : 5,
       "uploadTime" : 10
     },
     {
       "id" : 2,
       "title" : "제목2",
       "price" : 12000,
       "location" : "지역2",
       "likeCount" : 6,
       "uploadTime" : 10
     }
   ]
}

```
- title: 글 제목
- price: 상품 가격
- location: 상품 등록 지역
- likeCount: 해당 상품 좋아요 수
- uploadTime: 상품이 등록되고나서 지난 시간(단위:minute)


### 3. 상품에 좋아요 POST API
- 사용자가 좋아요 버튼을 누르면 해당 상품에 좋아요 +1
### Request
 
#### Path Parameter
- Path variables

| key       | value  | Description |
|-----------|--------|-------------|
| sellingId | Long    | 등록글 Id      |


#### Request Header
| key      | value  | Description |
|----------|--------|-------------|
| memberId | Long    | 좋아요 사용자 Id  |

```
POST /api/v1/home/post/sell HTTP1.1
Host: localhost:8080
```
##### Request Body

-----------------------------------
#### Response

##### Response Body
| status | 201 Created |
|--------|-------------|

```json
{
    "status": 201,
    "messsage": "좋아요 추가 완료"
}
```