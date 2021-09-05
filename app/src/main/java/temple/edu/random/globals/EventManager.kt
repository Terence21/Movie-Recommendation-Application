package temple.edu.random.globals

import java.util.*

abstract class EventManager<T> : EventListener {
    abstract fun subscribeToEvent(subscriber: T)
    abstract fun unsubscribeToEvent(unsubscriber: T)
}