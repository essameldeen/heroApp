apply {
    from("$rootDir/library-build.gradle")
}
plugins {
    id(SqlDelight.plugin)
}
dependencies {

    "implementation"(project(Modules.heroDomain))

    "implementation"(Ktor.core)
    "implementation"(Ktor.clientSerialization)
    "implementation"(Ktor.android)
    "implementation"(SqlDelight.runtime)
}

sqldelight{
    database(name="HeroDatabase"){
        packageName="com.vodeg.hero_datasource"
        sourceFolders= listOf("sqldelight")
    }
}