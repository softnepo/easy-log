package com.lnsantos.elog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

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