package com.example.lockdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lockdemo.ui.theme.LockDemoTheme
import com.ttlock.bl.sdk.api.TTLockClient
import com.ttlock.bl.sdk.callback.ControlLockCallback
import com.ttlock.bl.sdk.constant.ControlAction
import com.ttlock.bl.sdk.entity.ControlLockResult
import com.ttlock.bl.sdk.entity.LockError


class MainActivity : ComponentActivity() {

    private val lockData: String? = null
    private val lockMac: String? = null

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LockDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    private fun ensureBluetoothIsEnabled() {
        if(!TTLockClient.getDefault().isBLEEnabled(this)){
            TTLockClient.getDefault().requestBleEnable(this)
        }
    }

    fun lock() {
        ensureBluetoothIsEnabled()
        TTLockClient.getDefault().controlLock(
            ControlAction.LOCK,
            lockData,
            lockMac,
            object : ControlLockCallback {
                override fun onControlLockSuccess(controlLockResult: ControlLockResult) {
                    Log.d(TAG, "lockSuccess")
                }

                override fun onFail(error: LockError) {
                    Log.e(TAG, "lockFail: " + error.errorMsg)
                }
            }
        )
    }

    fun unlock() {
        ensureBluetoothIsEnabled()
        TTLockClient.getDefault().controlLock(
            ControlAction.UNLOCK,
            lockData,
            lockMac,
            object : ControlLockCallback {
                override fun onControlLockSuccess(controlLockResult: ControlLockResult) {
                    Log.d(TAG, "unLockSuccess")
                }

                override fun onFail(error: LockError) {
                    Log.e(TAG, "unLockFail: " + error.errorMsg)
                }
            })
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LockDemoTheme {
        Greeting("Android")
    }
}