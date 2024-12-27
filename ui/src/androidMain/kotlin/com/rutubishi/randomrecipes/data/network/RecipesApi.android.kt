package com.rutubishi.randomrecipes.data.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.engine.okhttp.OkHttpEngine

actual fun clientEngine(): HttpClientEngine? = OkHttpEngine(config = OkHttpConfig())
