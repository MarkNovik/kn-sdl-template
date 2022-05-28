import kotlinx.cinterop.MemScope
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import sdl.*

inline fun sdlError(msg: String): Nothing = error("$msg: ${SDL_GetError()?.toKString() ?: "Unknown"}")

inline fun sdl(title: String, width: Int, height: Int, block: MemScope.(Renderer, Keyboard) -> Unit) = memScoped {
    if (SDL_Init(SDL_INIT_VIDEO) < 0) sdlError("SDL INIT ERROR")
    defer { SDL_Quit() }

    val window =
        SDL_CreateWindow(title, 0, 50, width, height, SDL_WINDOW_RESIZABLE) ?: sdlError("SDL WINDOW CREATING ERROR")
    defer { SDL_DestroyWindow(window) }

    val renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED) ?: sdlError("SDL RENDERER CREATING ERROR")
    defer { SDL_DestroyRenderer(renderer) }

    val keyboard = SDL_GetKeyboardState(null) ?: sdlError("SDL KEYBOARD STATE GETTING")

    block(Renderer(renderer), Keyboard(keyboard))
}

fun none(vararg bools: Boolean): Boolean {
    for (bool in bools) {
        if (bool) return false
    }
    return true
}