# visual-android-sdk-sample

# aar
## libs/
    visual-ibk-ibkreceipt-2.0.5.aar
    visual-ibk-shared-2.0.5.aar
    visual-ibk-transmsparser-2.0.5.aar
    visual-ibk-visual-third-party-2.0.5.aar
    visual-ibk-visualbase-2.0.5.aar
    visual-ibk-visualibk-2.0.5.aar
    visual-ibk-webui-2.0.5.aar

```gradle 
dependencies {
    implementation fileTree(include: ['*.aar'], dir: 'libs')
    // Log
    implementation 'com.jakewharton.timber:timber:4.7.1'
    implementation "com.tenqube:notiparser:0.0.7"
    implementation 'androidx.preference:preference:1.2.0'
 
    // Network
    implementation "com.squareup.retrofit2:retrofit:2.4.0"
    implementation "com.squareup.retrofit2:converter-gson:2.3.0"
    implementation 'com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2'
    implementation "com.squareup.okhttp3:okhttp-urlconnection:3.8.1"
    implementation "com.squareup.okhttp3:logging-interceptor:3.11.0"
    implementation 'com.squareup.okhttp3:okhttp:3.12.0'
    implementation 'com.google.code.gson:gson:2.8.5'

    // Android View
    implementation 'androidx.core:core-ktx:1.0.0'
    implementation 'androidx.appcompat:appcompat:1.0.0'
    implementation 'com.google.android.material:material:1.0.0'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    implementation 'org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.71'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.2.1'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.1'
    implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.3.0'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0'
    implementation 'androidx.activity:activity-ktx:1.0.0'
    implementation 'androidx.fragment:fragment-ktx:1.0.0'
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'

    // Google Ad
    implementation 'com.google.android.gms:play-services-ads:15.0.1'

    // Room
    implementation 'androidx.room:room-runtime:2.2.5'
    kapt 'androidx.room:room-compiler:2.2.5'
    implementation 'androidx.room:room-ktx:2.2.5'
}
```
# aar 아닐경우
# gradle
```gradle
dependencies {
    implementation fileTree(include: ['*.aar'], dir: 'libs')
    implementation 'com.tenqube:visualibk:0.0.1'
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

