# Front ends
There are different front ends based on the role of the user using them:

## Web
- MUST be reactive
- MUST support different languages.
- MUST serve as an example for third party platforms to integrate with the `VerificationServer`

### `Initiator` web front end
- MUST drive the user through the `VerificationSession`.
- MUST allow `Initiator`s to provide details for the `VerificationSession`. 
- MUST allow `Initiator`s to see the status of their `VerificationSession`. 
- MUST be secured based on a token bound to the `VerificationSession` generated in the `VerificationServer`. 
and provided via the `EntryPoint`.

### `Verifier` web front end
- MUST present available `VerificationSession`s to the `Verifier`
- MUST allow `Verifier`s to join `VerificationSession` by creating a `VerificationConversation`
- MUST provide `Verifier`s a view of the `VerificationSessionDetails` through their `VerificationConversation`
- MUST present `Channel` link to start the `VerificationConversation` with the `Initiator`
- MUST allow `Verifier` input the result of the `VerificationConversation`

### `Checker` iOS App
- MUST present available `VerificationResult` to the `Checker`
- MUST be flexible to allow different types of JSON structures as each `VerificationDetails` may be different
- MUST allow logging in and out of the system

## Bot
### `Initiator` Bot
`Initiator` based front end.

### `Verifier` Bot (TBC)
`Verifier` based front end

## iOS
### `Initiator` iOS App
### `Verifier` iOS App
### `Checker` iOS App

## Android
### `Initiator` Android App
### `Verifier` Android App