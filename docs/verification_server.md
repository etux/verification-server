# Verification Server
Central component of the solution, responsible for:
- registering and managing `EntryPoint` [entrypoints](entry_points.md)
  - creating an access key for the `EntryPoint`
  - exchange of Public Keys between `EntryPoint` and `VerificationServer`
- supports the verification [protocol](./diagrams/sequence_diagram_verification.png)
- produces [`VerificationResult`](verification_result.md)s as proof of verification

## Verification Server endpoints
- entrypoints
  - [`POST /api/entrypoint`](http://localhost/path_to_generated_docs): registers an entry point.
  - [`GET /api/entrypoint`](): Retrieves the entrypoint details.
  - [`DELETE /api/entrypoint`](http://localhost/path_to_generated_docs): cancels an entry point.
- sessions
  - [`POST /api/sessions`](http://localhost/path_to_generated_docs): create verification session: endpoint to allow creating a verification session.
  - [`GET /api/sessions`](http://localhost/path_to_generated_docs): lists sessions relevant for the authenticated user.
  - [`DELETE /api/sessions/{sessionId}`](http://localhost/path_to_generated_docs): delete verification session: endpoint to cancel a verification session.
  - [`POST /api/sessions/{sessionId}/details`](http://localhost/path_to_generated_docs): add details to verification session: endpoint to add verification data to be evaluated by the verifier.
  - [`GET /api/sessions/{sessionId}/details`](http://localhost/path_to_generated_docs): read verification details: endpoint to expose the verification details involved in the session.
  - [`POST /api/sessions/{sessionId}/results`](http://localhost/path_to_generated_docs): create verification result: endpoint for the verifier to submit the verification result.
- conversations
  - [`POST /api/conversations`](http://localhost/path_to_generated_docs) create verification conversation: endpoint to invoke by both parties in the verification process to inform they are ready for the verification.
  - [`GET /api/conversations`](http://localhost?path_to_generated_docs): returns the list of conversations relevant to the authenticated user.
  - [`POST /api/conversations/{conversationId}`](http://localhost/path_to_generated_docs): add user to verification conversation: where the verification conversation will take place (e.g. 1st iteration WebRTC).
  - [`DELETE /api/conversations/{conversationId}`](http://localhost/path_to_generated_docs): delete verification conversation: endpoint to delete a conversation.
- verifications
  - [`GET /api/verifications/{entrypointId}/{userId}`](http://localhost/path_to_generated_docs): Retrieves the [`VerificationResult`](verification_result.md) of the given `Initiator` and `EntryPoint`.
  - [`POST /api/verification/{entrypointId}/{userId}/access`](http://localhost/path_to_generated_docs): Grants access rights to the verification result of the give `Initiator` and `EntryPoint`.

### Verification Server dependencies
- `Entrypoint`: 
  - send verification session details to `Initiator`
  - send verification request availability to `Initiator`
  - forward verification conversation details to `Initiator`
- `Verifier`:
  - send verification session details to `Verifier` via `Channel`
  - send verification request availability to verifier
  - forward verification conversation details to verifier
- `Channel`:
  - enables the communication for a `VerificationConversation` between `Initiator` and `Verifier`