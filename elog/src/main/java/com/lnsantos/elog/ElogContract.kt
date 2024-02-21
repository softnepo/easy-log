package com.lnsantos.elog

import java.lang.Exception

open class ELogContract {

    interface Logger {

        fun tag(tag: String): Logger

        /**
         * show log level DEBUG
         * **/
        fun d(message: String?): Logger

        /**
         * show log level DEBUG
         * **/
        fun d(exception: Throwable?): Logger

        /**
         * show log level DEBUG, with custom tag by class
         * **/
        fun d(clazz: Any? = null, message: String?): Logger

        /**
         * show log level DEBUG
         * **/
        fun d(clazz: Any? = null, exception: Throwable): Logger

        /**
         * show log level VERBOSE
         **/
        fun v(message: String?): Logger

        /**
         * show log level VERBOSE
         **/
        fun v(exception: Throwable): Logger

        /**
         * show log level VERBOSE
         **/
        fun v(clazz: Any? = null, message: String?): Logger

        /**
         * show log level VERBOSE
         **/
        fun v(clazz: Any? = null, exception: Throwable): Logger

        /**
         * show log level ERROR
         **/
        fun e(message: String?): Logger

        /**
         * show log level ERROR
         **/
        fun e(exception: Throwable): Logger

        /**
         * show log level ERROR
         **/
        fun e(clazz: Any? = null, message: String?): Logger

        /**
         * show log level ERROR
         **/
        fun e(clazz: Any? = null, exception: Throwable): Logger

        /**
         * show log level INFO
         **/
        fun i(message: String?): Logger

        /**
         * show log level INFO
         **/
        fun i(exception: Throwable): Logger

        /**
         * show log level ERROR
         **/
        fun i(clazz: Any? = null, message: String?): Logger

        /**
         * show log level ERROR
         **/
        fun i(clazz: Any? = null, exception: Throwable): Logger
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