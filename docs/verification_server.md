# Verification Server
Central component of the solution, responsible for:
- registering and managing entry points [entrypoints](entry_points.md)
- supports the verification [protocol](./diagrams/sequence_diagram_verification.puml)
- produces proof of verification

## Verification Server endpoints and webhooks
- endpoints
  - create verification session: endpoint to allow creating a verification session
  - create verification details: endpoint to add verification data to be evaluated by the verifier
  - read verification details: endpoint to expose the verification details involved in the session
  - create verification conversation: endpoint to invoke by both parties in the verification process to inform they are ready for the verification
  - add user to verification conversation: where the verification conversation will take place (e.g. 1st iteration WebRTC)
  - create verification result: endpoint for the verifier to submit the verification result.
  - delete verification session: endpoint to cancel a verification session
  - delete verification conversation: endpoint to delete a conversation
- webhooks
  - send verification session details to initiator (via entrypoint)
  - send verification session details to verifier (via??)
  - send verification request availability to verifier
  - send verification request availability to intiator
  - create room for verification conversation
  - forward verification conversation details to verifier
  - forward verification conversation details to initiator
  - calls off verification