# Entry points
Documentation about what an entry point is, what is required for its registration, and how it interacts with the users and the Verification Server.

An entry point represents an entity that MUST register in order to gain credentials to access the system.
An entry point MUST enable communication from the `Verification Server` with the `Initiator`.
An entry point MUST drive the `Initiator` through the verification process.
An entry point MUST collect from the `Initiator` the following information for the `Verification Server`:
- `user identifier` belonging to the `EntryPoint`'s universe (e.g. username for Telegram)
- user preferences
  - language
  - communication channels (e.g. Telegram, Physical, WhatsApp, etc...)
  - location (optional)
  - ip (optional)

An entry point SHOULD provide caching to save on traffic from the `Verification Server`.

An entry point is responsible for providing one or more [`Channel`](channel.md)'s to enable 
communication between the `Initiator` and the `Verifier`.

## Entrypoint endpoints
- [`PUT /api/{userId}`](): receives `VerificationDetails` from the `VerificationServer`.
- [`POST /api/{userId}/notification`](): receives availability requests from `VerificationServer` to wake up the user.
- [`POST /api/{userId}/conversation`](): receives the conversation details to allow the `Initiator` to join it.
- [`PUT /api/{userId}/result`](): receives the results of the verification for the `Initiator` to keep.

## Entrypoint dependencies
- `VerificationServer`: to complete the verification process.
- `Channel`: to allow the `Initiator` to join a `VerificationConversation`


## Telegram Entry Point
Using Telegram to start a `VerificationSession`

## Google Entry Point
Using Google Authentication to start a `VerificationSession`

## Facebook Entry Point
Using Facebook to start a `VerificationSession`

## Other parties...