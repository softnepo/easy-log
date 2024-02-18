package com.lnsantos.elog

open class ELogContract {

    interface Logger {
        /**
         * show log level DEBUG
         * **/
        fun d(message: String?): Logger

        /**
         * show log level VERBOSE
         **/
        fun v(message: String?): Logger

        /**
         * show log level ERROR
         **/
        fun e(message: String?): Logger

        /**
         * show log level INFO
         **/
        fun i(message: String?): Logger

        /**
         * show log level WARN
         **/
        fun w(message: String?): Logger

        /**
         * show log level ASSERT
         **/
        fun a(message: String?): Logger
    }
}