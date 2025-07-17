package arm.project.petunio.viewmodels.states

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(data: T? = null, message: String?): Resource<T>(data, message)
    class Fetching<T>: Resource<T>()
    class Empty<T>: Resource<T>()
}