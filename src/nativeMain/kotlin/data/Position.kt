package data

import sdl.SDL_WINDOWPOS_CENTERED

value class Position private constructor(private val packed: Long) {
    val x: Int get() = (packed shr 32).toInt()
    val y: Int get() = packed.toInt()

    constructor(x: Int, y: Int): this((x.toLong() shl 32) + y)

    inline operator fun component1() = x
    inline operator fun component2() = y

    companion object {
        val Centered: Position = Position(SDL_WINDOWPOS_CENTERED.toInt(), SDL_WINDOWPOS_CENTERED.toInt())
    }
}