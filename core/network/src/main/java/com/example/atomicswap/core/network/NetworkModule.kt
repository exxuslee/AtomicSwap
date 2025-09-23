package com.example.atomicswap.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
	single {
		HttpClient(OkHttp) {
			install(ContentNegotiation) {
				json(Json { ignoreUnknownKeys = true; isLenient = true })
			}
		}
	}
}
