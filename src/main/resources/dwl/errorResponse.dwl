%dw 2.0
output application/json
---
error: {
	errorNamespace: error.errorType.namespace,
	errorType: error.errorType.identifier,
	errorMessage: error.cause.message,
	errorDescription: error.description,
	errorCode: vars.httpStatus
}