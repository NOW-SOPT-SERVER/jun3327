### 1. 멤버 생성 API
- 사용자의 정보를 받아 Member 객체 생성 후 DB에 저장한다.  
### Request

#### Path Parameter

#### Request Header
```
POST /api/v1/member HTTP1.1
Host: localhost:8080
```
#### Request Body
| 이름   | 타입  | Description |
|------|-----|-------------|
| name | String | 사용자 이름      |
| part | String | 사용자 소속 파트   |
| age  | int | 사용자 나이      |
-----------------------------------
### Response 

#### Response Body
| status | 201 Created |
|--------|-------------|

```json

```

### 2. 멤버 단건 조회 API
- 사용자의 id를 입력 받아 해당 id 값의 멤버 정보를 반환한다. 
### Request

#### Path Parameter
- Path variables

| key      | value  | Description |
|----------|--------|--------|
| memberId | int    | 사용자 id |


#### Request Header
```
GET /api/v1/member HTTP1.1
Host: localhost:8080
```
#### Request Body

-----------------------------------
### Response

#### Response Body
| status | 200 Ok |
|--------|--------|

```json
{
    "name": "김환준",
    "part": "SERVER",
    "age": 22
}
```

### 3. 멤버 전체 목록 조회 API
- 등록되어 있는 모든 멤버 정보들을 List로 반환해준다. 
### Request

#### Path Parameter


#### Request Header
```
GET /api/v1/member/list HTTP1.1
Host: localhost:8080
```
#### Request Body

-----------------------------------
### Response

#### Response Body
| status | 200 Ok |
|--------|--------|

```json
[
  {
    "name": "김환준",
    "part": "SERVER",
    "age": 22
  },
  {
    "name": "김환중",
    "part": "WEB",
    "age": 23
  },
  {
    "name": "김황준",
    "part": "PLAN",
    "age": 24
  }
]
```


### 4. 멤버 단건 삭제 API
- 특정 id에 해당하는 멤버 정보를 DB에서 삭제한다.
### Request

#### Path Parameter
- Path variables

| key      | value  | Description |
|----------|--------|--------|
| memberId | int    | 사용자 id |

#### Request Header
```
DELETE /api/v1/member HTTP1.1
Host: localhost:8080
```
#### Request Body

-----------------------------------
### Response

#### Response Body
| status | 204 No Content |
|--------|----------------|

```json

```
