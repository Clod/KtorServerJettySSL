# embedded_ssl_server

This project is a Ktor server application demonstrating how to enable SSL (HTTPS) using Jetty, Tomcat, or Netty server engines.

## Technologies Used

- [Kotlin](https://kotlinlang.org/) - Programming language
- [Ktor](https://ktor.io/) - Asynchronous framework for creating microservices, web applications, and more
- [Gradle](https://gradle.org/) - Build automation tool
- [Jetty](https://www.eclipse.org/jetty/), [Tomcat](https://tomcat.apache.org/), [Netty](https://netty.io/) - Server engines for Ktor
- [SSL/TLS](https://en.wikipedia.org/wiki/Transport_Layer_Security) - Cryptographic protocols to secure communication

## Prerequisites

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) - Required to run Kotlin and Gradle
- [Gradle](https://gradle.org/install/) - Build automation tool

## Setup and Build

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd embedded_ssl_server
   ```

2. **Build the application:**
   ```bash
   ./gradlew shadowJar
   ```
   This command builds a fat JAR file containing all dependencies in `build/libs/embedded_ssl_server-1.0-SNAPSHOT-all.jar`.

## SSL Certificate Generation and Configuration

This project is configured to use SSL for HTTPS. You can generate SSL certificates using [ZeroSSL](https://zerossl.com/) (as suggested in the project notes) or [Let's Encrypt](https://letsencrypt.org/).

### Using ZeroSSL

1. **Request certificates from ZeroSSL:**
   - Go to [ZeroSSL](https://zerossl.com/) and follow their instructions to generate free SSL certificates.
   - ZeroSSL provides certificates in PEM format.

2. **Convert certificates to JKS keystore:**
   - The project uses a JKS keystore (`test.jks`) to store SSL certificates.
   - Follow these steps to create a JKS keystore from the PEM certificates and private key:

     a. **Concatenate public certificate and CA bundle:**
        ```bash
        Get-Content certificate.crt, ca_bundle.crt | Set-Content full_chain.crt
        ```
        (On Linux/macOS, use `cat certificate.crt ca_bundle.crt > full_chain.crt`)

     b. **Convert to PKCS12 format (including private key):**
        ```bash
        openssl pkcs12 -export -in full_chain.crt -inkey private.key -out keystore.p12 -name "vcsinc"
        ```
        Replace `private.key`, `certificate.crt`, and `ca_bundle.crt` with your actual certificate files.

     c. **Import PKCS12 keystore to JKS:**
        ```bash
        keytool -importkeystore -srckeystore keystore.p12 -srcstoretype pkcs12 -destkeystore test.jks
        ```

3. **Place keystore in `build` directory:**
   - Copy the generated `test.jks` file to the `build` directory of the project.

### Update hosts file (for local testing)

For local testing, you can modify your hosts file to map a domain name to your local machine:

1. **Edit hosts file:**
   - **Windows:** `c:\Windows\System32\Drivers\etc\hosts`
   - **Linux/macOS:** `/etc/hosts`

2. **Add the following line:**
   ```
   127.0.0.1 vcsinc.com.ar
   ```
   This maps `vcsinc.com.ar` to your local machine (localhost).

## Running the application

```bash
java -jar build/libs/embedded_ssl_server-1.0-SNAPSHOT-all.jar
```

Access the application in your browser:

- **HTTP:** `http://localhost:8080/`
- **HTTPS:** `https://vcsinc.com.ar:8443/` (or `https://localhost:8443/` if you didn't update hosts file)

## Notes

- The `LEER.txt` file in the project root contains additional notes and commands related to SSL certificate generation and configuration.
- The JKS keystore format is a proprietary format. Consider migrating to PKCS12, which is an industry-standard format. You can convert JKS to PKCS12 using the following command:
  ```bash
  keytool -importkeystore -srckeystore test.jks -destkeystore test.jks -deststoretype pkcs12
  ```

---

**Disclaimer:** This README.md is auto-generated based on project analysis and available information. Please verify and update the information as needed.
