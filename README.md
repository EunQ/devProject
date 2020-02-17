20.02.03

http://192.168.31.122:8197/swagger-ui.html



http://192.168.31.122:8197/api



### 회원관리 :person_with_pouting_face: - 업데이트 예정

- ##### addMember 

  - 회원가입 기능

  - POST

    

### 게시글 관리 :clipboard:

##### getBoard

- 모든 게시글 정보 가져옴

- GET

- Parameter

  - -

- Return value

  - List<Board> list

    ```
    [
      {
        "board_id": 0,
        "title": "string",
        "host": "string",
        "apply_start": "string(yyyy-MM-dd)",
        "apply_end": "string(yyyy-MM-dd)",
        "start": "string(yyyy-MM-dd)",
        "end": "string(yyyy-MM-dd)",
        "people_num": 0,
        "price": 0,
        "location": "string",
        "info": "string",
        "img": "string",
        "people_now": 0
      },
      .
      .
      .
      
    ]
    ```



##### getBoardByID

- 하나의 게시글 가져옴

- GET

- Parameter

  - int - board_id

- Return value

  - Board b

    ```
    {
      "apply_end": "string",
      "apply_start": "string",
      "board_id": 0,
      "end": "string",
      "host": "string",
      "img": "string",
      "info": "string",
      "location": "string",
      "people_now": 0,
      "people_num": 0,
      "price": 0,
      "start": "string",
      "title": "string"
    }
    ```



##### addBoard

- 게시글 추가

- POST

- Parameter

  - String - dto_str

    ```
    {
      "apply_end": "string(yyyy-MM-dd)",
      "apply_start": "string(yyyy-MM-dd)",
      "end": "string(yyyy-MM-dd)",
      "host": "string",
      "info": "string",
      "location": "string",
      "people_num": 0,
      "price": 0,
      "start": "string(yyyy-MM-dd)",
      "title": "string",
      "email": "string"
    }
    ```

  - File - file

- Return value

  - BoardNumberResult

    ```
    {
      "number" : 0
      "name" : "addBoard"
      "state" : "succ" or "fail"
    }
    ```

  

##### updateBoard

- 게시글 수정

- POST

- Parameter

  - String - dto_str

    ```
    {
      "board_id" : 0,
      "apply_end": "string(yyyy-MM-dd)",
      "apply_start": "string(yyyy-MM-dd)",
      "end": "string(yyyy-MM-dd)",
      "host": "string",
      "info": "string",
      "location": "string",
      "people_num": 0,
      "price": 0,
      "start": "string(yyyy-MM-dd)",
      "title": "string"
    }
    ```

  - File - file

- Return value

  - BoardNumberResult

    ```
    {
      "number" : 0,
      "name" : "updateBoard",
      "state" : "succ"
    }
    ```



##### deleteBoard

- 게시글 삭제

- POST

- Parameter

  - Post_board - dto

    ```
    {
      "board_id": 0,
      "email": "string",
    }
    ```

- Return value

  - BoardNumberResult

    ```
    {
      "number" : board_id(int),
      "name" : "deleteBoard",
      "state" : "succ" or "fail"
    }
    ```



##### searchBoard

- 게시글 검색

- GET

- Parameter

  - string - keyword

- Return value

  - List<Board> list

    ```
    [
      {
        "board_id": 0,
        "title": "string",
        "host": "string",
        "apply_start": "string(yyyy-MM-dd)",
        "apply_end": "string(yyyy-MM-dd)",
        "start": "string(yyyy-MM-dd)",
        "end": "string(yyyy-MM-dd)",
        "people_num": 0,
        "price": 0,
        "location": "string",
        "info": "string",
        "img": "string",
        "people_now": 0
      },
      .
      .
      .
      
    ]
    ```



##### getComment

- 게시글의 모든 댓글 목록 가져옴

- GET

- Parameter

  - int - board_id

- Return value

  - List<Comment> list

    ```
    [
      {
      "board_id": 0,
      "ccontent": "string",
      "cnum": 0,
      "email": "string"
      },
      .
      .
      .
      
    ]
    ```

    

##### addComment

- 댓글 추가

- POST

- Parameter

  - Comment - dto

    ```
    {
      "board_id": 0,
      "ccontent": "string",
      "email": "string"
    }
    ```

- Return value

  - BoardNumberResult

    ```
    {
      "number" : board_id(int),
      "name" : "addComment",
      "state" : "succ" or "fail"
    }
    ```



##### deleteComment

- 댓글 삭제

- GET

- Parameter

  - int - cnum

- Return value

  - BoardNumberResult

    ```
    {
      "number" : cnum(int),
      "name" : "deleteComment",
      "state" : "succ" or "fail"
    }
    ```



##### applyBoard

- 참가하기

- POST

- Parameter

  - Apply_board - dto

    ```
    {
      "board_id": 0,
      "email": "string"
    }
    ```

- Return value

  - BoardNumberResult

    ```
    {
      "number" : now_people(int),
      "name" : "applyBoard",
      "state" : "succ" or "fail"
    }
    ```

    

