import cnames.structs.SDL_Renderer
import kotlinx.cinterop.*
import sdl.*

value class Renderer(val ptr: CPointer<SDL_Renderer>) {

    inline var drawColor: Color
        get() = memScoped {
            val r = alloc<Uint8Var>()
            val g = alloc<Uint8Var>()
            val b = alloc<Uint8Var>()
            val a = alloc<Uint8Var>()
            SDL_GetRenderDrawColor(ptr, r.ptr, g.ptr, b.ptr, a.ptr)
            Color(r.value, g.value, b.value, a.value)
        }
        set(value): Unit = with(value) {
            SDL_SetRenderDrawColor(
                ptr,
                r, g, b, a
            )
        }


    fun fillRect(rect: Rect, color: Color) = memScoped {
        drawColor = color
        SDL_RenderFillRect(
            ptr,
            alloc<SDL_Rect> {
                x = rect.x
                y = rect.y
                w = rect.width
                h = rect.height
            }.ptr
        )
    }

    fun fillClear(color: Color) {
        drawColor = color
        SDL_RenderClear(ptr)
    }

    fun render() {
        SDL_RenderPresent(ptr)
    }
}