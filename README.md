# ContactsApp
An android application which uses mockAPI.io for listing and managing contacts.

## Prerequisites

#### 1. Check the App

If the app cannot list contacts, clone the example contacts (from the link below) and use new endpoint.

	https://mockapi.io/clone/5fae94e263e40a0016d89d26

#### 2. Ready to run.

## Features
- Contact Features (Listing, Adding, Editing, Deleting, Filtering)
- Caching Results (Offline Capability)
- Pull to Refresh
- Unit Tests

## Tech Stack
- **Kotlin** - Officially supported programming language for Android development by Google
- **Kotlin DSL** - Alternative syntax to the Groovy DSL
- **Coroutines** - Perform asynchronous operations
- **Flow** - Handle the stream of data asynchronously
- **Android Architecture Components**
  - **LiveData** - Notify views about data changes
  - **Room** - Persistence library
  - **ViewModel** - UI related data holder
  - **ViewBinding** - Allows to more easily write code that interacts with views
- **Hilt** - Dependency Injection framework
- **Retrofit** - Networking library
- **Moshi** - A modern JSON library for Kotlin and Java
 
## Local Unit Tests
- The project uses MockWebServer (scriptable web server) to test API interactions.

## Screenshots
![contacts_app](https://user-images.githubusercontent.com/25778714/131024975-ebb061f3-8981-44f4-880f-d260bc036ec3.jpg)

## Architecture
![arch500](https://user-images.githubusercontent.com/25778714/113482640-3801f100-94a8-11eb-98d6-e15cb21a905b.png)
