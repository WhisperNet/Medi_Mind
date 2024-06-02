package com.example.medimind.data.model.api_response

data class RequestOTPResponse(
    val referenceNo: String,
    val statusCode: String,
    val statusDetail: String,
    val version: String
)