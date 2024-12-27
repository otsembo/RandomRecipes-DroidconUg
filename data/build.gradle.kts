import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "RandomRecipeData"
        browser {}
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
        }
        wasmJsMain.dependencies {
        }
    }
}
