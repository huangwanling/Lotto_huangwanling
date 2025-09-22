package tw.edu.pu.csim.tcyang.lotto

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import tw.edu.pu.csim.tcyang.lotto.ui.theme.LottoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LottoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Play(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Play(modifier: Modifier = Modifier) {
    // 儲存樂透號碼的狀態
    var lucky by remember {
        mutableStateOf((1..100).random())
    }

    // 取得當前 Context，以便顯示 Toast 訊息
    val context = LocalContext.current

    // 將第一個 pointerInput 應用在 Column 上，用來處理背景觸控
    Column(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        // 取得觸控的 x 和 y 座標
                        val x = offset.x
                        val y = offset.y

                        // 格式化座標字串，並顯示為 Toast
                        val toastText = "X: ${"%.2f".format(x)}, Y: ${"%.2f".format(y)}"
                        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
                    }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "樂透數字(1-100)為 $lucky",
            // 將第二個 pointerInput 獨立應用在 Text 上，用來處理數字加減
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        // 短按時，數字減1，並確保不小於1
                        lucky = (lucky - 1).coerceAtLeast(1)
                    },
                    onLongPress = {
                        // 長按時，數字加1，並確保不大於100
                        lucky = (lucky + 1).coerceAtMost(100)
                    }
                )
            }
        )

        Button(
            onClick = { lucky = (1..100).random() }
        ) {
            Text("重新產生樂透碼")
        }

        Text(text = "黃婉凌個人共同編輯程式")
    }
}