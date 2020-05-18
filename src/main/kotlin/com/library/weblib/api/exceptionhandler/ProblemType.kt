package com.library.weblib.api.exceptionhandler

/**
 *
 * Enum to represent some problems can occur in api
 *
 */
enum class ProblemType(path: String, var title: String) {
    RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
    ENTITY_IN_USE("/entity-in-use", "Entity in use"),
    BUSINESS_ERROR("/business-error", "Business ruler violation"),
    INVALID_PROPERTY("/Invalid-property", "Invalid property"),
    INCOMPREHENSIBLE_MESSAGE("/incomprehensible-message", "Incomprehensible message"),
    INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"),
    INVALID_DATA("/invalid-data", "Invalid data"),
    ACCESS_DENIED("/access-denied", "Access denied"),
    SYSTEM_ERROR("/system-error", "System error");

    var uri = ""

    init {
        this.uri = String.format("https://weblib.com.br%s", path)
    }

}