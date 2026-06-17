package ru.plumsoftware.weatherforecastru.data.models.location

sealed interface LocationDetectionResult {
    data class Success(val location: Location) : LocationDetectionResult

    data object VpnActive : LocationDetectionResult

    data object PermissionDenied : LocationDetectionResult

    data object LocationDisabled : LocationDetectionResult

    data object LocationUnavailable : LocationDetectionResult
}
