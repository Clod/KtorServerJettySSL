package com.example

import org.slf4j.LoggerFactory
import io.ktor.application.*
import io.ktor.network.tls.certificates.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
// import io.ktor.server.netty.*
// import io.ktor.server.jetty.*
import io.ktor.server.tomcat.*
import java.io.*
import java.security.KeyStore

fun main() {

//    val keyStoreFile = File("build/keystore.jks")
    val keyStoreFile = File("build/test.jks")
    val keystorePW = "foobar"
    val keystore: KeyStore = KeyStore.getInstance(keyStoreFile, keystorePW.toCharArray())
//    val keystore = generateCertificate(
//        file = keyStoreFile,
//        keyAlias = "sampleAlias",
//        keyPassword = "foobar",
//        jksPassword = "foobar"
//    )

    val environment = applicationEngineEnvironment {
        log = LoggerFactory.getLogger("ktor.application")
        connector {
            port = 8080
        }
        sslConnector(
            keyStore = keystore,
//            keyAlias = "sampleAlias",
            keyAlias = "vcsinc",
            keyStorePassword = { "foobar".toCharArray() },
            privateKeyPassword = { "foobar".toCharArray() }) {
            port = 8443
            keyStorePath = keyStoreFile
        }
        module(Application::module)
    }

    embeddedServer(Tomcat, environment).start(wait = true)
}

fun Application.module() {
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
    }
}
