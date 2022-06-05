package util

import sdl.SDL_GetError
import kotlinx.cinterop.toKString
import kotlin.system.exitProcess

fun sdlerror(): Nothing {
    println(SDL_GetError()?.toKString() ?: "Unknown SDL error")
    exitProcess(1)
}