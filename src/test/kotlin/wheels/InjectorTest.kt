package wheels

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class InjectorTest {

  @Test
  fun inject() {
    val injector = Injector()
    injector.bind<X, XI>()
    injector.bind<Y, YI>()
    injector.bind<Z, ZI>()
    val x = injector.inject<X>()
    assertNotNull(x.y.z)
    assertTrue(x.y.z is ZI)
    assertTrue(x.y is YI)
    assertTrue(x is XI)
  }

}

interface X {
  val y: Y
}

interface Y {
  val z: Z
}

interface Z

class XI(override val y: Y) : X

class YI(override val z: Z) : Y

class ZI : Z
