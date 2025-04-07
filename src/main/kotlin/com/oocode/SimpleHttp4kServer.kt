package com.oocode

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer

val app: HttpHandler = routes(
    "/" bind GET to {
        val queries = it.queries("q")
        Response(OK).body(queries.firstOrNull()?.let { question ->
            println("question = ${question}")
            Answerer().answerFor(question) } ?: HomePage.HTML)
    }
)

fun main() {
    val server = SimpleHttp4kServer.http4kServer.start()
    println("Server started on " + server.port())
}

object SimpleHttp4kServer {
    val http4kServer = app.asServer(SunHttp(8124))
}

private object HomePage {
    val HTML = """
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Simple http4k Web Service</title>
        <script src="https://unpkg.com/htmx.org@2.0.4"></script>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #141414;
                color: white;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                flex-direction: column;
            }
            h1 {
                font-size: 2.5rem;
                margin-bottom: 20px;
            }
            .container {
                text-align: center;
                max-width: 600px;
                width: 100%;
            }
            .question-list {
                margin-top: 30px;
                list-style: none;
                padding: 0;
                font-size: 1.2rem;
                margin-bottom: 30px;
            }
            .question-list li {
                margin: 10px 0;
                font-weight: bold;
                color: #ff6347;
            }
            .form-control {
                padding: 10px;
                font-size: 1rem;
                border: 2px solid #333;
                background-color: #333;
                color: white;
                width: 100%;
                max-width: 500px;
                margin: 0 auto;
                border-radius: 5px;
                margin-bottom: 20px;
                outline: none;
            }
            .form-control:focus {
                border-color: #ff6347;
            }
            .result {
                margin-top: 20px;
                padding: 10px;
                background-color: #222;
                border-radius: 5px;
                color: #ff6347;
                font-size: 1.2rem;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Simple http4k Web Service</h1>
            <p>This is a minimal web app using http4k</p>
            <ul class="question-list">
                Try these questions:
                <li>What is your name?</li>
                <li>What is 2 + 2?</li>
            </ul>
            <input class="form-control" 
                   type="search"
                   name="q" 
                   placeholder="Ask a question ..."
                   hx-get="/"
                   hx-params="*"
                   hx-trigger="input changed delay:500ms, keyup[key=='Enter'], load"
                   hx-target="#search-results">
            <div class="result">Result of your query is: <span id="search-results"></span></div>
        </div>
    </body>
</html>""".trim()
}
