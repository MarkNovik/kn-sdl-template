import kotlinx.cinterop.MemScope
import kotlinx.cinterop.alloc
import sdl.SDL_Rect

open class Rect(
    var x: Int, var y: Int,
    val width: Int, val height: Int
) {

    infix fun overlaps(other: Rect) = none(
        other.x > this.x + this.width,
        other.x + other.width < this.x,
        other.y > this.y + this.height,
        other.y + other.height < this.y
    )

    fun toSDL(memScope: MemScope): SDL_Rect = memScope.alloc<SDL_Rect> {
        x = this@Rect.x
        y = this@Rect.y
        w = width
        h = height
    }
}

class Square(
    x: Int, y: Int,
    val size: Int
) : Rect(x, y, size, size)