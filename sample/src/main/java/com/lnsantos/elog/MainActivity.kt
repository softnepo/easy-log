package com.lnsantos.elog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lnsantos.elog.annotation.ELogExperimental
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ELogExperimental::class)
class MainActivity : AppCompatActivity(), ELog.Interception {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ELog.registerInterception(this)
            .initialization(true)

        ELog.i("Teste de log de info")
        ELog.d("Teste de log de debug")
        ELog.e("Teste de log de erro")
        ELog.w("Teste de log de warn")
        ELog.a("Teste de log de asset")
        ELog.v("Teste de log de verbose")
        GlobalScope.launch {
            launch { ELog.d(this@MainActivity, "teste 1") }
            launch { ELog.d(this@MainActivity, "teste 2") }
            launch { ELog.v(this@MainActivity, "teste 1") }
            launch { ELog.v("teste 2") }
        }
        ELog.d(this, "teste")
    }

    override fun onInterception(
        level: ELog.Level,
        message: String?,
        throwable: Throwable?
    ): ELog.Progress {

        if (level == ELog.Level.INFO) {
            return ELog.Progress.SKIP
        }

        return ELog.Progress.CONTINUE
    }
}