package com.lnsantos.elog

class ELog private constructor() : ELogContract() {

    init {
        throw RuntimeException("this is singleton, please not try make this")
    }

    enum class Level {
        VERBOSE,
        DEBUG,
        INFO,
        WARN,
        ERROR,
        ASSERT;

        fun getPriority() = ordinal + 0b10;
    }

    companion object Simple : ELogPack() {

    }
}

class Test {
    fun main() {
        ELog.a()
    }
}