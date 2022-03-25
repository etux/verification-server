# Verification service
The purpose of this system is to allow to create a chain of trust between anonymous people.
It does so by:
- Providing a verification process for individuals who free willingly wish to be verified.
- Securing the verified information by making it immutable and encrypting it.
- Auditing the verification process through its different stages. as well as the access to the data.
- Providing a proof of work, with its authenticity verified by private-public key signature.

# Context
Currently, people meet in real life after having had a first interaction in the virtual world.
This opens a door to potential security risks from either side towards the other, 
from innocent pranks to more serious crimes such as kidnapping or the alike.

# Description
Given a pair of humans that do not know each other in real life, 
each one wants to have the certainty that the other party is who they claim they are.
This introduces the requirement of some kind of verification, such as national ID card matching the person.
In order to proceed with such verification, and considering that this is done on the Internet (remotely), 
the following parties will be involved:

- [`Initiator`](initiators.md): human that wants/needs to be verified
- [`Entrypoint`](entry_points.md): component the initiator uses to request a verification
- [`VerificationServer`](verification_server.md): component that orchestrates the verification process and stores verified data.
- [`Web frontend`](web_frontend.md): component that allows the Initiator to enter their personal details, allows the verifier to check them and to confirm verification.
- [`Verifier`](): human/component that does the actual verification via web cam or AI. Depending on the type of verifier:
  - human -> WebRTC: will need a secure way to contact the initiator and proceed with the verification of the details provided by the user together with his ID.
  - machine -> API: verification done by a third party
- [`Checker`](checker.md): the person or entity interested in accessing the `VerificationResult` of a given individual,

Additionally, the following concepts are important:

- [`VerificationSession`](): is created upon initiator request from a registered entrypoint.
- [`VerificationConversation`](): is created upon a verifier has accepted the verification session. The verification conversation
allows the initiator to decide whether the session is to take place (by being available or not). This allows us to control
access to the initiator's details, by only allowing access to the person that is going to verify the initiator (and nobody else).
- [`VerificationChannel`](): A conversation is linked to a particular channel, where the communication between Initiator and Verifier will take place.
The Initiator (or the entrypoint in its default) should provide a list of supported channels. A channel can be a third party identity verification API,
a WebRTC room, or even a Telegram call (Initiator and Verifier must have a common supported channel).
- [`VerificationResult`](): Is the end result of the process, and is used by `Checker`s to gain trust on the individual.

# Roadmap
## 1st iteration
- Initiator: Telegram user / Web?
- Entrypoint: Telegram bot / Web?
- Verification server: first iteration of the verification process plus support to:
- Web front end: 
  - Allow the Initiator to input their details and store them in an immutable manner.
  - Allow the verifier to see the details of the user after receiving an individualized access token (to prevent from verifiers seeing details of initiators they are not dealing with)
- Verifier: volunteer (verified) willing to take webcam calls and do the verification. Ideally picked randomly to avoid potential abuses.
- WebRTC: As the solution to host secure web conferences without exposing personal details of either party, can be the default and first supported channel.
- `Checker`: can access `VerificationResult` (including `VerificationDetails`) as a registered user of the system.

## 2nd iteration
Same as the first one with the following changes:
- Initiator: third party software
- Entrypoint: ReST endpoint of the verification server
- Verification server: details from the initiator need to be imported into the system since they have been collected elsewhere.
- `Checker`
  - the same as the previous plus
  - a non registered user can request access to the `VerificationResult` to the identified person
  - the identified person may grant access to the non-registered user or decline it.
  - once the access is granted, the non registered `Checker` can see the details of the identified person for a given amount of time.

## 3rd iteration
Same as above with the following changes:
- Verifier: AI


### Open questions
