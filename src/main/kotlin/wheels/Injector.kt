package wheels

class Injector {

  inline fun <reified T, reified Y : T> bind() = bind(T::class.java, Y::class.java)

  fun <T, Y : T> bind(type: Class<T>, implement: Class<Y>) {
    bindings += type to implement
  }

  inline fun <reified T> inject() = inject(T::class.java)

  fun <T> inject(type: Class<T>): T {
    try {
      val clazz = bindings[type] ?: type
      val constructor = clazz.constructors.single()
      val arguments = constructor.parameters.map { inject(it.type) }
      val instance = constructor.newInstance(*arguments.toTypedArray())
      return type.cast(instance)
    } catch (e: Exception) {
      throw e as? InjectException ?: InjectException(e)
    }
  }

  private var bindings: Map<Class<*>, Class<*>> = mapOf()

}

class InjectException(e: Throwable) : RuntimeException(e)
