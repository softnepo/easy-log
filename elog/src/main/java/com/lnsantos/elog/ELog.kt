package com.lnsantos.elog

class ELog private constructor() {
    init {
        throw RuntimeException("this is singleton, please not try make this")
    }
}