import kotlinx.cinterop.CPointer
import kotlinx.cinterop.get
import sdl.SDL_Scancode
import sdl.Uint8Var

value class Keyboard(val ptr: CPointer<Uint8Var>) {
    infix fun hasPressed(scancode: SDL_Scancode): Boolean =
        ptr[scancode.toInt()] != 0u.toUByte()
}