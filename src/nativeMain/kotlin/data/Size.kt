package data

value class Size private constructor(private val packed: Long) {
    val width: Int get() = (packed shr 32).toInt()
    val height: Int get() = packed.toInt()

    constructor(width: Int, height: Int): this((width.toLong() shl 32) + height)

    inline operator fun component1() = width
    inline operator fun component2() = height
}