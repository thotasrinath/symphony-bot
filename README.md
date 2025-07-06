# Symphony Bot

A Symphony bot built with the Symphony BDK (Bot Developer Kit) and Spring Boot that provides welcome messages to new users, GIF functionality, time-based task scheduling, and a notification system for sending periodic messages to user groups.

## Features

- Automatically welcomes users when they join a room
- Provides a `/gif` slash command for displaying GIFs
- Handles GIF category selection through forms
- Includes a time schedulable actor for executing tasks at specific times
- Supports a notification system for sending periodic messages to user groups
- Offers a `/schedule-cs` command for scheduling control system notifications

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

### Time Schedulable Actor

The bot includes a time schedulable actor that can execute tasks at specific times. You can use the `ActorSchedulerService` to schedule tasks:

```java
// Inject the service
@Autowired
private ActorSchedulerService schedulerService;

// Schedule a one-time task
schedulerService.scheduleOnce("task-name", Duration.ofSeconds(10));

// Schedule a recurring task
schedulerService.scheduleAtFixedRate("recurring-task", Duration.ofSeconds(5), Duration.ofMinutes(1));

// Cancel a task
schedulerService.cancelTask("task-name");
```

See `SchedulerExample.java` for a complete example of how to use the scheduler service.

### Notification System

The bot includes a notification system that can send periodic messages to specific user groups. You can use the notification system as follows:

```java
// Create notification configurations
NotificationConfig config1 = new NotificationConfig(
    "system-id",           // Control system identifier
    Duration.ofMinutes(5), // Notification interval
    List.of("user1", "user2") // User group to notify
);

// Create actor system with the configuration
ActorSystem<ControlSystemActor.Command> system = 
    ActorSystem.create(ControlSystemActor.create(config1), "ControlSystemActor");
```

The notification system will automatically send messages to the specified user group at the configured interval. See `NotificationApp.java` for a complete example of how to use the notification system.

### Schedule Control System Command

Users can schedule control system notifications by typing `/schedule-cs` in a chat where the bot is present. This will display a form with the following fields:

- Control Center Name: Name of the control system
- Date: Date when the notification should be sent
- Time: Time when the notification should be sent

After submitting the form, the bot will create a new actor for the specified control system and schedule notifications according to the provided date and time.

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
- `ScheduledActor.java`: A time schedulable actor that can execute tasks at specific times
- `ActorSchedulerService.java`: A service that manages the actor system and provides methods to schedule tasks
- `SchedulerExample.java`: An example component that demonstrates how to use the scheduler service
- `ControlSystemActor.java`: An actor that sends periodic notifications to user groups
- `NotificationConfig.java`: Configuration class for the notification system
- `NotificationService.java`: Service for sending notifications to users
- `NotificationApp.java`: Example application demonstrating the notification system
- `ScheduleCSSlashHandler.java`: Handles the `/schedule-cs` slash command
- `ScheduleCSFormActivity.java`: Processes control system scheduling form submissions

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
