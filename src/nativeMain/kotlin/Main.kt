import sdl.*
import dsl.window
import data.Size
import data.Color

fun main() = window(
    "text editor",
    Size(800, 600)
) {
    renderLoop { canvas ->
        onEvent(SDL_QUIT) { stop() }
        canvas.clear(Color.rgb(0x18, 0x18, 0x18))
    }
}