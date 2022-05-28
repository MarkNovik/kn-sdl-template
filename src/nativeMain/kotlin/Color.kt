import kotlinx.cinterop.MemScope
import kotlinx.cinterop.alloc
import sdl.SDL_Color

value class Color(private val hex: UInt) {
    constructor(r: UInt, g: UInt, b: UInt, a: UInt = 0xFFu) : this(
        (a shl 24) + (r shl 16) + (g shl 8) + b
    )

    constructor(r: Int, g: Int, b: Int, a: Int = 0xFF) : this(r.toUInt(), g.toUInt(), b.toUInt(), a.toUInt())

    constructor(r: UByte, g: UByte, b: UByte, a: UByte = 0xFFu) : this(r.toInt(), g.toInt(), b.toInt(), a.toInt())

    val r: UByte get() = (hex shr 16).toUByte()
    val g: UByte get() = (hex shr 8).toUByte()
    val b: UByte get() = (hex shr 0).toUByte()
    val a: UByte get() = (hex shr 24).toUByte()

    inline operator fun component1() = r
    inline operator fun component2() = g
    inline operator fun component3() = b
    inline operator fun component4() = a

    inline fun toSDL(memScope: MemScope): SDL_Color =
        memScope.alloc<SDL_Color> {
            val (r, g, b, a) = this@Color
            this.r = r
            this.g = g
            this.b = b
            this.a = a
        }
}