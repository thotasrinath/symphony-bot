# Symphony Bot

A Symphony bot built with the Symphony BDK (Bot Developer Kit) and Spring Boot that provides welcome messages to new users and GIF functionality.

## Features

- Automatically welcomes users when they join a room
- Provides a `/gif` slash command for displaying GIFs
- Handles GIF category selection through forms

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Access to a Symphony instance (configured for develop2.symphony.com by default)
- Bot account on Symphony with RSA authentication

## Setup

1. Clone this repository:
   ```
   git clone <repository-url>
   cd symphony-bot
   ```

2. Configure your bot:
   - Update the `application.yaml` file with your bot's credentials
   - Place your bot's private key in the `src/main/resources/rsa/` directory
   - Update the bot username in the configuration if needed

3. Build the project:
   ```
   mvn clean package
   ```

4. Run the application:
   ```
   java -jar target/symphony-bot-0.0.1-SNAPSHOT.jar
   ```

## Configuration

The bot is configured through the `src/main/resources/application.yaml` file:

```yaml
bdk:
  host: develop2.symphony.com

  bot:
    username: devcertbot8728
    privateKey:
      path: src/main/resources/rsa/devcertbot8728-private.pem

logging:
  level:
    com.symphony: debug
```

Adjust these settings according to your Symphony environment.

## Usage

### Welcome Message

The bot automatically sends a welcome message to users when they join a room where the bot is present. The welcome message is defined in the `src/main/resources/templates/welcome.ftl` template.

### GIF Command

Users can trigger the GIF functionality by typing `/gif` in a chat where the bot is present. This will display a GIF form where users can select a category.

## Dependencies

- Symphony BDK 3.1.3
- Spring Boot
- Maven

## Development

This project uses Maven for dependency management and building. The main components are:

- `BotApplication.java`: The main Spring Boot application entry point
- `OnUserJoinedRoomListener.java`: Handles user join events and sends welcome messages
- `GifSlashHandler.java`: Handles the `/gif` slash command
- `GifFormActivity.java`: Processes GIF category form submissions

## License

This project is licensed under the Apache License, Version 2.0 - see the [LICENSE](LICENSE) file for details.

```
Copyright 2023

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
