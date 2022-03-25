# Verification result

`VerificationResult`s contain the following information:
- status: [pending|successful|failed|revoked]
- verified user: Unique user id assigned by the `VerificationServer`
- verified by: Unique user id assigned by the `VerificationServer`
- verified through: `Channel` through which the `VerificationConversation` took place.
- expires: Time of expiration of the given verification result
- self: URL referencing this verification result
- signature: `VerificationServer`'s server signature of the above information with its private key.


`VerificationResult`s MUST be transformed to different formats based on the `Channel`:
- `QRCode`s: to allow others to scan it and, if authorized, to see the details.
- `JSON`: to allow interoperability with [`EntryPoint`](entry_points.md)s and/or [`FrontEnd`](frontends.md)s
