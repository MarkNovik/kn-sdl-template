import kotlinx.cinterop.alloc
import kotlinx.cinterop.ptr
import sdl.*

const val WINDOW_WIDTH = 800
const val WINDOW_HEIGHT = 600
const val PLATE_WIDTH = 10
const val PLATE_HEIGHT = 100
const val PLATE_MARGIN = 30
const val PONG_SIZE = 20

val BACKGROUND_COLOR = Color(0xFF2f3e46u)
val PONG_COLOR = Color(0xFFdad7cdu)
val PLATE_COLOR = Color(0xFF84a98cu)

fun main() = sdl("Ping-Pong", WINDOW_WIDTH, WINDOW_HEIGHT) { renderer, keyboard ->
    var pongDx = 5
    var pongDy = 7
    var plateDy = 0
    var quit = false
    var paused = false

    val square = Square(
        WINDOW_WIDTH / 2,
        WINDOW_HEIGHT / 2,
        20
    )

    val plate = Rect(
        PLATE_MARGIN,
        WINDOW_HEIGHT / 2 - PLATE_HEIGHT,
        PLATE_WIDTH,
        PLATE_HEIGHT
    )

    val opponent = Rect(
        WINDOW_WIDTH - (PLATE_MARGIN + PLATE_WIDTH),
        WINDOW_HEIGHT / 2 - PLATE_HEIGHT,
        PLATE_WIDTH,
        PLATE_HEIGHT
    )

    val xs = 0..(WINDOW_WIDTH - PONG_SIZE)
    val ys = 0..(WINDOW_HEIGHT - PONG_SIZE)

    while (!quit) {
        val event = alloc<SDL_Event>()
        while (SDL_PollEvent(event.ptr) != 0) {
            when (event.type) {
                SDL_QUIT -> {
                    quit = true
                    break
                }
                SDL_KEYDOWN -> {
                    //println(SDL_GetScancodeName(event.key.keysym.scancode)?.toKString())
                    when (event.key.keysym.scancode) {
                        SDL_SCANCODE_ESCAPE -> {
                            quit = true
                            break
                        }
                        SDL_SCANCODE_SPACE -> {
                            paused = !paused
                        }
                        SDL_SCANCODE_S -> plateDy = 10
                        SDL_SCANCODE_W -> plateDy = -10
                    }
                }
                SDL_KEYUP -> {
                    when (event.key.keysym.scancode) {
                        SDL_SCANCODE_S, SDL_SCANCODE_W -> plateDy = 0
                    }
                }
            }
        }
        plate.y = (plate.y + plateDy).coerceIn(0..(WINDOW_HEIGHT - plate.height))

        if (!paused) {
            square.x = (square.x + pongDx)
            square.y = (square.y + pongDy)
            if (square.x !in xs || square overlaps plate || square overlaps opponent) {
                pongDx *= -1
            }
            if (square.y !in ys) {
                pongDy *= -1
            }
        }
        square.x = square.x.coerceIn(xs)
        square.y = square.y.coerceIn(ys)
        opponent.y = square.y - PLATE_HEIGHT / 2
        opponent.y = opponent.y.coerceIn(0..(WINDOW_HEIGHT - PLATE_HEIGHT))
        renderer.fillClear(BACKGROUND_COLOR)
        renderer.fillRect(plate, PLATE_COLOR)
        renderer.fillRect(opponent, PLATE_COLOR)
        renderer.fillRect(square, PONG_COLOR)
        renderer.render()
        SDL_Delay(1000 / 60)
    }
}