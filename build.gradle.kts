import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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

fun KotlinProjectExtension.forEachCompilerOptions(block: KotlinCommonCompilerOptions.() -> Unit) {
  when (this) {
    is KotlinJvmProjectExtension -> compilerOptions.block()
    is KotlinAndroidProjectExtension -> compilerOptions.block()
    is KotlinMultiplatformExtension -> {
      targets.all {
        compilations.all {
          compilerOptions.configure {
            block()
          }
        }
      }
    }
    else -> error("Unknown kotlin extension $this")
  }
}

kotlin.forEachCompilerOptions {
  allWarningsAsErrors.set(true)
}