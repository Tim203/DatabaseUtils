dependencies {
    implementation(projects.databaseCommon)
    implementation(projects.databaseSql)
    implementation(projects.databaseMongo)

    compileOnly(libs.checker.qual)

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}