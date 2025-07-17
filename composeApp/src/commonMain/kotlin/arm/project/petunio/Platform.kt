package arm.project.petunio

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform