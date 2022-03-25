# Front ends
There are different front ends based on the role of the user using them:

## Web
### `Initiator` web front end
- MUST be reactive
- MUST support different languages.
- MUST drive the user through the `VerificationSession`.
- MUST allow `Initiator`s to provide details for the `VerificationSession`. 
- MUST allow `Initiator`s to see the status of their `VerificationSession`. 
- MUST be secured based on a token bound to the `VerificationSession` generated in the `VerificationServer`. 
and provided via the `EntryPoint`.

### `Verifier` web front end
- MUST be reactive.
- MUST support different languages
- MUST present available `VerificationSession`s to the `Verifier`
- MUST allow `Verifier`s to join `VerificationSession` by creating a `VerificationConversation`
- MUST provide `Verifier`s a view of the `VerificationSessionDetails` through their `VerificationConversation`
- MUST present `Channel` link to start the `VerificationConversation` with the `Initiator`
- MUST allow `Verifier` input the result of the `VerificationConversation`

## Bot
### `Initiator` Bot
`Initiator` based front end.

### `Verifier` Bot (TBC)
`Verifier` based front end

## iOS
### `Initiator` iOS App
### `Verifier` iOS App

## Android
### `Initiator` Android App
### `Verifier` Android App