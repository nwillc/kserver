/*
 * Copyright 2018 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby
 * granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
 * INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN
 * AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
 * PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.kserver

import com.github.nwillc.ksvg.elements.SVG
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.title
import kotlinx.html.unsafe

fun main(args: Array<String>) {
    val server = embeddedServer(Netty, 8080) {
        routing {
            get("/") {
                val svg = SVG.svg {
                    height = "100"
                    width = "100"
                    defs {
                        g {
                            id = "circle1"
                            circle {
                                r = "20"
                                fill = "blue"
                                strokeWidth = "2"
                                stroke = "red"
                            }
                        }
                    }
                    for (i in 20..80 step 20)
                        use {
                            x = i.toString()
                            y = i.toString()
                            href = "#circle1"
                        }
                }
                call.respondHtml {
                    head {
                        title { +"Hello World" }
                    }
                    body {
                        h1 { +"Hello World" }
                        unsafe {
                            raw(svg.toString())
                        }
                    }
                }
            }
        }
    }
    server.start(wait = true)
}