import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import sdl.*

fun main() = memScoped {
    if (SDL_Init(SDL_INIT_VIDEO) < 0) //sdlError()
    defer { SDL_Quit() }

    val window = SDL_CreateWindow("title", 0, 50, 800, 600, SDL_WINDOW_RESIZABLE) //?: sdlError()
    defer { SDL_DestroyWindow(window) }

    val renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED) //?: sdlError()
    defer { SDL_DestroyRenderer(renderer) }
    SDL_SetRenderDrawColor(renderer, 0x18, 0x18, 0x18, 0xFF)
    SDL_RenderClear(renderer)
    SDL_RenderPresent(renderer)
    while (true) {
        val event = alloc<SDL_Event>()
        while (SDL_PollEvent(event.ptr) != 0) {
            if (event.type == SDL_QUIT) return@memScoped
        }
    }
}