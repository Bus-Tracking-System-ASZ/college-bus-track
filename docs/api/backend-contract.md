# COLLEGE BUS TRACK Backend Contract

## Authentication

Firebase Authentication is used.

Supported roles:

- STUDENT
- DRIVER
- ADMIN

---

## Users

Collection:

users/{uid}

Fields:

- uid
- name
- email
- phone
- classYear
- role
- passId
- emergencyContactName
- emergencyContactPhone

---

## Buses

Collection:

buses/{busId}

Fields:

- id
- number
- routeId
- driverId
- capacity
- seatsAvailable
- status

Status values:

- ACTIVE
- INACTIVE
- MAINTENANCE
- DELAYED

---

## Routes

Collection:

routes/{routeId}

Fields:

- id
- name
- active
- stopIds

---

## Stops

Collection:

stops/{stopId}

Fields:

- id
- name
- latitude
- longitude
- sequence

---

## Bus Locations

Collection:

busLocations/{busId}

Fields:

- busId
- latitude
- longitude
- heading
- timestamp

---

## Notifications

Collection:

notifications/{notificationId}

Fields:

- id
- title
- message
- routeId
- severity
- createdAt

Severity values:

- INFO
- WARNING
- CRITICAL

---

## Device Tokens

Collection:

deviceTokens/{tokenId}

Fields:

- uid
- token
- platform
- updatedAt