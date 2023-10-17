plugins {
  id("org.jetbrains.kotlin.multiplatform").version("1.9.20-RC")
}

kotlin {
  jvm()
  macosArm64()
  iosArm64()
  js(IR) {
    nodejs()
  }

  applyDefaultHierarchyTemplate {
    group("common") {
      group("concurrent") {
        group("apple")
        withJvm()
      }
    }
  }

  sourceSets.getByName("commonMain") {
    dependencies {
      implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    }
  }
}