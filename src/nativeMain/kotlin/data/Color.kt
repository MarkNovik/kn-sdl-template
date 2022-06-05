package data

value class Color private constructor(private val packed: Int) {

    val r: UByte
        get() = (packed shr 16).toUByte()
    val g: UByte
        get() = (packed shr 8).toUByte()
    val b: UByte
        get() = packed.toUByte()
    val a: UByte
        get() = (packed shr 24).toUByte()

    inline operator fun component1() = r
    inline operator fun component2() = g
    inline operator fun component3() = b
    inline operator fun component4() = a

    companion object {
        fun rgb(r: Int, g: Int, b: Int) = Color((0xFF shl 24) + (r shl 16) + (g shl 8) + b)
        inline fun rgb(r: UByte, g: UByte, b: UByte) = rgb(r.toInt(), g.toInt(), b.toInt())
        fun rgba(r: Int, g: Int, b: Int, a: Int) = Color((a shl 24) + (r shl 16) + (g shl 8) + b)
        inline fun rgba(r: UByte, g: UByte, b: UByte, a: UByte) = rgba(r.toInt(), g.toInt(), b.toInt(), a.toInt())
    }
}
