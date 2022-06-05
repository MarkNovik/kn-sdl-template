package dsl

import sdl.*
import kotlinx.cinterop.*
import data.Size
import data.Position
import kotlin.Result
import util.sdlerror

value class Window(val ptr: CPointer<SDL_Window>) {
    val size: Size get() {
        TODO()
    }

    val position: Position get() {
        TODO()
    }

    fun renderLoop(
        block: RenderLoop.(Canvas) -> Unit
    ) {
        val renderer = SDL_CreateRenderer(ptr, -1, SDL_RENDERER_ACCELERATED or SDL_RENDERER_PRESENTVSYNC) ?: sdlerror()
        val canvas = Canvas(renderer)
        val renderLoop = RenderLoop()

        val arena = Arena()
        val event = arena.alloc<SDL_Event>()

        while(renderLoop.isRunning) {
            while (SDL_PollEvent(event.ptr) != 0) {
                renderLoop.checkEvent(event)
            }
            canvas.clear()
            block(renderLoop, canvas)
            canvas.render()
        }

        arena.clear()
        SDL_DestroyRenderer(canvas.ptr)
    }
}

inline fun window(
    title: String,
    size: Size,
    position: Position = Position.Centered,
    sdlFlags: UInt = 0u,
    block: Window.() -> Unit
) {
    if (SDL_Init(SDL_INIT_EVERYTHING) < 0) sdlerror()
    val window = SDL_CreateWindow(
        title,
        position.x,
        position.y,
        size.width,
        size.height,
        sdlFlags
    ) ?: sdlerror()

    block(Window(window))
    SDL_DestroyWindow(window)
    SDL_Quit()
}