package io.ktorgraal.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val httpClient: java.net.http.HttpClient = java.net.http.HttpClient
        .newBuilder()
        .build()
    // Starting point for a Ktor app:
    routing {
        get("/") {
            val httpRequest: java.net.http.HttpRequest = java.net.http.HttpRequest.newBuilder().GET()
                .uri(java.net.URI.create("http://172.17.0.1:9090/test.txt")).build()
            val httpResponse: java.net.http.HttpResponse<String> =
                httpClient.send(httpRequest, java.net.http.HttpResponse.BodyHandlers.ofString())
            call.respondText(httpResponse.body())
        }
    }

}
