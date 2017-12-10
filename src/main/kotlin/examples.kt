import com.athaydes.rawhttp.core.RawHttp
import com.athaydes.rawhttp.core.body.FileBody
import com.athaydes.rawhttp.core.client.TcpRawHttpClient
import java.io.File
import java.net.InetAddress
import java.net.Socket

val examples = mapOf(
        "basic" to ::basicGetRequestExample,
        "raw" to ::completelyRawHttpRequest,
        "client" to ::getRequestWithClientExample,
        "fix" to ::fixGetRequestExample,
        "response" to ::responseExample,
        "file" to ::fileBodyExample
)

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        error("Please select an example to run! Options are: ${examples.keys}")
    }
    val example = examples[args.first()] ?:
            error("Example does not exist. Options are: ${examples.keys}")

    example()
}

fun basicGetRequestExample() {
    println("Sending basic GET request")

    val http = RawHttp()

    val request = http.parseRequest("""
        GET / HTTP/1.1
        Host: headers.jsontest.com
        Accept: application/json
    """.trimIndent())

    println(request)

    val response = Socket(InetAddress.getByName("date.jsontest.com"), 80).use {
        request.writeTo(it.getOutputStream())
        http.parseResponse(it.getInputStream()).eagerly()
    }

    println("-".repeat(40))
    println(response)

}

fun completelyRawHttpRequest() {
    println("Sending completely raw GET request")

    val request = "GET / HTTP/1.1\r\n" +
            "Host: date.jsontest.com\r\n" +
            "Accept: application/json\r\n" +
            "\r\n"

    println(request)

    val response = Socket(InetAddress.getByName("date.jsontest.com"), 80).use {
        it.getOutputStream().write(request.toByteArray())
        RawHttp().parseResponse(it.getInputStream()).eagerly()
    }

    println("-".repeat(40))
    println(response)

}

fun getRequestWithClientExample() {
    println("Sending GET request with client")

    val request = RawHttp().parseRequest("""
        GET / HTTP/1.1
        Host: headers.jsontest.com
        Accept: application/json
    """.trimIndent())

    println(request)

    val response = TcpRawHttpClient().send(request).eagerly()

    println("-".repeat(40))
    println(response)

}

fun fixGetRequestExample() {
    println("Specified GET Request:")
    println("GET http://example.com/path/to/resource")
    val request = RawHttp().parseRequest("GET http://example.com/path/to/resource")
    println("-".repeat(40))
    println("Fixed GET Request:")
    println(request)
}

fun responseExample() {
    val response = RawHttp().parseResponse("""
        HTTP/1.1 200 OK
        Server: RawHTTP
        Content-Length: 12

        Hello World!
    """.trimIndent())
    println(response.eagerly())
}

fun fileBodyExample() {

val response = RawHttp().parseResponse("""
    HTTP/1.1 200 OK
    Server: RawHTTP
""".trimIndent()
).replaceBody(FileBody(File("example.json"), "application/json", true))

println(response.eagerly())

}