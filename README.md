
# Fruit Ninja Android Game


This project is an Android game inspired by the popular Fruit Ninja game. The game is developed using Android Studio and features a simple gameplay where the player slices fruits to score points. The player needs to avoid slicing bombs and maintain lives to keep playing.


## Deployment

To deploy this project run

```bash
  npm run deploy
```

Clone the Repository:

sh
Copy code
git clone https://github.com/your-username/fruit-ninja-android-game.git
Open the Project:

Open Android Studio.
Select Open an existing Android Studio project.
Navigate to the cloned repository and select it.
Build the Project:

Ensure that you have the required SDKs installed.
Click on Build > Make Project.
Run the Game:

Connect an Android device or start an emulator.
Click on Run > Run 'app'.
## Features

- Continuous Fruit Spawning: Fruits spawn at regular intervals and fall from the top of the screen.
- Background Scaling: The background image is scaled to fit the screen dimensions.
- Swipe Detection: Players can swipe on the screen to slice fruits.
- Score Tracking: The game tracks and displays the player's score.
- Lives System: The player has a limited number of lives, which decrease when a fruit is missed.
- Game Over Condition: The game ends when the player runs out of lives.


## Tech Stack

**Android Studio:** The primary IDE for Android development.

**Java:** The programming language used development.

**SurfaceView:** Used for rendering the game graphics.

**Bitmap:** Used for handling images in the game.


## Documentation

[Documentation](https://linktodocumentation)

Start the Game: Launch the app and click on the start button to begin the game.
Slice Fruits: Swipe on the screen to slice the falling fruits and increase your score.
Avoid Bombs: Be careful not to slice bombs, as they will cause the game to end.
Monitor Lives: Keep an eye on the lives counter; the game ends when you run out of lives.
