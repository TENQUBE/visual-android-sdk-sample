package com.tenqube.visualsample

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tenqube.ibkreceipt.*
import com.tenqube.visual_third.Constants
import com.tenqube.visual_third.VisualServiceImpl

class MainActivity : AppCompatActivity() {
    private lateinit var receiptService: VisualReceiptService
    private val VISUAL_API_KEY = "35FfM5fp0A7qloAq9qISm3gbTHJ5LXH726Qpfy5y" // TODO 가계부 api 키정보
    private val RECEIPT_API_KEY = "QjtqKhrWX63EY5zv3hE1P3HBQaos4SYNaGYXoVLJ" // TODO 영수증 api 키정보
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visual_main)
        createReceiptService()

        val ibk: Button = findViewById(R.id.ibk)
        checkPermission()
        ibk.setOnClickListener {
            startVisual()
        }
        val ibkReceipt: Button = findViewById(R.id.ibk_receipt)
        ibkReceipt.setOnClickListener {
            startReceipt()
        }
        val notiPopup: Button = findViewById(R.id.noti_popup)
        notiPopup.setOnClickListener {
            receiptService.startNotiPopup(this)
        }
    }

    /**
     * 기존 가계부
     */
    private fun startVisual() {
        // visual service 생성
        val visualService = VisualServiceImpl(
            this,
            VISUAL_API_KEY, // TODO api 키정보
            Constants.PROD, // TODO 레이어 정보 상용 배포시 Constants.PROD
            applicationContext.packageName // TODO 패키지 명
        )
        // IBKMainActivity.this 값을 통해 startActivityForResult로 호출합니다.
        // IBK user 고유 아이디 정보를 추가해 주세요.
        // getVisualPath() 함수를 통해 딥링크를 통해 들어온 값을 전달합니다.
        visualService.startVisual(
            this@MainActivity, ":userUniqueId", ""
        ) { signUpResult, msg -> } // TODO uid 및 딥링크 패스 넣어주기
    }

    private fun createReceiptService() {
        receiptService = VisualReceiptServiceBuilder()
            .context(this)
            .apiKey(RECEIPT_API_KEY) // TODO 전달받은 API KEY 정보
            .layer(Layer.PROD) // TODO 개발 : Layer.DEV, 상용: Layer.PROD
            .notification(
                NotificationArg(
                    R.drawable.ic_status, // TODO 알림 small_icon 정보
                    "ibk-channel-id", // TODO 현재 사용 중인 알림 채널 아이디
                    R.color.white // 색상코드 // TODO 알림 색상 코드
                )
            )
            .service(Service.IBK) // IBK 고정
            .build()
    }
    /**
     * 모바일 영수증
     */
    private fun startReceipt() {
        receiptService.start(UserArg(":UID", null, null)) // TODO 사용자 고유 아이디, 생년, 성별 넣어주기
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.READ_SMS
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    Manifest.permission.READ_SMS
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS),
                    0
                )
            }
        }
    }
}
