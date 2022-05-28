import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.toKString
import sdl.*

fun main() = memScoped {
    if (SDL_Init(SDL_INIT_VIDEO) < 0) sdlError("SDL INIT ERROR")
    defer { SDL_Quit() }

    val window =
        SDL_CreateWindow("window", 0, 50, 800, 600, SDL_WINDOW_RESIZABLE) ?: sdlError("SDL WINDOW CREATING ERROR")
    defer { SDL_DestroyWindow(window) }

    val renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED) ?: sdlError("SDL RENDERER CREATING ERROR")
    defer { SDL_DestroyRenderer(renderer) }

    //val keyboard = SDL_GetKeyboardState(null) ?: sdlError("SDL KEYBOARD STATE GETTING")

    while (true) {
        val event = alloc<SDL_Event>()
        while (SDL_PollEvent(event.ptr) != 0) {
            if (event.type == SDL_QUIT) return@memScoped
        }
    }
}

inline fun sdlError(message: String): Nothing = error("$message: ${SDL_GetError()?.toKString()}")