# Volunteer verification service
The purpose of this system is to allow create a trust relationship between anonymous users from the Internet.

# Context
Currently, people meet in real life after having had a first interaction in the virtual world.
This opens a door to potential security risks from either side towards the other, from innocent pranks to more serious crimes such as kidnapping or the alike.

# Description
Given a pair of humans that do not know each other in real life, 
each one wants to have the certainty that the other party is who they claim they are.
This introduces the requirement of some kind of verification, such as national ID card matching the person.
In order to proceed with such verification, and considering that this is done on the Internet (remotely), 
the following parties will be involved:

- Initiator: human that wants/needs to be verified
- Entrypoint: component the initiator uses to request a verification
- Verification Server: component that orchestrates the verification process and stores verified data.
- Web front end: component that allows the Initiator to enter their personal details, allows the verifier to check them and to confirm verification.
- Verifier: human/component that does the actual verification via web cam or AI. Depending on the type of verifier:
  - human -> WebRTC: will need a secure way to contact the initiator and proceed with the verification of the details provided by the user together with his ID.
  - machine -> API: verification done by a third party

# Roadmap
## 1st iteration
- Initiator: Telegram user
- Entrypoint: Telegram bot
- Verification server: capable of orchestrating the process across parties, providing encryption and correlation elements to prevent spoofing or any other attack vectors.
- Web front end: 
  - Allow the Initiator to input their details and store them in an immutable manner.
  - Allow the verifier to see the details of the user after receiving an individualized access token (to prevent from verifiers seeing details of initiators they are not dealing with)
- Verifier: volunteer (verified) willing to take web cam calls and do the verification. Ideally picked randomly to avoid potential abuses.
- WebRTC: As the solution to host secure web conferences without exposing personal details of either party.

## 2nd iteration
Same as the first one with the following changes:
- Initiator: third party software
- Entrypoint: ReST endpoint of the verification server
- Verification server: details from the initiator need to be imported into the system since they have been collected elsewhere.

## 3rd iteration
Same as above with the following changes:
- Verifier: AI