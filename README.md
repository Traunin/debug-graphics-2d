# DebugGraphics2D

The following library is a superset of Java's Graphics2D class which allows users to display markers at primitives' control points for debugging purposes.

## Prerequisites

- JDK (preferably 21 or newer)
- `javac` and `jar` in PATH
- `git` (to clone the repository, not necessary)

## Building

### Manually

Clone the repository:

```sh
git clone https://github.com/traunin/debug-graphics-2d
```

Or download the code manually and extract it in the current folder.

Go into the folder:

```sh
cd debug-graphics-2d
```

Run:

```sh
javac -d bin src/com/github/traunin/debug_graphics_2d/*.java
jar cf DebugGraphics2D.jar -C bin .
```

The jar file is generated in the root folder of the repository.

### IntelliJ IDEA

Alternatively, if you are using IntelliJ IDEA: 

1. Go to "File > Project Structure > Artifacts".
2. Click on the plus icon.
3. Select "JAR > From modules with dependencies".
4. Go to "Build > Build Artifacts".
5. Your jar file is now in the out/artifacts directory.

## Importing

### Gradle

In the `app` directory create the `lib` folder (or name as you wish) at the same level as the build file and the `src` folder. Place the jar file in that directory.

Then, modify the corresponding build file:

`build.gradle`:

```groovy
dependencies {
    # other dependencies above
    implementation files('lib/DebugGraphics2D.jar')
}
```

`build.gradle.kts`:

```kts
dependencies {
    // other dependencies above
    implementation(files("lib/DebugGraphics2D.jar"))
}
```

And rebuild the project. The package should be available for import.

## Usage

```java
// get the object

// import the library
import com.github.traunin.debug_graphics_2d.DebugGraphics2D;

// get any graphics instance from JFrame, JPanel, etc.
final Graphics g;

// create from graphics2d instance
final DebugGraphics2D g2d = new DebugGraphics2D((Graphics2D) g);


// debug parameters

// enable or disable debugging (no new markers will be recorded if disabled)
// enabled by default
g2d.enableDebugging();
g2d.disableDebugging();
 
// set marker color and/or size
g2d.setDebugColor(Color.YELLOW);
g2d.setDebugMarkerSize(4); // prefer even size


// draw

// works the same as standard graphics2d instance
// but will draw the corners of the drawn primitives
g2d.fillOval(50, 50, 100, 100);

// works with transforms
g2d.translate(50, 50);

// works with general path
g2d.fill(somePath);


// deferring markers

// start drawing markers on the top layer
g2d.startDeferringMarkers();

// draw something
g2d.fillRect(10, 10, 20, 20);

// draw all of the deferred markers on the top layer
// (if debugging was enabled)
g2d.drawDeferredMarkers();
```

