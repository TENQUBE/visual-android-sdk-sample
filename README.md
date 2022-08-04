# visual-android-sdk-sample

# 빌드
https://visual-third-party.s3.ap-northeast-2.amazonaws.com/visualibk-0.0.1-alpha04.aar

# aar
## libs/
- visaul-ibk-receipt-0.0.1-alpha05.aar
- visual-ibk-0.0.1-alpha04.aar
- visual-third-party-1.6.8-ibk.aar

```gradle 
dependencies {
    implementation "com.tenqube:notiparser:0.0.7"
    implementation "com.tenqube:transmsparser:0.0.1-alpha05"
    implementation "com.tenqube:visualbase:0.0.1-alpha05"
    implementation "com.tenqube:webui:0.0.1-alpha05"
    implementation "com.tenqube:shared:0.0.1-alpha05"
}
```
# aar 아닐경우
# gradle
```gradle
dependencies {
    implementation fileTree(include: ['*.aar'], dir: 'libs')
    implementation 'com.tenqube:ibkreceipt:0.0.1-alpha05'
    implementation 'com.tenqube:ibk:1.6.8'
}
```

# 가계부
- VISUAL_API_KEY 정보 요청
```kotlin
  private fun startVisual() {
      // visual service 생성
      val visualService = VisualServiceImpl(
          this,
          VISUAL_API_KEY, // TODO api 키정보
          Constants.DEV, // TODO 레이어 정보 상용 배포시 Constants.PROD
          applicationContext.packageName // TODO 패키지 명
      )
      // IBKMainActivity.this 값을 통해 startActivityForResult로 호출합니다.
      // IBK user 고유 아이디 정보를 추가해 주세요.
      // getVisualPath() 함수를 통해 딥링크를 통해 들어온 값을 전달합니다.
      visualService.startVisual(
          this@MainActivity, ":userUniqueId", ""
      ) { signUpResult, msg -> } // TODO uid 및 딥링크 패스 넣어주기
  }
```

# 영수증
- RECEIPT_API_KEY 정보 요청
```kotlin
  private fun startReceipt() {
    VisualServiceBuilder()
        .activity(this)
        .apiKey(RECEIPT_API_KEY) // TODO 전달받은 API KEY 정보
        .layer(Layer.DEV) // TODO 개발 : Layer.DEV, 상용: Layer.PROD
        .notification(
            NotificationArg(
                R.drawable.ic_status, // TODO 알림 small_icon 정보
                "ibk-channel-id", // TODO 현재 사용 중인 알림 채널 아이디
                R.color.white // 색상코드 // TODO 알림 색상 코드
            )
        )
        .service(Service.IBK) // IBK 고정
        .build()
        .start(UserArg(":UID", 1987, VisualGender.MALE)) // TODO 사용자 고유 아이디, 생년, 성별 넣어주기
  }
```

# 테스트 방법
- 어플알림 연동 ON 
<img src="https://user-images.githubusercontent.com/15064370/182561946-d7bd4751-1707-4560-898a-f4305b23566f.jpg" width="200" height="400"/>
<img src="https://user-images.githubusercontent.com/15064370/182561930-8591fe23-f303-4c43-96fa-47230124588b.jpg" width="200" height="400"/>

- 테스트 할 단말기에 아래와 같은 문자를 보냅니다
```
`15881600;title;[Web발신] NH카드8*9* 승인 김*중 21,380원 체크 11/19 16:53 쿠팡102;2
```
- 모바일 영수증 및 알림이 뜹니다.
<img src="https://user-images.githubusercontent.com/15064370/182564090-2cf87cfc-2ec2-4350-9dba-5e89b455599a.jpg" width="200" height="400"/>

