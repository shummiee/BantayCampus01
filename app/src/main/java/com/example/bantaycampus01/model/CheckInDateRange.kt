package com.example.bantaycampus01.model

enum class CheckInDateRange(val label: String) {
    TODAY("Today"),
    LAST_7("Last 7 days"),
    LAST_30("Last 30 days"),
    CUSTOM("Custom range")
}