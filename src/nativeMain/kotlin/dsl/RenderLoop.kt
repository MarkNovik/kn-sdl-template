package dsl

import sdl.*

typealias OnEvent = (SDL_Event) -> Unit

class RenderLoop {
    var isRunning = true
        private set
    private val eventReactions = mutableMapOf<SDL_EventType, OnEvent>()

    fun onEvent(type: SDL_EventType, block: OnEvent) {
        eventReactions[type] = block
    }

    fun checkEvent(event: SDL_Event) = eventReactions[event.type]?.invoke(event) ?: Unit

    fun stop() {
        isRunning = false
    }
}
