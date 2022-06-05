package dsl

import data.Color
import kotlinx.cinterop.CPointer
import sdl.*

value class Canvas(val ptr: CPointer<SDL_Renderer>) {

    private fun setColor(color: Color?) {
        color?.let { (r, g, b, a) -> SDL_SetRenderDrawColor(ptr, r, g, b, a) }
    }

    fun clear(color: Color? = null) {
        setColor(color)
        SDL_RenderClear(ptr)
    }

    fun render() {
        SDL_RenderPresent(ptr)
    }
}
