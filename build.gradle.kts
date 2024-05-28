plugins {
    id("java")
    id("maven-publish")
    id("signing")
}

group = "io.github.lambdatest"
description = "lambdatest-java-sdk"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.apache.httpcomponents:httpclient:4.5.14")
    implementation("org.seleniumhq.selenium:selenium-java:4.21.0")
    implementation("com.google.code.gson:gson:2.11.0")
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            pom {
                name.set("LambdaTest Java SDK")
                description.set("A Java SDK for LambdaTest services.")
                url.set("https://github.com/lambdatest/lambdatest-java-sdk")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("shahnawazsk")
                        name.set("Shahnawaz Sk")
                        email.set("shahnawaz@lambdatest.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/lambdatest/lambdatest-java-sdk.git")
                    developerConnection.set("scm:git:ssh://github.com:lambdatest/lambdatest-java-sdk.git")
                    url.set("https://github.com/lambdatest/lambdatest-java-sdk")
                }
            }
        }
    }

    repositories {
         maven {
             name = "sonatype"
             url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
             credentials(PasswordCredentials::class)
         }
         maven {
             name = "sonatypeSnapshots"
             url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
             credentials(PasswordCredentials::class)
         }
    }
}

val sign: String? by project

if (!sign.isNullOrBlank()) {
    signing {
        sign(publishing.publications["maven"])
    }
}
