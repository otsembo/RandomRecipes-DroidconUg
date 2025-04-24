package com.rutubishi.randomrecipes.data.network

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import java.util.concurrent.TimeUnit

actual fun clientEngine(): HttpClientEngine? {
    return OkHttpEngine(config = OkHttpConfig().apply {
        this.config {
            callTimeout(5, TimeUnit.SECONDS)
        }
    })
}